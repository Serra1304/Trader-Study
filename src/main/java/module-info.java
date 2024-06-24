module es.gtorresdev.traderstudy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires spring.context;

    opens es.gtorresdev.traderstudy to javafx.fxml;
    opens es.gtorresdev.traderstudy.controllers to javafx.fxml;
    opens es.gtorresdev.traderstudy.models to javafx.base;

    exports es.gtorresdev.traderstudy;
    exports es.gtorresdev.traderstudy.exceptions;
    exports es.gtorresdev.traderstudy.controllers;
    exports es.gtorresdev.traderstudy.services;
    exports es.gtorresdev.traderstudy.models;
}