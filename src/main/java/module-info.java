module com.CVC.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.CVC.sudoku to javafx.fxml;
    exports com.CVC.sudoku;
}