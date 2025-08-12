package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.Prioridade;
import br.com.esig.gestor.modelo.Tarefa;
import br.com.esig.gestor.persistencia.TarefaDAO;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CadastroTarefaBean implements Serializable {

    @Inject
    private TarefaDAO tarefaDAO;

    private Tarefa tarefa = new Tarefa();

    // Em CadastroTarefaBean.java
    public String salvar() {
        try {
            tarefaDAO.salvar(this.tarefa);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Tarefa cadastrada com sucesso."));
            return "listagem?faces-redirect=true";
        } catch (Exception e) {
            // LINHA DE DEBUG ADICIONADA AQUI
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Ocorreu um erro ao cadastrar a tarefa."));
            return null;
        }
    }

    public Prioridade[] getPrioridades() {
        return Prioridade.values();
    }

    // Getters e Setters continuam os mesmos
    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
}