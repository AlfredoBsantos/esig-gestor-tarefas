package br.com.esig.gestor.util;

import br.com.esig.gestor.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
@Startup
public class DataInitializer {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @PostConstruct
    public void init() {
        // Verifica se já existe algum usuário
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        if (usuarios.isEmpty()) {
            // Cria um usuário padrão se o banco estiver vazio
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            // Gera o hash da senha "admin"
            String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
            admin.setPassword(hashedPassword);

            em.persist(admin);
        }
    }
}