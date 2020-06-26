module SharifAP {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jfoenix;

    opens JavaProject to javafx.fxml;
    opens JavaProject.Controller to javafx.fxml, javafx.base;
    opens JavaProject.Model to javafx.base;
    opens JavaProject.Model.Account to com.google.gson, javafx.base;
    opens JavaProject.Model.Discount to com.google.gson, javafx.base;
    opens JavaProject.Model.Request to com.google.gson, javafx.base;
    opens JavaProject.Model.ProductOrganization to com.google.gson, javafx.base;
    opens JavaProject.Model.Log to com.google.gson, javafx.base;
    exports JavaProject;
    exports JavaProject.Model.Account;
    exports JavaProject.Model.ProductOrganization;
    exports JavaProject.Model.Discount;
    exports JavaProject.Model.Request;
    exports JavaProject.Model.Database;
}