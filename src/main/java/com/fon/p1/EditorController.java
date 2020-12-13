package com.fon.p1;

import java.io.BufferedReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.scene.control.Alert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pepper
 */
public class EditorController {
    
    @FXML
    private Stage controllerStage;
    
    @FXML
    private TextArea taEditor;
    
    public void initialize() {
        taEditor.setWrapText(true);
    }
    
    @FXML
    public void openFile(){
        System.out.println("Here");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Sadece text dosyalarını seçebilmesi için filtre
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");

        // filtreyi dosya seçicisine uygulanıyor
        fileChooser.getExtensionFilters().add(extFilter);
        
        File textFile = fileChooser.showOpenDialog(controllerStage);
        
        if (textFile != null) {
            System.out.println(textFile.getAbsolutePath());
            
            try {
                
                BufferedReader reader = new BufferedReader(new FileReader(textFile.getAbsolutePath()));
                StringBuilder sb = new StringBuilder();
                
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(e.getLocalizedMessage());
                alert.setContentText(e.getMessage());
                
                alert.showAndWait();
            }
            
        }
        
    }
    
    public void saveFile() {
        
    }
    
    public void saveAsFile() {
        
    }
    
    public void closeFile() {
        
    }
    
    public void newFile() {
        
    }
    
}
