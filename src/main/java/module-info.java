module es.gtorresdev.traderstudy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires spring.context;
    requires java.desktop;
    requires javafx.swing;
    requires candleChart;
    requires reflections;
    requires org.jetbrains.annotations;
    requires spring.beans;

    opens es.gtorresdev.traderstudy to javafx.fxml;
    opens es.gtorresdev.traderstudy.controllers;
    opens es.gtorresdev.traderstudy.models to javafx.base;
    opens es.gtorresdev.traderstudy.services;


    exports es.gtorresdev.traderstudy;
    exports es.gtorresdev.traderstudy.indicators;
    exports es.gtorresdev.traderstudy.exceptions;
    exports es.gtorresdev.traderstudy.controllers;
    exports es.gtorresdev.traderstudy.services;
    exports es.gtorresdev.traderstudy.models;
    exports es.gtorresdev.traderstudy.utils;
    exports es.gtorresdev.traderstudy.models.input;
    opens es.gtorresdev.traderstudy.models.input to javafx.base;
}