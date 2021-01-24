/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory.errorFactory;

/**
 *
 * @author pepper
 */
public class FileSaveErrorError implements Error {

    @Override
    public void error() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Error!");
        alert.setContentText("Error while saving file!");
        alert.showAndWait();
    }
}
