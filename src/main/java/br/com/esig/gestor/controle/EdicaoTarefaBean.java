package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.Prioridade;
import br.com.esig.gestor.modelo.Tarefa;
import br.com.esig.gestor.persistencia.TarefaDAO;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EdicaoTarefaBean implements Serializable {

    @Inject
    private TarefaDAO tarefaDAO;

    // ALTERAÇÃO AQUI: Inicializamos o objeto para nunca ser nulo.
    private Tarefa tarefa = new Tarefa();

    private Long tarefaId;

    @PostConstruct
    public void init() {
        if (tarefaId != null) {
            // O objeto carregado do banco substituirá a instância vazia que criamos acima.
            tarefa = tarefaDAO.buscarPorId(tarefaId);
        }
    }

    public String salvar() {
        tarefaDAO.salvar(tarefa);
        return "listagem?faces-redirect=true";
    }

    public Prioridade[] getPrioridades() {
        return Prioridade.values();
    }

    // --- Getters e Setters (continuam os mesmos) ---
    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }
}