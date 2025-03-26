module com.foro {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;

    opens com.foro to javafx.fxml;
    exports com.foro;
}