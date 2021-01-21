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
public class AlertFactory {

    public Alert getAlert(String alertType) {
        if (alertType == null) {
            return null;
        }
        if (alertType.equalsIgnoreCase("TextNotFound")) {
            return new TextNotFoundAlert();

        } else if (alertType.equalsIgnoreCase("FileNotFound")) {
            return new FileNotFoundAlert();

        } else if (alertType.equalsIgnoreCase("FileReadError")) {
            return new FileReadErrorAlert();

        } else if (alertType.equalsIgnoreCase("FileSaveError")) {
            return new FileSaveErrorAlert();

        }

        return null;
    }
}
