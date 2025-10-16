package com.CVC.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller class for the Sudoku game interface.
 * Handles user interactions, manages the game state, and coordinates between
 * the view (FXML) and the model (game logic).
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
public class Controller {
    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Label messageLabel;

    private Model model;
    private TextField[][] textFields;
    private Solver solver;

    /**
     * Initializes the controller after the FXML fields have been injected.
     * Sets up the game model, solver, and initializes the game board.
     */
    @FXML
    public void initialize() {
        model = new Model();
        solver = new Solver();
        textFields = new TextField[6][6];
        initializeBoard();
        messageLabel.setText("Welcome to Sudoku 6x6! Enter numbers from 1 to 6.");
    }

    /**
     * Initializes the game board UI by creating and configuring text fields
     * for each cell in the 6x6 grid.
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
     * Creates and configures a text field for a specific cell in the Sudoku grid.
     *
     * @param row the row index of the cell (0-5)
     * @param col the column index of the cell (0-5)
     * @return the configured TextField ready for user input
     */
    private TextField createTextField(int row, int col) {
        TextField field = new TextField();
        field.setPrefSize(50, 50);
        field.setStyle("-fx-font-size: 18; -fx-alignment: center;");

        int value = model.getCellValue(row, col);
        if (value != 0) {
            field.setText(String.valueOf(value));
            if (model.isCellFixed(row, col)) {
                field.setStyle("-fx-font-size: 18; -fx-alignment: center; -fx-background-color: #f0f0f0; -fx-font-weight: bold;");
                field.setEditable(false);
            }
        }

        final int currentRow = row;
        final int currentCol = col;

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            handleUserInput(currentRow, currentCol, newValue);
        });

        return field;
    }

    /**
     * Handles user input in a specific cell, validating the input and updating the model.
     *
     * @param row the row index of the cell being edited
     * @param col the column index of the cell being edited
     * @param input the text input from the user
     */
    private void handleUserInput(int row, int col, String input) {
        if (model.isCellFixed(row, col)) {
            return; // Prevent editing fixed cells
        }

        if (input.isEmpty()) {
            // Cell cleared
            model.setCellValue(row, col, 0);
            updateCellStyle(row, col, "normal");
        } else {
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 6) {
                    if (model.setCellValue(row, col, value)) {
                        // Valid move
                        updateCellStyle(row, col, "valid");
                        messageLabel.setText("");

                        // Check if game is complete
                        if (model.isCompleteAndCorrect()) {
                            messageLabel.setText("Congratulations! You have completed the Sudoku correctly!");
                        }
                    } else {
                        // Invalid move
                        updateCellStyle(row, col, "invalid");
                        messageLabel.setText("Invalid number. Check row, column and block.");
                    }
                } else {
                    // Number out of range
                    textFields[row][col].setText("");
                    messageLabel.setText("Only numbers from 1 to 6 are allowed.");
                }
            } catch (NumberFormatException e) {
                // Non-numeric input
                textFields[row][col].setText("");
                messageLabel.setText("Only numbers are allowed.");
            }
        }
    }

    /**
     * Updates the visual style of a cell based on its state.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param type the type of styling to apply ("valid", "invalid", "suggestion", or "normal")
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

    /**
     * Provides help to the user by suggesting a valid number for an empty cell.
     * Finds the first empty cell and fills it with a valid suggestion.
     */
    @FXML
    private void handleHelp() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (model.getCellValue(row, col) == 0 && !model.isCellFixed(row, col)) {
                    int suggestion = model.getHelp(row, col);
                    if (suggestion != 0) {
                        model.setCellValue(row, col, suggestion);
                        textFields[row][col].setText(String.valueOf(suggestion));
                        updateCellStyle(row, col, "suggestion");
                        messageLabel.setText("Suggestion applied at row " + (row + 1) + ", column " + (col + 1));
                    } else {
                        messageLabel.setText("Cannot find a valid suggestion for this cell.");
                    }
                    return;
                }
            }
        }
        messageLabel.setText("No empty cells to suggest! The board might be complete.");
    }

    /**
     * Starts a new game by resetting the model and reinitializing the board.
     */
    @FXML
    private void handleNewGame() {
        model = new Model();
        initializeBoard();
        messageLabel.setText("New game started. Good luck!");
    }

    /**
     * Verifies the current state of the board and provides feedback to the user.
     */
    @FXML
    private void handleVerify() {
        if (model.isComplete()) {
            if (model.isCompleteAndCorrect()) {
                messageLabel.setText("Correct! The board is complete and valid.");
            } else {
                messageLabel.setText("The board is complete but contains errors.");
            }
        } else {
            messageLabel.setText("The board is not complete. Keep playing.");
        }
    }

    /**
     * Resets the board while keeping the fixed cells intact.
     * Clears all user-entered values from non-fixed cells.
     */
    @FXML
    private void handleReset() {
        model.resetBoard();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (!model.isCellFixed(row, col)) {
                    textFields[row][col].setText("");
                    updateCellStyle(row, col, "normal");
                }
            }
        }
        messageLabel.setText("Board reset. Fixed cells are preserved.");
    }
}