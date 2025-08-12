package br.com.esig.gestor.controle;

import br.com.esig.gestor.modelo.SituacaoTarefa;
import br.com.esig.gestor.modelo.Tarefa;
import br.com.esig.gestor.persistencia.TarefaDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListagemTarefaBean implements Serializable {

    @Inject
    private TarefaDAO tarefaDAO;

    private List<Tarefa> tarefas;

    // --- Campos para o Filtro ---
    private Long filtroNumero;
    private String filtroTituloDescricao;
    private String filtroResponsavel;
    private SituacaoTarefa filtroSituacao;

    // --- Campos para a Paginação ---
    private int paginaAtual = 1;
    private final int tamanhoPagina = 5;
    private Long totalTarefas;

    @PostConstruct
    public void init() {
        // Voltamos a definir o filtro padrão aqui
        filtroSituacao = SituacaoTarefa.EM_ANDAMENTO;
        buscar();
    }

    // O método buscar volta a usar os filtros
    public void buscar() {
        totalTarefas = tarefaDAO.contar(filtroNumero, filtroTituloDescricao, filtroResponsavel, filtroSituacao);
        int firstResult = (paginaAtual - 1) * tamanhoPagina;
        this.tarefas = tarefaDAO.buscar(filtroNumero, filtroTituloDescricao, filtroResponsavel, filtroSituacao, firstResult, tamanhoPagina);
    }

    public void buscarPrimeiraPagina() {
        paginaAtual = 1;
        buscar();
    }

    public void limparFiltros() {
        paginaAtual = 1;
        this.filtroNumero = null;
        this.filtroTituloDescricao = null;
        this.filtroResponsavel = null;
        this.filtroSituacao = SituacaoTarefa.EM_ANDAMENTO; // Volta ao padrão
        buscar();
    }

    // O resto da classe continua igual
    public void paginaAnterior() { if (paginaAtual > 1) { paginaAtual--; buscar(); } }
    public void proximaPagina() { if (paginaAtual * tamanhoPagina < totalTarefas) { paginaAtual++; buscar(); } }
    public void excluir(Tarefa tarefa) { try { tarefaDAO.remover(tarefa); buscar(); adicionarMensagem("Sucesso!", "Tarefa excluída com sucesso.", FacesMessage.SEVERITY_INFO); } catch (Exception e) { adicionarMensagem("Erro!", "Ocorreu um erro ao excluir a tarefa.", FacesMessage.SEVERITY_ERROR); } }
    public void concluir(Tarefa tarefa) { try { tarefa.setSituacao(SituacaoTarefa.CONCLUIDA); tarefaDAO.salvar(tarefa); buscar(); adicionarMensagem("Sucesso!", "Tarefa concluída com sucesso.", FacesMessage.SEVERITY_INFO); } catch (Exception e) { adicionarMensagem("Erro!", "Ocorreu um erro ao concluir a tarefa.", FacesMessage.SEVERITY_ERROR); } }
    public boolean isPodeVoltar() { return paginaAtual > 1; }
    public boolean isPodeAvancar() { return totalTarefas > 0 && (paginaAtual * tamanhoPagina < totalTarefas); }
    public String getDescricaoPaginacao() { if (totalTarefas == 0) { return ""; } long totalPaginas = (long) Math.ceil((double) totalTarefas / tamanhoPagina); return "Página " + paginaAtual + " de " + totalPaginas; }
    public SituacaoTarefa[] getSituacoes() { return SituacaoTarefa.values(); }
    public List<String> getResponsaveis() { return tarefaDAO.listarResponsaveis(); }
    private void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipo) { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, sumario, detalhe)); }
    public List<Tarefa> getTarefas() { return tarefas; }
    public Long getFiltroNumero() { return filtroNumero; }
    public void setFiltroNumero(Long filtroNumero) { this.filtroNumero = filtroNumero; }
    public String getFiltroTituloDescricao() { return filtroTituloDescricao; }
    public void setFiltroTituloDescricao(String filtroTituloDescricao) { this.filtroTituloDescricao = filtroTituloDescricao; }
    public String getFiltroResponsavel() { return filtroResponsavel; }
    public void setFiltroResponsavel(String filtroResponsavel) { this.filtroResponsavel = filtroResponsavel; }
    public SituacaoTarefa getFiltroSituacao() { return filtroSituacao; }
    public void setFiltroSituacao(SituacaoTarefa filtroSituacao) { this.filtroSituacao = filtroSituacao; }
}