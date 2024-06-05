module org.ais {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens org.ais to javafx.fxml;
    exports org.ais;
    exports org.ais.controller;
    opens org.ais.controller to javafx.fxml;
    exports org.ais.model;
    opens org.ais.model to javafx.fxml;
    exports org.ais.restHandler;
    opens org.ais.restHandler to javafx.fxml;
    exports org.ais.util.validators;
    opens org.ais.util.validators to javafx.fxml;
    exports org.ais.util.routing;
    opens org.ais.util.routing to javafx.fxml;

}