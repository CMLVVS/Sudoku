package com.CVC.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controlador {
    @FXML
    private GridPane tableroGrid;

    @FXML
    private Label etiquetaMensaje;

    @FXML
    public void initialize() {
        etiquetaMensaje.setText("¡Juego listo! Haz clic en Nuevo Juego para comenzar.");
    }

    @FXML
    private void manejarAyuda() {
        etiquetaMensaje.setText("Función de ayuda - Por implementar");
    }

    @FXML
    private void manejarNuevoJuego() {
        etiquetaMensaje.setText("Nuevo juego iniciado - Por implementar");
    }

    @FXML
    private void manejarVerificacion() {
        etiquetaMensaje.setText("Verificando tablero - Por implementar");
    }
}
