module org.gb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens org.gb to javafx.fxml;
    exports org.gb;
}