/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

/**
 *
 * @author pepper
 */
public class FileNotFoundAlert implements Alert {

    @Override
    public void alert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("File not found!");
        alert.setHeaderText("File not found!");
        alert.setContentText("Error while attempting to open file!");
        alert.showAndWait();
    }

}
