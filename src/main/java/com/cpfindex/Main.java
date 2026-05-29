package com.cpfindex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/MainView.fxml")
        );
        Scene scene = new Scene(loader.load(), 1300, 900);
        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );
        stage.setTitle("CPF Index — Receita Federal");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}