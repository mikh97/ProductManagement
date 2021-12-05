module com.cs157afall21.productmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.cs157afall21.productmanagement to javafx.fxml;
    exports com.cs157afall21.productmanagement;
}