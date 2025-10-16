package com.CVC.sudoku;

/**
 * Represents a single cell in the Sudoku grid.
 * Contains information about the cell's value, position, and fixed status.
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
public class Cell {
    private int value;
    private boolean isFixed;
    private int row;
    private int col;
    private boolean isIncorrect;

    /**
     * Constructs a new empty cell at the specified position.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
        this.isFixed = false;
        this.isIncorrect = false;
    }

    /**
     * Constructs a new cell with specified attributes.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the initial value of the cell
     * @param isFixed whether the cell is fixed (non-editable)
     */
    public Cell(int row, int col, int value, boolean isFixed) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isFixed = isFixed;
        this.isIncorrect = false;
    }

    /**
     * Gets the current value of the cell.
     *
     * @return the cell value (0 for empty)
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the cell.
     *
     * @param value the new value for the cell
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Checks if the cell is fixed (non-editable).
     *
     * @return true if the cell is fixed, false otherwise
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * Sets the fixed status of the cell.
     *
     * @param isFixed true to make the cell fixed, false to make it editable
     */
    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    /**
     * Gets the row index of the cell.
     *
     * @return the row index (0-5)
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of the cell.
     *
     * @return the column index (0-5)
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if the cell contains an incorrect value.
     *
     * @return true if the cell value is incorrect, false otherwise
     */
    public boolean isIncorrect() {
        return isIncorrect;
    }

    /**
     * Sets the incorrect status of the cell.
     *
     * @param isIncorrect true if the cell value is incorrect, false otherwise
     */
    public void setIncorrect(boolean isIncorrect) {
        this.isIncorrect = isIncorrect;
    }

    /**
     * Checks if the cell is empty (value is 0).
     *
     * @return true if the cell is empty, false otherwise
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Returns a string representation of the cell's value.
     *
     * @return the cell value as a string, or empty string if value is 0
     */
    @Override
    public String toString() {
        return value == 0 ? "" : String.valueOf(value);
    }
}