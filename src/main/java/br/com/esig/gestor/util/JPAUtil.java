package br.com.esig.gestor.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class JPAUtil {

    private EntityManagerFactory factory;

    public JPAUtil() {
        try {
            Map<String, String> properties = new HashMap<>();

            // Tenta ler a variável de ambiente DATABASE_URL fornecida pelo Render
            String databaseUrl = System.getenv("DATABASE_URL");

            if (databaseUrl != null && !databaseUrl.isEmpty()) {
                // Ambiente de produção (Render)
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

                properties.put("javax.persistence.jdbc.url", dbUrl);
                properties.put("javax.persistence.jdbc.user", username);
                properties.put("javax.persistence.jdbc.password", password);
                properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                properties.put("hibernate.hbm2ddl.auto", "update"); // Use 'update' em produção
            }

            // Se as variáveis de ambiente não existirem, o JPA usará o persistence.xml (para rodar localmente)
            // Se existirem, ele usará as propriedades que acabamos de definir
            this.factory = Persistence.createEntityManagerFactory("default", properties);

        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao parsear a URL do banco de dados.", e);
        }
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}