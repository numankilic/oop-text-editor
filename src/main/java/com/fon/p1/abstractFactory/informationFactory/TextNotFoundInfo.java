/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory.informationFactory;

/**
 *
 * @author pepper
 */
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
