package com.fon.p2.abstractFactory.informationFactory;


public class TextNotFoundInfo implements Information {

    @Override
    public void info() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Not Found");
        alert.setHeaderText("Text not found");
        alert.setContentText("Text not found");
        alert.showAndWait();
    }

}
