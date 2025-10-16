package com.CVC.sudoku;

import java.util.Random;

/**
 * Model class representing the Sudoku game logic and state.
 * Handles the game rules, validation, and board management for the 6x6 Sudoku game.
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
public class Model {
    private int[][] board;
    private boolean[][] fixedCells;
    private Random random;

    /**
     * Constructs a new Sudoku model and initializes the game board.
     */
    public Model() {
        board = new int[6][6];
        fixedCells = new boolean[6][6];
        random = new Random();
        initializeGame();
    }

    /**
     * Initializes the game board with a predefined starting pattern.
     * Sets up fixed cells that cannot be modified by the player.
     */
    private void initializeGame() {
        // Clear board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
                fixedCells[i][j] = false;
            }
        }

        // Initial game pattern
        int[][] initialPattern = {
                {1, 0, 2, 0, 0, 0},
                {0, 2, 0, 5, 0, 0},
                {0, 3, 4, 0, 0, 0},
                {5, 0, 0, 6, 0, 0},
                {0, 6, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 3}
        };

        // Apply pattern and mark fixed cells
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (initialPattern[i][j] != 0) {
                    board[i][j] = initialPattern[i][j];
                    fixedCells[i][j] = true;
                }
            }
        }
    }

    /**
     * Sets a value in the specified cell if the move is valid.
     *
     * @param row the row index (0-5)
     * @param col the column index (0-5)
     * @param value the value to set (1-6)
     * @return true if the move was valid and applied, false otherwise
     */
    public boolean setCellValue(int row, int col, int value) {
        if (isValidMove(row, col, value)) {
            board[row][col] = value;
            return true;
        }
        return false;
    }

    /**
     * Checks if a move is valid according to Sudoku rules.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int row, int col, int value) {
        // Check if cell is fixed
        if (fixedCells[row][col]) return false;

        // Check if value is in range
        if (value < 1 || value > 6) return false;

        // Check row
        for (int i = 0; i < 6; i++) {
            if (i != col && board[row][i] == value) return false;
        }

        // Check column
        for (int i = 0; i < 6; i++) {
            if (i != row && board[i][col] == value) return false;
        }

        // Check 2x3 block
        int blockRow = (row / 2) * 2;
        int blockCol = (col / 3) * 3;

        for (int i = blockRow; i < blockRow + 2; i++) {
            for (int j = blockCol; j < blockCol + 3; j++) {
                if (i != row && j != col && board[i][j] == value) return false;
            }
        }

        return true;
    }

    /**
     * Provides a valid suggestion for an empty cell.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return a valid number suggestion, or 0 if no valid suggestion exists
     */
    public int getHelp(int row, int col) {
        for (int num = 1; num <= 6; num++) {
            if (isValidMove(row, col, num)) {
                return num;
            }
        }
        return 0;
    }

    /**
     * Checks if the board is completely filled and follows all Sudoku rules.
     *
     * @return true if the board is complete and correct, false otherwise
     */
    public boolean isCompleteAndCorrect() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 0) {
                    return false; // Empty cells
                }
                if (!isValidMove(i, j, board[i][j]) && !fixedCells[i][j]) {
                    return false; // Invalid numbers
                }
            }
        }
        return true;
    }

    /**
     * Checks if all cells in the board are filled (regardless of correctness).
     *
     * @return true if all cells have values, false otherwise
     */
    public boolean isComplete() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets the value of a specific cell.
     *
     * @param row the row index
     * @param col the column index
     * @return the value at the specified cell (0 for empty)
     */
    public int getCellValue(int row, int col) {
        return board[row][col];
    }

    /**
     * Checks if a cell is fixed (pre-filled and non-editable).
     *
     * @param row the row index
     * @param col the column index
     * @return true if the cell is fixed, false otherwise
     */
    public boolean isCellFixed(int row, int col) {
        return fixedCells[row][col];
    }

    /**
     * Gets a copy of the current game board.
     *
     * @return a 6x6 array representing the current board state
     */
    public int[][] getBoard() {
        return board.clone();
    }

    /**
     * Resets the board by clearing all non-fixed cells.
     */
    public void resetBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!fixedCells[i][j]) {
                    board[i][j] = 0;
                }
            }
        }
    }
}