package model;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class NodeBstVisual {
    public String cpf;
    public NodeBstVisual left, right;
    public double x, y;
    public double relX;   // deslocamento relativo ao pai — usado pelo layout
    public StackPane nodePane;
    private Rectangle rect;

    public NodeBstVisual(String cpf) {
        this.cpf = cpf;

        rect = new Rectangle(150, 36);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.getStyleClass().add("node-rect");

        Label label = new Label(formatCpf(cpf));
        label.getStyleClass().add("node-label");

        nodePane = new StackPane(rect, label);
    }

    public void setColor(String cssColor) {
        rect.setStyle("-fx-fill: " + cssColor + ";");
    }

    public void resetColor() {
        rect.setStyle("");
        rect.getStyleClass().clear();
        rect.getStyleClass().add("node-rect");
    }

    private String formatCpf(String cpf) {
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3), cpf.substring(3, 6),
                cpf.substring(6, 9), cpf.substring(9, 11));
    }
}