module com.expansion.expansion {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.kordamp.bootstrapfx.core;

    opens com.expansion.ui to javafx.fxml;
    exports com.expansion.ui;
}