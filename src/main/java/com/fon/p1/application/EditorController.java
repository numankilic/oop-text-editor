package com.fon.p1.application;

import com.fon.p1.text_manipulation.TextManipulator;
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
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

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
    private AnchorPane editorAnchor;

    @FXML
    private TextArea textArea;

    private String filePath = "";

    private TextManipulator textManipulator = new TextManipulator();

    private int lastFindIndex = 0;
    private String lastSearched = "";

    // Aramayı baştan sona doğru yapacaksa true aksi halde false.
    private boolean isFindingDown = true;

    private void updateTitle(String title) {
        ((Stage) editorAnchor.getScene().getWindow()).setTitle("FON | TextEditor | " + title);
    }

    public void initialize() {
        textArea.setWrapText(true);
    }

    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        // Sadece text dosyalarını seçebilmesi için filtre
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");

        // filtreyi dosya seçicisine uygulanıyor
        fileChooser.getExtensionFilters().add(extFilter);

        File textFile = fileChooser.showOpenDialog(controllerStage);

        if (textFile != null) {

            this.filePath = textFile.getAbsolutePath();
            this.updateTitle(textFile.getName());

            try {
                BufferedReader reader = new BufferedReader(new FileReader(this.filePath));
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

        if (!this.filePath.equals("")) {
            try {
                FileWriter writer = new FileWriter(this.filePath);
                writer.write(textArea.getText());
                writer.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.getLocalizedMessage());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            this.saveAsFile();
        }

    }

    public void saveAsFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TEXT files (*.txt)", "*.txt"));

        File textFile = fileChooser.showSaveDialog(controllerStage);

        this.filePath = textFile.getAbsolutePath();
        this.updateTitle(textFile.getName());

        try {
            FileWriter writer = new FileWriter(textFile);
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void closeFile() {
        this.filePath = "";
        this.updateTitle("*");
        this.newFile();
    }

    public void newFile() {
        textArea.setText("");
    }

    public void onFindTextButtonClick() {

        // reset lastFindIndex
        lastFindIndex = 0;

        String textContent = textArea.getText();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("find_popup.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            FindPopupController fpController = fxmlLoader.getController();
            fpController.saveFindTextFunction((str) -> this.findText(str));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Find");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void findText(String what) {
        int from = textManipulator.findText(what, textArea.getText(), this.lastFindIndex);
        if(from == -1 && this.lastFindIndex != 0 || !this.lastSearched.equals(what)){
            from = textManipulator.findText(what, textArea.getText(), 0);
        }

        if (from != -1) {

            lastFindIndex = from + what.length();

            textArea.requestFocus();
            textArea.selectRange(from, this.lastFindIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("Text not found");
            alert.showAndWait();
        }
        
        lastSearched = what;

    }

}
