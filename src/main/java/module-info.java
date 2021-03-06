module com.pwm.argos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.apache.commons.codec;
    requires org.beryx.textio;
    requires java.datatransfer;
    requires java.desktop;

    opens com.pwm.argos to javafx.fxml, com.google.gson;
    exports com.pwm.argos;
}