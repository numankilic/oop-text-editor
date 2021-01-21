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
public class FileSaveErrorAlert implements Alert {

    @Override
    public void alert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Error!");
        alert.setContentText("Error while saving file!");
        alert.showAndWait();
    }
}
