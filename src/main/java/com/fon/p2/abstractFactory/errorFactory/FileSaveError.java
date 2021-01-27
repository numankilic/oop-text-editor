package com.fon.p2.abstractFactory.errorFactory;


public class FileSaveError implements Error {

    @Override
    public void error() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Error!");
        alert.setContentText("Error while saving file!");
        alert.showAndWait();
    }
}
