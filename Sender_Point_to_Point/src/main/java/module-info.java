module com.example.sender_point_to_point {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires log4j;
    requires java.naming;
    requires javax.jms.api;
    requires jaxb.api;

    opens com.example.sender_point_to_point to javafx.fxml;
    exports com.example.sender_point_to_point;
}