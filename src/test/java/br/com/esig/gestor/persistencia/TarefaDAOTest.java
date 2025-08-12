package br.com.esig.gestor.persistencia;

import br.com.esig.gestor.modelo.Prioridade;
import br.com.esig.gestor.modelo.SituacaoTarefa;
import br.com.esig.gestor.modelo.Tarefa;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarefaDAOTest {

    private static EntityManagerFactory factory;
    private EntityManager em;
    private TarefaDAO tarefaDAO;

    @BeforeAll
    static void setupAll() {
        factory = Persistence.createEntityManagerFactory("test");
    }

    @AfterAll
    static void tearDownAll() {
        if (factory != null) {
            factory.close();
        }
    }

    @BeforeEach
    void setup() {
        em = factory.createEntityManager();
        tarefaDAO = new TarefaDAO();
        tarefaDAO.setEm(em);

        // CORREÇÃO: Limpamos o banco antes de cada teste para garantir isolamento.
        tarefaDAO.deleteAll();
    }

    @AfterEach
    void tearDown() {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        if (em != null) {
            em.close();
        }
    }

    @Test
    @DisplayName("Deve salvar uma nova tarefa e encontrá-la pelo ID")
    void deveSalvarUmaNovaTarefa() {
        // Arrange
        Tarefa novaTarefa = criarTarefa("Teste Unitário", "JUnit", Prioridade.MEDIA);

        // Act
        Tarefa tarefaPersistida = tarefaDAO.salvar(novaTarefa);

        // Assert
        assertNotNull(tarefaPersistida.getNumero());
        Tarefa tarefaSalvaDoBanco = tarefaDAO.buscarPorId(tarefaPersistida.getNumero());
        assertNotNull(tarefaSalvaDoBanco);
        assertEquals("Teste Unitário", tarefaSalvaDoBanco.getTitulo());
    }

    @Test
    @DisplayName("Deve remover uma tarefa existente")
    void deveRemoverUmaTarefa() {
        // Arrange
        Tarefa tarefaParaRemover = tarefaDAO.salvar(criarTarefa("Tarefa a ser removida", "Tester", Prioridade.BAIXA));
        Tarefa tarefaParaManter = tarefaDAO.salvar(criarTarefa("Tarefa a ser mantida", "Tester", Prioridade.ALTA));

        // Act
        tarefaDAO.remover(tarefaParaRemover);

        // Assert
        Tarefa tarefaRemovidaDoBanco = tarefaDAO.buscarPorId(tarefaParaRemover.getNumero());
        Tarefa tarefaMantidaNoBanco = tarefaDAO.buscarPorId(tarefaParaManter.getNumero());

        assertNull(tarefaRemovidaDoBanco);
        assertNotNull(tarefaMantidaNoBanco);
    }

    @Test
    @DisplayName("Deve buscar tarefas com filtros corretamente")
    void deveBuscarTarefasComFiltros() {
        // Arrange
        tarefaDAO.salvar(criarTarefa("Relatório Mensal de Vendas", "Ana", Prioridade.ALTA));
        tarefaDAO.salvar(criarTarefa("Apresentação Final do Projeto", "João", Prioridade.MEDIA));
        tarefaDAO.salvar(criarTarefa("Revisar Relatório Anual", "Ana", Prioridade.BAIXA));

        // Act & Assert
        List<Tarefa> resultadoFiltroTitulo = tarefaDAO.buscar(null, "Relatório", null, null, 0, 10);
        assertEquals(2, resultadoFiltroTitulo.size());

        List<Tarefa> resultadoFiltroResponsavel = tarefaDAO.buscar(null, null, "João", null, 0, 10);
        assertEquals(1, resultadoFiltroResponsavel.size());

        List<Tarefa> resultadoFiltroSituacao = tarefaDAO.buscar(null, null, null, SituacaoTarefa.EM_ANDAMENTO, 0, 10);
        // Agora o teste espera 3, pois o banco está limpo no início.
        assertEquals(3, resultadoFiltroSituacao.size());
    }

    private Tarefa criarTarefa(String titulo, String responsavel, Prioridade prioridade) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(titulo);
        tarefa.setDescricao("Descrição de " + titulo);
        tarefa.setResponsavel(responsavel);
        tarefa.setPrioridade(prioridade);
        tarefa.setDeadline(LocalDate.now());
        return tarefa;
    }
}