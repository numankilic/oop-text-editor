package com.fon.p2.abstractFactory.errorFactory;


public class FileNotFoundError implements Error {

    @Override
    public void error() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("File not found!");
        alert.setHeaderText("File not found!");
        alert.setContentText("Error while attempting to open file!");
        alert.showAndWait();
    }

}
