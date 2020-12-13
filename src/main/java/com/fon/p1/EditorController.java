package com.fon.p1;

import java.awt.FileDialog;
import java.io.BufferedReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private TextArea textArea;
    
    public void initialize() {
        textArea.setWrapText(true);
    }
    
    public void openFile(){
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
                    textArea.setText(sb.toString());
                }
                reader.close();
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save file");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        
        File textFile = fileChooser.showSaveDialog(controllerStage);
        
        try {
            FileWriter writer = new FileWriter(textFile);
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
    
    public void saveAsFile() {
        
    }
    
    
    public void closeFile() {
        System.exit(0);
    }
    
    public void newFile() {
        textArea.setText("");
    }
    
    
}
