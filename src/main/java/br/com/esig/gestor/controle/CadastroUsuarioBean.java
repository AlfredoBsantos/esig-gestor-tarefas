package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.Usuario;
import br.com.esig.gestor.persistencia.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CadastroUsuarioBean {

    @Inject
    private UsuarioDAO usuarioDAO;

    private String username;
    private String password;
    private String confirmPassword;

    public String salvar() {
        // 1. Validar se as senhas coincidem
        if (!password.equals(confirmPassword)) {
            adicionarMensagem("Erro", "As senhas não coincidem.");
            return null;
        }

        // 2. Validar se o nome de usuário já existe
        if (usuarioDAO.findByUsername(username) != null) {
            adicionarMensagem("Erro", "Este nome de usuário já está em uso.");
            return null;
        }

        // 3. Criar e salvar o novo usuário com a senha criptografada
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(username);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        novoUsuario.setPassword(hashedPassword);

        usuarioDAO.salvar(novoUsuario);

        // 4. Redirecionar para a página de login com uma mensagem de sucesso
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        adicionarMensagem("Sucesso!", "Usuário cadastrado com sucesso. Faça o login.");
        return "login?faces-redirect=true";
    }

    private void adicionarMensagem(String sumario, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, sumario, detalhe));
    }

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}