module es.gtorresdev.traderstudy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens es.gtorresdev.traderstudy to javafx.fxml;
    exports es.gtorresdev.traderstudy;
}