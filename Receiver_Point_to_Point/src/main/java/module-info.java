module com.example.receiver_point_to_point {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.naming;
    requires log4j;
    requires javax.jms.api;
    requires jaxb.api;

    opens com.example.receiver_point_to_point to javafx.fxml;
    exports com.example.receiver_point_to_point;
}