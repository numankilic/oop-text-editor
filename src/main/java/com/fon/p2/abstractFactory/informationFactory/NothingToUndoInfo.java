package com.fon.p2.abstractFactory.informationFactory;


public class NothingToUndoInfo implements Information{

    @Override
    public void info() {  
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Nothing to Undo");
        alert.setHeaderText("Nothing to Undo");
        alert.setContentText("Nothing to Undo");
        alert.showAndWait();
    }
    
}
