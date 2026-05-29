package controller;
import model.BinarySearchTree;

import model.Contribuinte;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a camada de negócio (ReceitaController) de forma isolada,
 * sem depender do DataGeneratorControler nem do arquivo mock.json.
 *
 * Para isso, o ReceitaController precisa de um construtor alternativo
 * que aceite uma BSTpré-criada — veja a nota abaixo.
 */
class ReceitaControllerTest {

    private ReceitaController controller;

    /**
     * ATENÇÃO: ReceitaController() lê o mock.json no construtor via
     * DataGeneratorControler. Para testes, adicione um construtor
     * de teste no ReceitaController:
     *
     *   // Apenas para testes — não popula a base
     *   ReceitaController(BinarySearchTree tree) {
     *       this.tree = tree;
     *       this.dataGeneratorControler = null;
     *   }
     *
     * Assim os testes não dependem do arquivo JSON.
     */
    @BeforeEach
    void setUp() {
        // Passa a árvore diretamente — sem tocar no mock.json
        controller = new ReceitaController(new BinarySearchTree());
    }

    // ═══════════════════════════════════════════════════
    // registerCPF
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("registerCPF: CPF válido é cadastrado com sucesso")
    void cadastrarCpfValido() {
        String result = controller.registerCPF("12345678901", "João Silva", "Ativo");
        assertEquals("Contribuinte Cadastrado com sucesso", result);
    }

    @Test
    @DisplayName("registerCPF: CPF formatado também é aceito")
    void cadastrarCpfFormatado() {
        String result = controller.registerCPF("123.456.789-01", "João Silva", "Ativo");
        assertEquals("Contribuinte Cadastrado com sucesso", result);
    }

    @Test
    @DisplayName("registerCPF: CPF com menos de 11 dígitos é rejeitado")
    void cadastrarCpfCurtoEhInvalido() {
        String result = controller.registerCPF("1234567", "João Silva", "Ativo");
        assertEquals("CPF Inválido", result);
    }

    @Test
    @DisplayName("registerCPF: CPF com mais de 11 dígitos é rejeitado")
    void cadastrarCpfLongoEhInvalido() {
        String result = controller.registerCPF("123456789012", "João Silva", "Ativo");
        assertEquals("CPF Inválido", result);
    }

    @Test
    @DisplayName("registerCPF: CPF duplicado retorna mensagem de duplicata")
    void cadastrarCpfDuplicadoRetornaMensagemCorreta() {
        controller.registerCPF("12345678901", "João Silva", "Ativo");
        String result = controller.registerCPF("12345678901", "Outro Nome", "Inativo");
        assertEquals("CPF Duplicado", result);
    }

    @Test
    @DisplayName("registerCPF: CPF duplicado não aumenta o total de nós")
    void cadastrarCpfDuplicadoNaoAumentaContagem() {
        controller.registerCPF("12345678901", "João", "Ativo");
        controller.registerCPF("12345678901", "João", "Ativo");
        assertEquals(1, controller.getBstNodeAmount());
    }

    // ═══════════════════════════════════════════════════
    // searchCPF
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("searchCPF: encontra contribuinte cadastrado")
    void buscarCpfCadastrado() {
        controller.registerCPF("12345678901", "Maria Lima", "Ativo");
        Contribuinte result = controller.searchCPF("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        assertEquals("Maria Lima", result.getNome());
    }

    @Test
    @DisplayName("searchCPF: busca com CPF formatado encontra mesmo registro")
    void buscarCpfFormatadoEncontraRegistro() {
        controller.registerCPF("12345678901", "Maria Lima", "Ativo");
        Contribuinte result = controller.searchCPF("123.456.789-01");
        assertNotNull(result);
    }

    @Test
    @DisplayName("searchCPF: retorna null para CPF não cadastrado")
    void buscarCpfNaoCadastradoRetornaNull() {
        controller.registerCPF("12345678901", "Maria Lima", "Ativo");
        assertNull(controller.searchCPF("99999999999"));
    }

    @Test
    @DisplayName("searchCPF: CPF inválido retorna null")
    void buscarCpfInvalidoRetornaNull() {
        assertNull(controller.searchCPF("123")); // curto demais
    }

    // ═══════════════════════════════════════════════════
    // removeCPF
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("removeCPF: remove CPF existente e retorna true")
    void removerCpfExistenteRetornaTrue() {
        controller.registerCPF("12345678901", "Carlos", "Ativo");
        assertTrue(controller.removeCPF("12345678901"));
    }

    @Test
    @DisplayName("removeCPF: CPF removido não é mais encontrado")
    void cpfRemovidoNaoEEncontrado() {
        controller.registerCPF("12345678901", "Carlos", "Ativo");
        controller.removeCPF("12345678901");
        assertNull(controller.searchCPF("12345678901"));
    }

    @Test
    @DisplayName("removeCPF: retorna false para CPF não cadastrado")
    void removerCpfNaoCadastradoRetornaFalse() {
        assertFalse(controller.removeCPF("99999999999"));
    }

    @Test
    @DisplayName("removeCPF: CPF inválido retorna false")
    void removerCpfInvalidoRetornaFalse() {
        assertFalse(controller.removeCPF("123")); // curto demais
    }

    // ═══════════════════════════════════════════════════
    // listCPF
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("listCPF: retorna lista vazia quando não há registros")
    void listarSemRegistrosRetornaListaVazia() {
        assertTrue(controller.listCPF().isEmpty());
    }

    @Test
    @DisplayName("listCPF: retorna todos os contribuintes em ordem")
    void listarRetornaTodosEmOrdem() {
        controller.registerCPF("50000000000", "C", "Ativo");
        controller.registerCPF("30000000000", "A", "Ativo");
        controller.registerCPF("70000000000", "B", "Ativo");

        List<Contribuinte> lista = controller.listCPF();

        assertEquals(3, lista.size());
        assertEquals("30000000000", lista.get(0).getCpf());
        assertEquals("50000000000", lista.get(1).getCpf());
        assertEquals("70000000000", lista.get(2).getCpf());
    }

    // ═══════════════════════════════════════════════════
    // Métricas da árvore
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("getBstNodeAmount: reflete inserções e remoções")
    void contagemDeNosAtualizaCorretamente() {
        controller.registerCPF("11111111111", "A", "Ativo");
        controller.registerCPF("22222222222", "B", "Ativo");
        assertEquals(2, controller.getBstNodeAmount());

        controller.removeCPF("11111111111");
        assertEquals(1, controller.getBstNodeAmount());
    }

    @Test
    @DisplayName("getBstHeight: cresce com inserções em ordem (árvore degenerada)")
    void alturaCresCeComInsercaoOrdenada() {
        // Inserção em ordem crescente → vira lista ligada
        controller.registerCPF("10000000000", "A", "Ativo");
        controller.registerCPF("20000000000", "B", "Ativo");
        controller.registerCPF("30000000000", "C", "Ativo");
        controller.registerCPF("40000000000", "D", "Ativo");

        assertEquals(3, controller.getBstHeight()); // altura = n-1
    }

    @Test
    @DisplayName("getBstAmountLastSearch: retorna número de passos da última busca")
    void passosDaBuscaRetornadosCorretamente() {
        controller.registerCPF("50000000000", "Raiz", "Ativo");
        controller.registerCPF("30000000000", "Esq", "Ativo");

        controller.searchCPF("30000000000"); // 2 comparações: raiz → esquerda
        assertTrue(controller.getBstAmountLastSearch() >= 2);
    }
}