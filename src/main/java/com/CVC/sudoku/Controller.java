package com.CVC.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controlador principal del juego Sudoku
 */
public class Controller {
    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Label messageLabel;

    private Model model;
    private TextField[][] textFields;
    private Solver solver;

    @FXML
    public void initialize() {
        model = new Model();
        solver = new Solver();
        textFields = new TextField[6][6];
        initializeBoard();
        messageLabel.setText("¡Bienvenido al Sudoku 6x6! Escribe números del 1 al 6.");
    }

    /**
     * Inicializa la interfaz gráfica del tablero
     */
    private void initializeBoard() {
        sudokuGrid.getChildren().clear();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = createTextField(row, col);
                sudokuGrid.add(textField, col, row);
                textFields[row][col] = textField;
            }
        }
    }

    /**
     * Crea un campo de texto para una celda del tablero
     */
    private TextField createTextField(int row, int col) {
        TextField field = new TextField();
        field.setPrefSize(50, 50);
        field.setStyle("-fx-font-size: 18; -fx-alignment: center;");

        int value = model.getValue(row, col);
        if (value != 0) {
            field.setText(String.valueOf(value));
            if (model.isFixedCell(row, col)) {
                field.setStyle("-fx-font-size: 18; -fx-alignment: center; -fx-background-color: #f0f0f0; -fx-font-weight: bold;");
                field.setEditable(false);
            }
        }

        // Manejar eventos de entrada
        final int currentRow = row;
        final int currentCol = col;

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            handleUserInput(currentRow, currentCol, newValue);
        });

        return field;
    }

    /**
     * Maneja la entrada del usuario en una celda
     */
    private void handleUserInput(int row, int col, String input) {
        if (model.isFixedCell(row, col)) {
            return; // No permitir editar celdas fijas
        }

        if (input.isEmpty()) {
            // Celda vaciada
            model.setValue(row, col, 0);
            updateCellStyle(row, col, "normal");
        } else {
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 6) {
                    if (model.setValue(row, col, value)) {
                        // Movimiento válido
                        updateCellStyle(row, col, "valid");
                        messageLabel.setText("");

                        // Verificar si el juego está completo
                        if (model.isCompleteAndCorrect()) {
                            messageLabel.setText("¡Felicidades! ¡Has completado el Sudoku correctamente!");
                        }
                    } else {
                        // Movimiento inválido
                        updateCellStyle(row, col, "invalid");
                        messageLabel.setText("Número inválido. Verifica fila, columna y bloque.");
                    }
                } else {
                    // Número fuera de rango
                    textFields[row][col].setText("");
                    messageLabel.setText("Solo se permiten números del 1 al 6.");
                }
            } catch (NumberFormatException e) {
                // Entrada no numérica
                textFields[row][col].setText("");
                messageLabel.setText("Solo se permiten números.");
            }
        }
    }

    /**
     * Actualiza el estilo visual de una celda
     */
    private void updateCellStyle(int row, int col, String type) {
        String baseStyle = "-fx-font-size: 18; -fx-alignment: center;";

        switch (type) {
            case "valid":
                textFields[row][col].setStyle(baseStyle + " -fx-text-fill: green;");
                break;
            case "invalid":
                textFields[row][col].setStyle(baseStyle + " -fx-text-fill: red;");
                break;
            case "suggestion":
                textFields[row][col].setStyle(baseStyle + " -fx-text-fill: blue; -fx-font-style: italic;");
                break;
            default:
                textFields[row][col].setStyle(baseStyle);
                break;
        }
    }

    @FXML
    private void handleHelp() {
        // Buscar la primera celda vacía no fija
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (model.getValue(row, col) == 0 && !model.isFixedCell(row, col)) {
                    int suggestion = model.getSuggestion(row, col);
                    if (suggestion != 0) {
                        model.setValue(row, col, suggestion);
                        textFields[row][col].setText(String.valueOf(suggestion));
                        updateCellStyle(row, col, "suggestion");
                        messageLabel.setText("Sugerencia aplicada en fila " + (row + 1) + ", columna " + (col + 1));
                    } else {
                        messageLabel.setText("No se puede encontrar una sugerencia para esta celda.");
                    }
                    return;
                }
            }
        }
        messageLabel.setText("¡No hay celdas vacías para sugerir! El tablero puede estar completo.");
    }

    @FXML
    private void handleNewGame() {
        model = new Model();
        initializeBoard();
        messageLabel.setText("Nuevo juego iniciado. ¡Buena suerte!");
    }

    @FXML
    private void handleVerify() {
        if (model.isComplete()) {
            if (model.isCompleteAndCorrect()) {
                messageLabel.setText("¡Correcto! El tablero está completo y válido.");
            } else {
                messageLabel.setText("El tablero está completo pero contiene errores.");
            }
        } else {
            messageLabel.setText("El tablero no está completo. Sigue jugando.");
        }
    }

    @FXML
    private void handleReset() {
        model.resetBoard();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (!model.isFixedCell(row, col)) {
                    textFields[row][col].setText("");
                    updateCellStyle(row, col, "normal");
                }
            }
        }
        messageLabel.setText("Tablero reiniciado. Se conservan las celdas fijas.");
    }
}