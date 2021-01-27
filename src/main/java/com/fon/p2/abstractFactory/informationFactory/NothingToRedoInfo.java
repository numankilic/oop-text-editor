package com.fon.p2.abstractFactory.informationFactory;


public class NothingToRedoInfo implements Information {

  
    @Override
    public void info() {  
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Nothing to Redo");
        alert.setHeaderText("Nothing to Redo");
        alert.setContentText("Nothing to Redo");
        alert.showAndWait();
    }
    
    
}
