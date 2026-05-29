package view;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.NodeBstVisual;
import javafx.animation.FadeTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * Painel que renderiza e anima uma BST de NodeBstVisual.
 */
public class TreePane extends Pane {

    private static final double NODE_W   = 154;  // largura do StackPane do nó
    private static final double NODE_H   =  36;  // altura do StackPane do nó
    private static final double LEVEL_H  =  70;  // distância vertical entre níveis
    private static final double MIN_GAP  =  20;  // espaço mínimo entre nós irmãos

    private NodeBstVisual root;



    public void setRoot(NodeBstVisual root) {
        this.root = root;
        redraw();
    }

    /** Anima a busca: laranja para o caminho, verde/vermelho no destino. */
    public void searchAnimation(List<NodeBstVisual> path, String targetCpf) {
        resetAllColors(root);

        Timeline timeline = new Timeline();

        for (int i = 0; i < path.size(); i++) {
            NodeBstVisual node = path.get(i);
            int step = i;

            KeyFrame kf = new KeyFrame(Duration.millis(step * 600L), e -> {
                String cpf = node.cpf;
                if (cpf.equals(targetCpf)) {
                    node.setColor("#4CAF50");   // verde — encontrado
                } else {
                    node.setColor("#FF9800");   // laranja — caminho percorrido
                }
            });
            timeline.getKeyFrames().add(kf);
        }

        // se o último nó não é o alvo → vermelho (não encontrado)
        if (!path.isEmpty() && !path.get(path.size() - 1).cpf.equals(targetCpf)) {
            NodeBstVisual lastNode = path.get(path.size() - 1);
            KeyFrame kf = new KeyFrame(Duration.millis(path.size() * 600L), e ->
                    lastNode.setColor("#F44336")    // vermelho — não encontrado
            );
            timeline.getKeyFrames().add(kf);
        }

        timeline.play();
    }

    /**
     * Anima a remoção em duas fases:
     * 1. Busca animada (laranja → verde no alvo)
     * 2. FadeTransition no nó encontrado → chama onDeleted ao terminar
     *
     * Se o CPF não for encontrado, pinta vermelho e chama onDeleted imediatamente.
     */
    public void removeAnimation(List<NodeBstVisual> path, String targetCpf, Runnable onDeleted) {
        resetAllColors(root);

        Timeline timelineSearch = new Timeline();

        for (int i = 0; i < path.size(); i++) {
            NodeBstVisual node = path.get(i);
            KeyFrame kf = new KeyFrame(Duration.millis(i * 600L), e -> {
                if (node.cpf.equals(targetCpf)) {
                    node.setColor("#4CAF50");  // verde — encontrado
                } else {
                    node.setColor("#FF9800");  // laranja — caminho
                }
            });
            timelineSearch.getKeyFrames().add(kf);
        }

        NodeBstVisual lastNode = path.isEmpty() ? null : path.get(path.size() - 1);
        boolean found = lastNode != null && lastNode.cpf.equals(targetCpf);

        if (!found) {
            // Não achou — pinta vermelho e termina
            if (lastNode != null) {
                timelineSearch.getKeyFrames().add(
                        new KeyFrame(Duration.millis(path.size() * 600L), e ->
                                lastNode.setColor("#F44336")
                        )
                );
            }
            timelineSearch.setOnFinished(e -> onDeleted.run());
        } else {
            // Achou, então após a busca, faz fade no nó e chama onDeleted
            timelineSearch.setOnFinished(e -> {
                FadeTransition fade = new FadeTransition(Duration.millis(500), lastNode.nodePane);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(fe -> {
                    lastNode.nodePane.setOpacity(1.0); // reseta para o próximo redraw
                    onDeleted.run();
                });
                fade.play();
            });
        }

        timelineSearch.play();
    }


    public void insertAnimation(List<NodeBstVisual> path, String newCpf, Runnable onInserted) {
        resetAllColors(root);

        Timeline timeLineWay = new Timeline();

        for (int i = 0; i < path.size(); i++) {
            NodeBstVisual node = path.get(i);
            KeyFrame kf = new KeyFrame(Duration.millis(i * 600L), e ->
                    node.setColor("#FF9800")
            );
            timeLineWay.getKeyFrames().add(kf);
        }

        timeLineWay.setOnFinished(e -> {
            onInserted.run();

            NodeBstVisual newNode = searchVisual(root, newCpf);
            if (newNode != null) {
                newNode.setColor("#2E7D32");
                newNode.nodePane.setScaleX(0);
                newNode.nodePane.setScaleY(0);

                javafx.animation.ScaleTransition scale =
                        new javafx.animation.ScaleTransition(Duration.millis(400), newNode.nodePane);
                scale.setToX(1);
                scale.setToY(1);
                scale.play();
            }
        });

        timeLineWay.play();
    }

