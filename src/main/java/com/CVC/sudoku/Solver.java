package com.CVC.sudoku;

/**
 * Solver class for Sudoku puzzles.
 * Contains algorithms for solving and generating Sudoku boards.
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
public class Solver {

    /**
     * Solves a Sudoku board using backtracking algorithm.
     *
     * @param board the 6x6 Sudoku board to solve
     * @return true if the board was solved successfully, false otherwise
     */
    public boolean solve(int[][] board) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 6; num++) {
                        if (isValidNumber(board, row, col, num)) {
                            board[row][col] = num;

                            if (solve(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a number can be placed at a specific position without violating Sudoku rules.
     *
     * @param board the current board state
     * @param row the row index to check
     * @param col the column index to check
     * @param num the number to validate
     * @return true if the number can be placed at the position, false otherwise
     */
    private boolean isValidNumber(int[][] board, int row, int col, int num) {
        // Check row
        for (int c = 0; c < 6; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < 6; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check 2x3 block
        int blockRow = (row / 2) * 2;
        int blockCol = (col / 3) * 3;

        for (int i = blockRow; i < blockRow + 2; i++) {
            for (int j = blockCol; j < blockCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Generates a valid Sudoku board.
     *
     * @return a 6x6 array representing a valid Sudoku board
     */
    public int[][] generateBoard() {
        int[][] board = new int[6][6];

        // Fill diagonal 2x3 blocks with valid numbers
        fillBlock(board, 0, 0);
        fillBlock(board, 2, 0);
        fillBlock(board, 4, 0);

        // Solve the rest of the board
        solve(board);

        return board;
    }

    /**
     * Fills a 2x3 block with numbers 1-6 without repetition.
     *
     * @param board the board to fill
     * @param startRow the starting row of the block
     * @param startCol the starting column of the block
     */
    private void fillBlock(int[][] board, int startRow, int startCol) {
        boolean[] used = new boolean[7]; // indices 1-6

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int num;
                do {
                    num = (int) (Math.random() * 6) + 1;
                } while (used[num]);

                board[i][j] = num;
                used[num] = true;
            }
        }
    }
}