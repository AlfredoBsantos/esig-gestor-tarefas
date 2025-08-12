package br.com.esig.gestor.persistencia;

import br.com.esig.gestor.modelo.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@ApplicationScoped
public class UsuarioDAO implements Serializable {

    @Inject
    private EntityManager em;

    /**
     * Salva um novo usuário no banco de dados.
     * @param usuario O usuário a ser salvo.
     */
    public void salvar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario); // Usamos persist para entidades novas
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar o usuário.", e);
        }
    }

    public Usuario findByUsername(String username) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // Setter para testes, se necessário
    public void setEm(EntityManager em) {
        this.em = em;
    }
}