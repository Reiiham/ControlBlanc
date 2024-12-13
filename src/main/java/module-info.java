module ma.ensa.controlblanc {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens ma.ensa.controlblanc to javafx.fxml;
    exports ma.ensa.controlblanc;
}