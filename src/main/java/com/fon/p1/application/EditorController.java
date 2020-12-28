package com.fon.p1.application;

import com.fon.p1.text_manipulation.TextManipulator;
import java.io.BufferedReader;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import org.fxmisc.richtext.InlineCssTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pepper
 */
public class EditorController implements Initializable {

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

    // Varsayılan textArea stili
    private String textAreaDefaultStyle = "-fx-font-size: 18px; -fx-padding: 10px; -fx-fill: #000000;";

    private int pressedKeyIndex;

    private boolean isUndoRedo = false;
    private boolean shouldResetStyle = false;

    private void updateTitle(String title) {
        ((Stage) editorAnchor.getScene().getWindow()).setTitle("FON | TextEditor | " + title);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setWrapText(true);
        textArea.setStyle(this.textAreaDefaultStyle);
        try {
            this.textManipulator = new TextManipulator();
        } catch (FileNotFoundException e) {
            showError("File not found!", "", "Error while opening word list!");
        }

        // text area üzerinde bir değişiklik olduğunda undo-redo işlemlerini ele 
        // alabilmek için textin önceki versiyonunu kayıt altına alır.
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldText, String newText) {
                if (isUndoRedo) {
                    isUndoRedo = false;
                } else {
                    textManipulator.pushUndoStack(oldText);
                    textManipulator.resetRedoStack();
                }
                
                if(shouldResetStyle){
                    textArea.setStyle(0, textArea.getText().length(), textAreaDefaultStyle);
                    shouldResetStyle = false;
                }
            }
        });
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
        textManipulator.resetUndoStack();
        textManipulator.resetRedoStack();

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
        if (textFile != null) {
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

    }

    public void closeFile() {
        this.newFile();
    }

    public void newFile() {
        filePath = "";
        this.updateTitle("*New");
        textArea.replaceText("");
        textManipulator.resetUndoStack();
        textManipulator.resetRedoStack();
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
        shouldResetStyle = true;
    }

    private void validateSubStr(int leftIndex, int rightIndex) {
        String subStr = textArea.getText().substring(leftIndex, rightIndex);

        boolean isSubStrValid = this.isWordValid(subStr);

        if (!isSubStrValid) {
            String validWord = this.getValidWord(subStr);
            if (!subStr.equalsIgnoreCase(validWord)) {
                textArea.replaceText(leftIndex, rightIndex, validWord);
                textArea.setStyle(leftIndex, rightIndex, "-fx-fill: #e58e26;");
            } else {
                textArea.setStyle(leftIndex, rightIndex, "-fx-fill: red;");
            }
        } else {
            textArea.setStyle(leftIndex, rightIndex, "-fx-fill: green;");
        }

        textArea.setStyle(Math.min(rightIndex, textArea.getText().length()), textArea.getText().length(), "-fx-fill: black;");
    }

    private String getValidWord(String word) {
        return this.textManipulator.getValidForm(word.toLowerCase());
    }

    public boolean isWordValid(String word) {
        boolean result = this.textManipulator.isValidWord(word.toLowerCase());
        System.out.println("lookng:" + word + ":::" + result);
        return result;
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
            showInfo("Not Found", "", "Text not found");
        }

        lastSearched = what;

    }

    public void replaceText(String current, String replace) {
        int start, stop;
        start = textManipulator.findText(current, textArea.getText(), 0);

        if (start == -1) {
            showInfo("Not Found", "", "Text not found");
        } else {
            stop = start + current.length();
            textArea.replaceText(start, stop, replace);
        }

    }

    public void replaceAllText(String current, String replace) {
        int start = textManipulator.findText(current, textArea.getText(), 0);
        if (start == -1) {
            showInfo("Not Found", "", "Text not found");
        }
        String currentText = textArea.getText();
        currentText = currentText.replaceAll(current, replace);
        textArea.replaceText(currentText);
    }

    public void undo() {
        this.isUndoRedo = true;
        String undoText = textManipulator.undo();
        if (undoText == null) {
            this.showInfo("Warning", "", "Nothing to undo.");
            return;
        }
        textManipulator.pushRedoStack(textArea.getText());
        textArea.replaceText(undoText);

    }

    public void redo() {
        this.isUndoRedo = true;
        String redoText = textManipulator.redo();
        if (redoText == null) {
            this.showInfo("Warning", "", "Nothing to redo.");
            return;
        }
        textManipulator.pushUndoStack(textArea.getText());
        textArea.replaceText(redoText);
    }

    public void showInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