    private NodeBstVisual searchVisual(NodeBstVisual node, String cpf) {
        if (node == null) return null;
        int cmp = cpf.compareTo(node.cpf);
        if (cmp == 0) return node;
        if (cmp < 0)  return searchVisual(node.left, cpf);
        return searchVisual(node.right, cpf);
    }



    /** Chamado quando a árvore muda. Redesenha e ajusta o tamanho do painel. */
    public void redraw() {
        getChildren().clear();
        if (root == null) {
            setPrefSize(0, 0);
            return;
        }

        // Passo 1: posiciona a raiz em x=0 e desenha com coordenadas temporárias
        positionCalculator(root, 0, 20);

        // Passo 2: mede o bounding box real de todos os nós
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        for (NodeBstVisual n : nodeCollect(root)) {
            minX = Math.min(minX, n.x);
            maxX = Math.max(maxX, n.x + NODE_W);
            maxY = Math.max(maxY, n.y + NODE_H);
        }

        // Passo 3: translada tudo para que nenhum nó fique com x negativo
        double margin  = MIN_GAP * 2;
        double offsetX = margin - minX;
        double paneWidth  = maxX - minX + margin * 2;
        double paneHeight = maxY + margin;
        setPrefSize(paneWidth, paneHeight);

        // Passo 4: renderiza com as coordenadas corrigidas
        render(root, offsetX);
    }

    /** Calcula x,y de cada nó recursivamente sem adicionar ao painel. */
    private void positionCalculator(NodeBstVisual node, double x, double y) {
        if (node == null) return;
        node.x = x - NODE_W / 2.0;
        node.y = y;

        if (node.left != null) {
            double lw = subtreeWidth(node.left);
            positionCalculator(node.left, x - lw / 2.0 - MIN_GAP / 2.0, y + LEVEL_H);
        }
        if (node.right != null) {
            double rw = subtreeWidth(node.right);
            positionCalculator(node.right, x + rw / 2.0 + MIN_GAP / 2.0, y + LEVEL_H);
        }
    }

    /** Aplica offsetX e adiciona linhas + nós ao painel. */
    private void render(NodeBstVisual node, double offsetX) {
        if (node == null) return;

        double cx = node.x + NODE_W / 2.0 + offsetX;
        double cy = node.y;

        node.nodePane.setLayoutX(node.x + offsetX);
        node.nodePane.setLayoutY(cy);

        if (node.left != null) {
            double lcx = node.left.x + NODE_W / 2.0 + offsetX;
            Line line = new Line(cx, cy + NODE_H, lcx, node.left.y);
            line.getStyleClass().add("tree-edge");
            getChildren().add(line);
            render(node.left, offsetX);
        }
        if (node.right != null) {
            double rcx = node.right.x + NODE_W / 2.0 + offsetX;
            Line line = new Line(cx, cy + NODE_H, rcx, node.right.y);
            line.getStyleClass().add("tree-edge");
            getChildren().add(line);
            render(node.right, offsetX);
        }

        getChildren().add(node.nodePane);
    }

    /** Coleta todos os nós da árvore em uma lista. */
    private List<NodeBstVisual> nodeCollect(NodeBstVisual node) {
        List<NodeBstVisual> list = new ArrayList<>();
        nodeCollectRecursive(node, list);
        return list;
    }
    private void nodeCollectRecursive(NodeBstVisual node, List<NodeBstVisual> list) {
        if (node == null) return;
        list.add(node);
        nodeCollectRecursive(node.left, list);
        nodeCollectRecursive(node.right, list);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    /**
     * Retorna a largura total ocupada pela sub-árvore enraizada em node.
     * É a base do layout adaptativo: pais se posicionam em função
     * da largura real dos filhos, não de um offset fixo.
     */
    private double subtreeWidth(NodeBstVisual node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return NODE_W;

        double leftWidth = node.left  != null ? subtreeWidth(node.left)  : 0;
        double rightWidth = node.right != null ? subtreeWidth(node.right) : 0;

        if (node.left  == null) return rightWidth + NODE_W / 2.0 + MIN_GAP;
        if (node.right == null) return leftWidth + NODE_W / 2.0 + MIN_GAP;

        return leftWidth + rightWidth + MIN_GAP;
    }

    /** Percorre toda a árvore e reseta as cores dos nós. */
    private void resetAllColors(NodeBstVisual node) {
        if (node == null) return;
        node.resetColor();
        resetAllColors(node.left);
        resetAllColors(node.right);
    }
}