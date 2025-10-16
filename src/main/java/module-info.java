/**
 * Module declaration for the Sudoku game application.
 * Defines the module dependencies and exports for the Java module system.
 *
 * @author Camilo Vivas Correa
 * @studentID 202439049
 * @institution Universidad del Valle
 * @version 1.0
 * @since 2025
 */
module com.CVC.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.CVC.sudoku to javafx.fxml;
    exports com.CVC.sudoku;
}