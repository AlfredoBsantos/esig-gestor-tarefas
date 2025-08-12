package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.Prioridade;
import br.com.esig.gestor.modelo.Tarefa;
import br.com.esig.gestor.persistencia.TarefaDAO;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class EdicaoTarefaBean implements Serializable {

    @Inject
    private TarefaDAO tarefaDAO;

    // A tarefa agora começa como nula. Ela será carregada sob demanda.
    private Tarefa tarefa;

    private Long tarefaId;

    // O @PostConstruct não é mais necessário para carregar a tarefa, então o removemos.

    public String salvar() {
        tarefaDAO.salvar(tarefa);
        return "listagem?faces-redirect=true";
    }

    // Método para validação de data
    public Date getHoje() {
        return new Date();
    }

    public Prioridade[] getPrioridades() {
        return Prioridade.values();
    }

    // --- GETTERS E SETTERS ---

    // CORREÇÃO PRINCIPAL: A LÓGICA DE CARREGAMENTO ESTÁ AQUI
    public Tarefa getTarefa() {
        // Se a tarefa ainda não foi carregada...
        if (this.tarefa == null) {
            // ...e se um ID foi passado pela URL...
            if (this.tarefaId != null) {
                // ...buscamos a tarefa no banco de dados.
                this.tarefa = tarefaDAO.buscarPorId(this.tarefaId);
            }
            // Se, mesmo assim, a tarefa for nula (ex: ID inválido), criamos uma nova para evitar erros na página.
            if (this.tarefa == null) {
                this.tarefa = new Tarefa();
            }
        }
        return this.tarefa;
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