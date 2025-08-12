package br.com.esig.gestor.persistencia;

import br.com.esig.gestor.modelo.SituacaoTarefa;
import br.com.esig.gestor.modelo.Tarefa;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
// Remova o import do @Transactional, não vamos mais usá-lo
// import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TarefaDAO implements Serializable {

    @Inject
    private EntityManager em;

    // MÉTODO SALVAR CORRIGIDO
    public Tarefa salvar(Tarefa tarefa) {
        try {
            em.getTransaction().begin(); // Inicia a transação
            Tarefa tarefaSalva = em.merge(tarefa);
            em.getTransaction().commit(); // Confirma a transação
            return tarefaSalva;
        } catch (Exception e) {
            // Se der erro, desfaz a transação
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Propaga o erro para a camada de controle
            throw new RuntimeException("Erro ao salvar a tarefa.", e);
        }
    }

    // MÉTODO REMOVER CORRIGIDO
    public void remover(Tarefa tarefa) {
        try {
            em.getTransaction().begin(); // Inicia a transação
            Tarefa tarefaGerenciada = em.merge(tarefa);
            em.remove(tarefaGerenciada);
            em.getTransaction().commit(); // Confirma a transação
        } catch (Exception e) {
            // Se der erro, desfaz a transação
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Propaga o erro para a camada de controle
            throw new RuntimeException("Erro ao remover a tarefa.", e);
        }
    }

    public Tarefa buscarPorId(Long id) {
        return em.find(Tarefa.class, id);
    }

    // O restante da classe (métodos de busca, etc.) continua igual

    public List<Tarefa> buscar(Long numero, String tituloDescricao, String responsavel, SituacaoTarefa situacao, int firstResult, int pageSize) {
        String jpql = construirQueryBusca(numero, tituloDescricao, responsavel, situacao, false);
        TypedQuery<Tarefa> query = em.createQuery(jpql, Tarefa.class);
        setarParametros(query, numero, tituloDescricao, responsavel, situacao);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long contar(Long numero, String tituloDescricao, String responsavel, SituacaoTarefa situacao) {
        String jpql = construirQueryBusca(numero, tituloDescricao, responsavel, situacao, true);
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        setarParametros(query, numero, tituloDescricao, responsavel, situacao);
        return query.getSingleResult();
    }

    private String construirQueryBusca(Long numero, String tituloDescricao, String responsavel, SituacaoTarefa situacao, boolean isCount) {
        StringBuilder jpql = new StringBuilder();
        if (isCount) {
            jpql.append("SELECT COUNT(t) FROM Tarefa t WHERE 1=1 ");
        } else {
            jpql.append("SELECT t FROM Tarefa t WHERE 1=1 ");
        }
        if (numero != null) {
            jpql.append("AND t.numero = :numero ");
        }
        if (tituloDescricao != null && !tituloDescricao.isEmpty()) {
            jpql.append("AND (LOWER(t.titulo) LIKE LOWER(:tituloDescricao) OR LOWER(t.descricao) LIKE LOWER(:tituloDescricao)) ");
        }
        if (responsavel != null && !responsavel.isEmpty()) {
            jpql.append("AND t.responsavel = :responsavel ");
        }
        if (situacao != null) {
            jpql.append("AND t.situacao = :situacao ");
        }
        if (!isCount) {
            jpql.append("ORDER BY t.numero");
        }
        return jpql.toString();
    }

    private void setarParametros(TypedQuery<?> query, Long numero, String tituloDescricao, String responsavel, SituacaoTarefa situacao) {
        if (numero != null) {
            query.setParameter("numero", numero);
        }
        if (tituloDescricao != null && !tituloDescricao.isEmpty()) {
            query.setParameter("tituloDescricao", "%" + tituloDescricao + "%");
        }
        if (responsavel != null && !responsavel.isEmpty()) {
            query.setParameter("responsavel", responsavel);
        }
        if (situacao != null) {
            query.setParameter("situacao", situacao);
        }
    }

    public List<String> listarResponsaveis() {
        String jpql = "SELECT DISTINCT t.responsavel FROM Tarefa t ORDER BY t.responsavel";
        return em.createQuery(jpql, String.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void deleteAll() {
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Tarefa").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar todas as tarefas.", e);
        }
    }
}