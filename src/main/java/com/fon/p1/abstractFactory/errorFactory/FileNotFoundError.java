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
