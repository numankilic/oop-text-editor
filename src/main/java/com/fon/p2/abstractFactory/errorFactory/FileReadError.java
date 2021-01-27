package com.fon.p2.abstractFactory.errorFactory;


public class FileReadError implements Error{

    @Override
    public void error() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("File read error!");
        alert.setHeaderText("File read error!");
        alert.setContentText("Error while reading file!");
        alert.showAndWait();
    }
}
