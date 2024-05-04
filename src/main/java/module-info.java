module eliteexchange.eliteexchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires de.jensd.fx.glyphs.fontawesome;

    opens eliteexchange.eliteexchange to javafx.fxml;
    exports eliteexchange.eliteexchange;
    exports ApplicationElite;
    opens ApplicationElite to javafx.fxml;
}