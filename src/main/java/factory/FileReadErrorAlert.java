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
public class FileReadErrorAlert implements Alert{

    @Override
    public void alert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("File read error!");
        alert.setHeaderText("File read error!");
        alert.setContentText("Error while reading file!");
        alert.showAndWait();
    }
}
