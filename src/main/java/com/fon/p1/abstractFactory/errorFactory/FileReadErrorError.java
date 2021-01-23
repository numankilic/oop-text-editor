/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory.errorFactory;

import com.fon.p1.abstractFactory.errorFactory.Error;

/**
 *
 * @author pepper
 */
public class FileReadErrorError implements Error{

    @Override
    public void error() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("File read error!");
        alert.setHeaderText("File read error!");
        alert.setContentText("Error while reading file!");
        alert.showAndWait();
    }
}
