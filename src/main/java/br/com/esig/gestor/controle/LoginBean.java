package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.Usuario;
import br.com.esig.gestor.persistencia.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean {

    @Inject
    private UsuarioDAO usuarioDAO;

    private String username;
    private String password;

    public String login() {
        Usuario usuario = usuarioDAO.findByUsername(username);

        if (usuario != null && BCrypt.checkpw(password, usuario.getPassword())) {
            // Senha correta - Login bem-sucedido
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            return "/listagem.xhtml?faces-redirect=true"; // Redireciona para a página principal
        } else {
            // Usuário ou senha incorretos
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Login", "Usuário ou senha inválidos."));
            return null; // Permanece na página de login
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }
        return "/login.xhtml?faces-redirect=true"; // Redireciona para a página de login
    }

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}