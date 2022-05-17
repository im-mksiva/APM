module apm.apm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.csv;
    requires MaterialFX;
    requires passay;


    opens apm.apm to javafx.fxml;
    exports apm.apm;
}