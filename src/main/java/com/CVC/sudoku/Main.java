package com.CVC.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the Sudoku 6x6 game.
 * This class serves as the entry point for the JavaFX application.
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and sets up the primary stage.
     * Loads the FXML file and initializes the main game interface.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sudoku-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 700);
        stage.setTitle("Sudoku 6x6 - Universidad del Valle");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}