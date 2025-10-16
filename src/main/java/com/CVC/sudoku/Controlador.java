package com.CVC.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controlador principal del juego Sudoku
 */
public class Controlador {
    @FXML
    private GridPane tableroGrid;

    @FXML
    private Label etiquetaMensaje;

    private modelo modelo;
    private TextField[][] camposTexto;
    private solver solver;

    @FXML
    public void initialize() {
        modelo = new modelo();
        solver = new solver();
        camposTexto = new TextField[6][6];
        inicializarTablero();
        etiquetaMensaje.setText("¡Bienvenido al Sudoku 6x6! Escribe números del 1 al 6.");
    }

    /**
     * Inicializa la interfaz gráfica del tablero
     */
    private void inicializarTablero() {
        tableroGrid.getChildren().clear();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextField campoTexto = crearCampoTexto(fila, columna);
                tableroGrid.add(campoTexto, columna, fila);
                camposTexto[fila][columna] = campoTexto;
            }
        }
    }

    /**
     * Crea un campo de texto para una celda del tablero
     */
    private TextField crearCampoTexto(int fila, int columna) {
        TextField campo = new TextField();
        campo.setPrefSize(50, 50);
        campo.setStyle("-fx-font-size: 18; -fx-alignment: center;");

        int valor = modelo.obtenerValor(fila, columna);
        if (valor != 0) {
            campo.setText(String.valueOf(valor));
            if (modelo.esCeldaFija(fila, columna)) {
                campo.setStyle("-fx-font-size: 18; -fx-alignment: center; -fx-background-color: #f0f0f0; -fx-font-weight: bold;");
                campo.setEditable(false);
            }
        }

        // Manejar eventos de entrada
        final int filaActual = fila;
        final int columnaActual = columna;

        campo.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            manejarEntradaUsuario(filaActual, columnaActual, valorNuevo);
        });

        return campo;
    }

    /**
     * Maneja la entrada del usuario en una celda
     */
    private void manejarEntradaUsuario(int fila, int columna, String entrada) {
        if (modelo.esCeldaFija(fila, columna)) {
            return; // No permitir editar celdas fijas
        }

        if (entrada.isEmpty()) {
            // Celda vaciada
            modelo.establecerValor(fila, columna, 0);
            actualizarEstiloCelda(fila, columna, "normal");
        } else {
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= 1 && valor <= 6) {
                    if (modelo.establecerValor(fila, columna, valor)) {
                        // Movimiento válido
                        actualizarEstiloCelda(fila, columna, "valido");
                        etiquetaMensaje.setText("");

                        // Verificar si el juego está completo
                        if (modelo.estaCompletoYCorrecto()) {
                            etiquetaMensaje.setText("¡Felicidades! ¡Has completado el Sudoku correctamente!");
                        }
                    } else {
                        // Movimiento inválido
                        actualizarEstiloCelda(fila, columna, "invalido");
                        etiquetaMensaje.setText("Número inválido. Verifica fila, columna y bloque.");
                    }
                } else {
                    // Número fuera de rango
                    camposTexto[fila][columna].setText("");
                    etiquetaMensaje.setText("Solo se permiten números del 1 al 6.");
                }
            } catch (NumberFormatException e) {
                // Entrada no numérica
                camposTexto[fila][columna].setText("");
                etiquetaMensaje.setText("Solo se permiten números.");
            }
        }
    }

    /**
     * Actualiza el estilo visual de una celda
     */
    private void actualizarEstiloCelda(int fila, int columna, String tipo) {
        String estiloBase = "-fx-font-size: 18; -fx-alignment: center;";

        switch (tipo) {
            case "valido":
                camposTexto[fila][columna].setStyle(estiloBase + " -fx-text-fill: green;");
                break;
            case "invalido":
                camposTexto[fila][columna].setStyle(estiloBase + " -fx-text-fill: red;");
                break;
            case "sugerencia":
                camposTexto[fila][columna].setStyle(estiloBase + " -fx-text-fill: blue; -fx-font-style: italic;");
                break;
            default:
                camposTexto[fila][columna].setStyle(estiloBase);
                break;
        }
    }

    @FXML
    private void manejarAyuda() {
        // Buscar la primera celda vacía no fija
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (modelo.obtenerValor(fila, columna) == 0 && !modelo.esCeldaFija(fila, columna)) {
                    int sugerencia = modelo.obtenerSugerencia(fila, columna);
                    if (sugerencia != 0) {
                        modelo.establecerValor(fila, columna, sugerencia);
                        camposTexto[fila][columna].setText(String.valueOf(sugerencia));
                        actualizarEstiloCelda(fila, columna, "sugerencia");
                        etiquetaMensaje.setText("Sugerencia aplicada en fila " + (fila + 1) + ", columna " + (columna + 1));
                    } else {
                        etiquetaMensaje.setText("No se puede encontrar una sugerencia para esta celda.");
                    }
                    return;
                }
            }
        }
        etiquetaMensaje.setText("¡No hay celdas vacías para sugerir! El tablero puede estar completo.");
    }

    @FXML
    private void manejarNuevoJuego() {
        modelo = new modelo();
        inicializarTablero();
        etiquetaMensaje.setText("Nuevo juego iniciado. ¡Buena suerte!");
    }

    @FXML
    private void manejarVerificacion() {
        if (modelo.estaCompleto()) {
            if (modelo.estaCompletoYCorrecto()) {
                etiquetaMensaje.setText("¡Correcto! El tablero está completo y válido.");
            } else {
                etiquetaMensaje.setText("El tablero está completo pero contiene errores.");
            }
        } else {
            etiquetaMensaje.setText("El tablero no está completo. Sigue jugando.");
        }
    }

    @FXML
    private void manejarReinicio() {
        modelo.reiniciarTablero();
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (!modelo.esCeldaFija(fila, columna)) {
                    camposTexto[fila][columna].setText("");
                    actualizarEstiloCelda(fila, columna, "normal");
                }
            }
        }
        etiquetaMensaje.setText("Tablero reiniciado. Se conservan las celdas fijas.");
    }
}
