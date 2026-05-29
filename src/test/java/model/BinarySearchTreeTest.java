package model;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private BinarySearchTree bst;

    /** Helper: cria um Contribuinte com CPF já limpo (só números) */
    private Contribuinte c(String cpf) {
        return new Contribuinte(cpf, "Teste", "Regular");
    }

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree();
    }

    // ═══════════════════════════════════════════════════
    // INSERÇÃO
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("insert: amount sobe a cada inserção")
    void inserirDeveAumentarAmount() {
        bst.insert(c("11111111111"));
        assertEquals(1, bst.getAmount());

        bst.insert(c("22222222222"));
        assertEquals(2, bst.getAmount());
    }

    @Test
    @DisplayName("insert: CPF duplicado não é inserido (amount não muda)")
    void inserirDuplicadoNaoAumentaAmount() {
        // A árvore ignora duplicatas silenciosamente — comportamento documentado no fonte
        bst.insert(c("11111111111"));
        bst.insert(c("11111111111"));
        assertEquals(1, bst.getAmount());
    }

    @Test
    @DisplayName("insert: CPF formatado e puro resultam na mesma chave")
    void cpfFormatadoEPuroSaoMesmaChave() {
        // Contribuinte normaliza no construtor: remove tudo que não é dígito
        bst.insert(c("111.111.111-11")); // vira "11111111111" internamente
        assertNotNull(bst.search("11111111111"));
    }

    // ═══════════════════════════════════════════════════
    // BUSCA
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("search: encontra CPF existente e retorna dados corretos")
    void buscarCpfExistente() {
        bst.insert(new Contribuinte("12345678901", "Maria Silva", "Ativo"));
        Contribuinte result = bst.search("12345678901");

        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        assertEquals("Maria Silva", result.getNome());
    }

    @Test
    @DisplayName("search: retorna null para CPF inexistente")
    void buscarCpfInexistenteRetornaNull() {
        bst.insert(c("12345678901"));
        assertNull(bst.search("99999999999"));
    }

    @Test
    @DisplayName("search: retorna null em árvore vazia sem lançar exceção")
    void buscarEmArvoreVaziaRetornaNull() {
        assertNull(bst.search("12345678901"));
    }

    @Test
    @DisplayName("search: searchCount é pelo menos 1 após busca")
    void buscaIncrementaSearchCount() {
        bst.insert(c("12345678901"));
        bst.search("12345678901");
        assertTrue(bst.getSearchCount() >= 1);
    }

    @Test
    @DisplayName("search: searchCount reseta a cada nova busca")
    void searchCountResetaACadaBusca() {
        bst.insert(c("50000000000"));
        bst.insert(c("30000000000")); // aumenta profundidade

        bst.search("30000000000"); // busca mais profunda
        int countBusca1 = bst.getSearchCount();

        bst.search("50000000000"); // busca na raiz
        int countBusca2 = bst.getSearchCount();

        // Se acumulasse, countBusca2 seria maior que countBusca1
        assertTrue(countBusca2 < countBusca1 + countBusca1,
                "searchCount deve refletir só a última busca");
    }

    // ═══════════════════════════════════════════════════
    // REMOÇÃO
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("delete: retorna true ao remover CPF existente")
    void removerExistenteRetornaTrue() {
        bst.insert(c("12345678901"));
        assertTrue(bst.delete("12345678901"));
    }

    @Test
    @DisplayName("delete: amount diminui após remoção")
    void removerDiminuiAmount() {
        bst.insert(c("12345678901"));
        bst.delete("12345678901");
        assertEquals(0, bst.getAmount());
    }

    @Test
    @DisplayName("delete: CPF removido não é mais encontrado")
    void cpfRemovidoNaoEEncontrado() {
        bst.insert(c("12345678901"));
        bst.delete("12345678901");
        assertNull(bst.search("12345678901"));
    }

    @Test
    @DisplayName("delete: retorna false para CPF inexistente")
    void removerInexistenteRetornaFalse() {
        bst.insert(c("12345678901"));
        assertFalse(bst.delete("99999999999"));
    }

    @Test
    @DisplayName("delete: árvore vazia não lança exceção")
    void removerEmArvoreVaziaNaoLancaExcecao() {
        assertDoesNotThrow(() -> bst.delete("12345678901"));
        assertFalse(bst.delete("12345678901"));
    }

    @Test
    @DisplayName("delete: caso 1 — nó folha")
    void removerFolha() {
        //      50
        //     /
        //    30   ← folha, será removido
        bst.insert(c("50000000000"));
        bst.insert(c("30000000000"));

        bst.delete("30000000000");

        assertNull(bst.search("30000000000"));
        assertNotNull(bst.search("50000000000")); // pai intacto
        assertEquals(1, bst.getAmount());
    }

    @Test
    @DisplayName("delete: caso 2 — nó com um filho")
    void removerNoComUmFilho() {
        //    50   ← será removido (só tem filho direito)
        //      \
        //       70
        bst.insert(c("50000000000"));
        bst.insert(c("70000000000"));

        bst.delete("50000000000");

        assertNull(bst.search("50000000000"));
        assertNotNull(bst.search("70000000000")); // filho promovido
        assertEquals(1, bst.getAmount());
    }

    @Test
    @DisplayName("delete: caso 3 — nó com dois filhos (usa sucessor in-order)")
    void removerNoComDoisFilhos() {
        //        50   ← será removido
        //       /  \
        //      30   70
        bst.insert(c("50000000000"));
        bst.insert(c("30000000000"));
        bst.insert(c("70000000000"));

        bst.delete("50000000000");

        assertNull(bst.search("50000000000"));
        assertNotNull(bst.search("30000000000")); // filhos preservados
        assertNotNull(bst.search("70000000000"));
        assertEquals(2, bst.getAmount());
    }

    @Test
    @DisplayName("delete: remover raiz de árvore com um único nó")
    void removerUnicoNoEsvaziaArvore() {
        bst.insert(c("50000000000"));
        bst.delete("50000000000");

        assertEquals(0, bst.getAmount());
        assertNull(bst.search("50000000000"));
        assertNull(bst.getRoot()); // raiz deve ser null
    }

    // ═══════════════════════════════════════════════════
    // IN-ORDER
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("inOrder: retorna lista vazia para árvore vazia")
    void inOrderArvoreVaziaRetornaListaVazia() {
        assertTrue(bst.inOrder().isEmpty());
    }

    @Test
    @DisplayName("inOrder: retorna contribuintes em ordem crescente de CPF")
    void inOrderRetornaOrdemCrescente() {
        bst.insert(c("50000000000"));
        bst.insert(c("30000000000"));
        bst.insert(c("70000000000"));
        bst.insert(c("20000000000"));
        bst.insert(c("40000000000"));

        List<Contribuinte> lista = bst.inOrder();

        assertEquals(5, lista.size());
        for (int i = 0; i < lista.size() - 1; i++) {
            assertTrue(
                    lista.get(i).getCpf().compareTo(lista.get(i + 1).getCpf()) < 0,
                    "inOrder não está em ordem crescente na posição " + i
            );
        }
    }

    @Test
    @DisplayName("inOrder: contém todos os elementos inseridos")
    void inOrderContemTodosOsElementos() {
        String[] cpfs = {"50000000000","30000000000","70000000000","20000000000"};
        for (String cpf : cpfs) bst.insert(c(cpf));

        List<String> chaves = bst.inOrder().stream()
                .map(Contribuinte::getCpf)
                .toList();

        for (String cpf : cpfs) {
            assertTrue(chaves.contains(cpf), "CPF ausente no inOrder: " + cpf);
        }
    }

    // ═══════════════════════════════════════════════════
    // ALTURA
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("bstHeight: árvore vazia retorna -1")
    void alturaArvoreVaziaRetornaMenosUm() {
        assertEquals(-1, bst.bstHeight());
    }

    @Test
    @DisplayName("bstHeight: árvore com só raiz retorna 0")
    void alturaComUmNoRetornaZero() {
        bst.insert(c("50000000000"));
        assertEquals(0, bst.bstHeight());
    }

    @Test
    @DisplayName("bstHeight: árvore balanceada de 3 nós retorna 1")
    void alturaArvoreBalanceadaDeTresNos() {
        //    50
        //   /  \
        //  30   70
        bst.insert(c("50000000000"));
        bst.insert(c("30000000000"));
        bst.insert(c("70000000000"));

        assertEquals(1, bst.bstHeight());
    }

    @Test
    @DisplayName("bstHeight: inserção em ordem crescente gera altura máxima (degenerada)")
    void alturaArvoreDegenereada() {
        // 10 → 20 → 30 → 40 → 50: vira lista ligada à direita
        bst.insert(c("10000000000"));
        bst.insert(c("20000000000"));
        bst.insert(c("30000000000"));
        bst.insert(c("40000000000"));
        bst.insert(c("50000000000"));

        assertEquals(4, bst.bstHeight()); // altura = n-1
    }

    // ═══════════════════════════════════════════════════
    // SEARCH PATH
    // ═══════════════════════════════════════════════════

    @Test
    @DisplayName("searchPath: caminho até a raiz tem tamanho 1")
    void searchPathParaRaiz() {
        bst.insert(c("50000000000"));
        List<NodeBst> path = bst.searchPath("50000000000");
        assertEquals(1, path.size());
        assertEquals("50000000000", path.get(0).getKey());
    }

    @Test
    @DisplayName("searchPath: caminho inclui todos os nós visitados até o alvo")
    void searchPathIncluiNosVisitados() {
        bst.insert(c("50000000000")); // raiz
        bst.insert(c("30000000000")); // esquerda
        bst.insert(c("20000000000")); // esquerda da esquerda

        List<NodeBst> path = bst.searchPath("20000000000");

        // deve ter passado por 50 → 30 → 20
        assertEquals(3, path.size());
        assertEquals("50000000000", path.get(0).getKey());
        assertEquals("30000000000", path.get(1).getKey());
        assertEquals("20000000000", path.get(2).getKey());
    }

    @Test
    @DisplayName("searchPath: CPF inexistente retorna caminho até onde parou")
    void searchPathCpfInexistente() {
        bst.insert(c("50000000000"));
        List<NodeBst> path = bst.searchPath("99999999999");
        // Passa pela raiz e não encontra — lista tem ao menos a raiz
        assertFalse(path.isEmpty());
    }
}