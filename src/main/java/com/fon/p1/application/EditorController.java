package com.fon.p1.application;

import com.fon.p1.text_manipulation.TextManipulator;
import java.awt.FileDialog;
import java.io.BufferedReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyledTextArea;

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
    private InlineCssTextArea textArea;

    @FXML
    private MenuItem undoButton;

    @FXML
    private MenuItem redoButton;

    private String filePath = "";

    private TextManipulator textManipulator;

    private int lastFindIndex = 0;
    private String lastSearched = "";

    // Aramayı baştan sona doğru yapacaksa true aksi halde false.
    private boolean isFindingDown = true;

    private int pressedKeyIndex;

    Stack<Integer> pressedKeyStackForUndo = new Stack<>();
    Stack<Integer> pressedKeyStackForRedo = new Stack<>();

    private void updateTitle(String title) {
        ((Stage) editorAnchor.getScene().getWindow()).setTitle("FON | TextEditor | " + title);
    }

    public void initialize() {
        textArea.setWrapText(true);
        textArea.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");
        try {
            this.textManipulator = new TextManipulator();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found!");
            alert.setContentText("Error while opening word list!");
            alert.showAndWait();
        }
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
                    textArea.replaceText(sb.toString());
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
        textArea.replaceText("");
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

    public void onReplaceButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("replace_popup.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ReplacePopupController rpController = fxmlLoader.getController();

            rpController.saveFindTextFunction((str) -> this.findText(str));
            rpController.saveReplaceFunction((current, replace) -> this.replaceText(current, replace));
            rpController.saveReplaceAllFunction((current, replace) -> this.replaceAllText(current, replace));

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Replace");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onSingleTranspositionButtonClick() {

        int left = 0;
        int right = 0;
        String currentText = textArea.getText();
        while (left < currentText.length()) {
            if (currentText.length() <= right || currentText.length() <= left) {
                if (left != right) {
                    this.validateSubStr(left, right);
                    left = right + 1;
                }
                continue;
            }
            char currentChar = currentText.charAt(right);
            if (!Character.isLetter(currentChar)) {

                if (Character.isDigit(currentChar)) {
                    while (!Character.isAlphabetic(currentChar)) {
                        right++;
                        if (currentText.length() <= right) {
                            break;
                        }
                        currentChar = currentText.charAt(right);
                    }
                    left = right;
                    continue;
                } else {
                    // değer tire ise devam et
                    if (currentChar == '-') {
                        right++;
                        continue;
                    }
                    // tire veya sayı değerinin olmadığı aynı zamanda alfabetik 
                    // olmadığı durumda kelime olarak ele alıyoruz.
                    this.validateSubStr(left, right);
                    right++;
                    left = right;
                }
            } else {
                right++;
            }
        }

    }
    
    private void validateSubStr(int leftIndex, int rightIndex) {
        String subStr = textArea.getText().substring(leftIndex, rightIndex);

        boolean isSubStrValid = this.isWordValid(subStr);

        if (!isSubStrValid) {
            String validWord = this.getValidWord(subStr);
            if (!subStr.equals(validWord)) {
                System.out.println(textArea.getText());
                textArea.replaceText(leftIndex, rightIndex, validWord);
                System.out.println(textArea.getText());
                textArea.setStyle(leftIndex, rightIndex, "-fx-fill: #e58e26;");
            } else {
                textArea.setStyle(leftIndex, rightIndex, "-fx-fill: red;");
            }
        } else {
            textArea.setStyle(leftIndex, rightIndex, "-fx-fill: green;");
        }

        textArea.setStyle(Math.min(rightIndex, textArea.getText().length() - 1), textArea.getText().length() - 1, "-fx-fill: black;");
    }

    private String getValidWord(String word) {
        return this.textManipulator.getValidForm(word);
    }

    public boolean isWordValid(String word) {
        return this.textManipulator.isValidWord(word);
    }

    public void findText(String what) {
        int from = textManipulator.findText(what, textArea.getText(), this.lastFindIndex);
        if (from == -1 && this.lastFindIndex != 0 || !this.lastSearched.equals(what)) {
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

    public void replaceText(String current, String replace) {
        int start, stop;
        start = textManipulator.findText(current, textArea.getText(), 0);

        if (start == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("Text not found");
            alert.showAndWait();
        } else {
            stop = start + current.length();
            textArea.replaceText(start, stop, replace);
        }

    }

    public void replaceAllText(String current, String replace) {
        int start, stop;
        start = textManipulator.findText(current, textArea.getText(), 0);

        if (start == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("Text not found");
            alert.showAndWait();
        }
        while (start != -1) {
            stop = start + current.length();
            textArea.replaceText(start, stop, replace);
            start = textManipulator.findText(current, textArea.getText(), 0);

        }
    }

    public void getPressedKeyIndex() {
        pressedKeyIndex = textArea.getCaretPosition();
        pressedKeyStackForUndo.push(pressedKeyIndex);
    }

    public void undo() {
        String textContent = textArea.getText();
        if (textContent.equals("")) {
            undoButton.setDisable(true);
        }
        int lastPressedKeyIndex = pressedKeyStackForUndo.pop();
        textManipulator.toUndoRedo(textContent.substring(lastPressedKeyIndex, lastPressedKeyIndex + 1));
        pressedKeyStackForRedo.push(lastPressedKeyIndex);
        textArea.deleteText(lastPressedKeyIndex, lastPressedKeyIndex + 1);
    }

    public void redo() {

        String deletedString = textManipulator.bringText();
        if (deletedString.equals("")) {
//            redoButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Redo");
            alert.setHeaderText("Nothing to redo");
            alert.showAndWait();
        } else {
            int lastPressedKeyIndex = pressedKeyStackForRedo.pop();
            pressedKeyStackForUndo.push(lastPressedKeyIndex);
            textArea.insertText(lastPressedKeyIndex, deletedString);
        }
    }

}
