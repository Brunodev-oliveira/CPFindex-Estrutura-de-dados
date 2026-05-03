package com.cpfindex;
import controller.ReceitaController;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main {
    public static void main(String[] args) {
        System.out.println("===SISTEMA RECEITA FEDERAL===");
        System.out.println("Demonstração de uma BST");

        ReceitaController controller = new ReceitaController();
        controller.start();


    }
}