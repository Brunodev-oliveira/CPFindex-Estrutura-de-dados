package view;

import controller.DataGeneratorControler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.BinarySearchTree;
import model.Contribuinte;
import model.NodeBst;
import model.NodeBstVisual;

import java.util.ArrayList;
import java.util.List;

public class DemoController {

    @FXML private ScrollPane scrollPane;
    @FXML private Pane       treeContainer;
    @FXML private TextField  tfCpf;
    @FXML private Label      lblStatus;

    private final BinarySearchTree bst = new BinarySearchTree();
    private NodeBstVisual rootVisual;
    private TreePane treePane;



    @FXML
    public void initialize() {
        treePane = new TreePane();

        // O TreePane se autodimensiona conforme a árvore — não fica preso ao container.
        // O ScrollPane cuida do overflow horizontal e vertical.
        treeContainer.getChildren().add(treePane);

        loadDataBase();
    }

    private void loadDataBase() {
        try {
            DataGeneratorControler dgc = new DataGeneratorControler();
            dgc.populateInitialBase(bst, 15);

            rootVisual = buildVisual(bst.getRoot());
            treePane.setRoot(rootVisual);
            centerScroll();

            lblStatus.setText("Árvore carregada com " + bst.getAmount() + " nós.");
        } catch (Exception e) {
            lblStatus.setText("Erro ao carregar: " + e.getMessage());
        }
    }




    @FXML
    private void onInsert() {
        String cpf = tfCpf.getText().replaceAll("[^0-9]", "");
        if (cpf.length() != 11) { lblStatus.setText("CPF inválido."); return; }

        List<NodeBst> pathData = bst.searchPath(cpf);

        if (!pathData.isEmpty() && pathData.get(pathData.size() - 1).getKey().equals(cpf)) {
            lblStatus.setText("CPF " + formatCpf(cpf) + " já existe na árvore.");
            tfCpf.clear();
            return;
        }

        List<NodeBstVisual> pathVisual = buildVisualPath(pathData, rootVisual, cpf);
        lblStatus.setText("Inserindo " + formatCpf(cpf) + "...");
        tfCpf.clear();

        treePane.insertAnimation(pathVisual, cpf, () -> {
            bst.insert(new Contribuinte(cpf, "—", "—"));
            rootVisual = buildVisual(bst.getRoot());
            treePane.setRoot(rootVisual);
            centerScroll();
            lblStatus.setText("CPF " + formatCpf(cpf) + " inserido. Total: " + bst.getAmount());
        });
    }
























    @FXML
    private void onSearch() {
        String cpf = tfCpf.getText().replaceAll("[^0-9]", "");
        if (cpf.length() != 11) { lblStatus.setText("CPF inválido."); return; }

        List<NodeBst> pathData = bst.searchPath(cpf);
        if (pathData.isEmpty()) { lblStatus.setText("Árvore vazia."); return; }

        List<NodeBstVisual> pathVisual = buildVisualPath(pathData, rootVisual, cpf);
        treePane.searchAnimation(pathVisual, cpf);

        boolean found = pathData.get(pathData.size() - 1).getKey().equals(cpf);
        lblStatus.setText(found
                ? "✔ " + formatCpf(cpf) + " encontrado em " + bst.getSearchCount() + " comparações."
                : "✘ " + formatCpf(cpf) + " não encontrado (" + bst.getSearchCount() + " comparações).");
    }

    @FXML
    private void onRemove() {
        String cpf = tfCpf.getText().replaceAll("[^0-9]", "");
        if (cpf.length() != 11) { lblStatus.setText("CPF inválido."); return; }

        List<NodeBst> pathData = bst.searchPath(cpf);
        if (pathData.isEmpty()) { lblStatus.setText("Árvore vazia."); return; }

        boolean found = pathData.get(pathData.size() - 1).getKey().equals(cpf);
        List<NodeBstVisual> pathVisual = buildVisualPath(pathData, rootVisual, cpf);

        lblStatus.setText(found
                ? "Removendo " + formatCpf(cpf) + "..."
                : "✘ " + formatCpf(cpf) + " não encontrado.");

        tfCpf.clear();

        treePane.removeAnimation(pathVisual, cpf, () -> {
            if (found) {
                bst.delete(cpf);
                rootVisual = buildVisual(bst.getRoot());
                treePane.setRoot(rootVisual);
                centerScroll();
                lblStatus.setText("CPF " + formatCpf(cpf) + " removido. Total: " + bst.getAmount());
            }
        });
    }

    // -- Helpers --------------------------------

    /**
     * Centraliza o scroll horizontalmente após cada mudança na árvore,
     * para a raiz ficar sempre visível no centro.
     */
    private void centerScroll() {
        // runLater garante que o layout já foi calculado antes de mover o scroll
        javafx.application.Platform.runLater(() -> scrollPane.setHvalue(0.5));
    }

    private NodeBstVisual buildVisual(NodeBst node) {
        if (node == null) return null;
        NodeBstVisual visual = new NodeBstVisual(node.getKey());
        visual.left  = buildVisual(node.getLeft());
        visual.right = buildVisual(node.getRight());
        return visual;
    }

    private List<NodeBstVisual> buildVisualPath(List<NodeBst> pathData, NodeBstVisual visualRoot, String targetCpf) {
        List<NodeBstVisual> pathVisual = new ArrayList<>();
        NodeBstVisual current = visualRoot;

        for (int i = 0; i < pathData.size(); i++) {
            if (current == null) break;
            pathVisual.add(current);
            // Navega pelo CPF alvo — espelha exatamente o que a BST fez
            int comparison = targetCpf.compareTo(current.cpf);
            if      (comparison < 0) current = current.left;
            else if (comparison > 0) current = current.right;
            else break;
        }

        return pathVisual;
    }

    private String formatCpf(String cpf) {
        return String.format("%s.%s.%s-%s",
                cpf.substring(0,3), cpf.substring(3,6),
                cpf.substring(6,9), cpf.substring(9,11));
    }
}