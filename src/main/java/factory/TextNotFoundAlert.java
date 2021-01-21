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
public class TextNotFoundAlert implements Alert {

    @Override
    public void alert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Not Found");
        alert.setHeaderText("Text not found");
        alert.setContentText("Text not found");
        alert.showAndWait();
    }

}
