package com.fon.p1.application;

import com.fon.p1.abstractFactory.AbstractFactory;
import com.fon.p1.abstractFactory.FactoryProducer;
import com.fon.p1.text_manipulation.TextManipulator;
import command.BackSpaceCommand;
import command.CommandManager;
import command.CutCommand;
import command.DeleteCommand;
import command.PasteCommand;
import command.WriteCommand;
import com.fon.p1.abstractFactory.errorFactory.ErrorFactory;
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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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

    // dosya işlemlerinde kullanılıyor.
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

    //////
    private CommandManager cmdMgr = new CommandManager();
    public boolean isChange = false;
    public AbstractFactory errorFactory = FactoryProducer.getFactory("error");
    public AbstractFactory infoFactory = FactoryProducer.getFactory("information");
    

    //////
    // editörün başlığındaki yazının güncellenmesi için metot
    private void updateTitle(String title) {
        ((Stage) editorAnchor.getScene().getWindow()).setTitle("FON | TextEditor | " + title);
    }

    //FX standart metodu. Run edildikten sonra otomatik çalışıyor.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //yazılar tek satır halinde olmaması, düzgün bir görünümü olması için kullanılıyor
        textArea.setWrapText(true);
        textArea.setStyle(this.textAreaDefaultStyle);
        try {
            this.textManipulator = new TextManipulator();
        } catch (FileNotFoundException e) {
            errorFactory.getAlert("filenotfound").error();
        }

//        textArea.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> ov, String oldText, String newText) {
//                if (isUndoRedo) {
//                    isUndoRedo = false;
//                } else {
//                    textManipulator.pushUndoStack(oldText);
//                    textManipulator.resetRedoStack();
//                }
//
//                if (shouldResetStyle) {
//                    textArea.setStyle(0, textArea.getText().length(), textAreaDefaultStyle);
//                    shouldResetStyle = false;
//                }
//            }
//        });
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            final KeyCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            final KeyCombination up = new KeyCodeCombination(KeyCode.UP);
            final KeyCombination right = new KeyCodeCombination(KeyCode.RIGHT);
            final KeyCombination left = new KeyCodeCombination(KeyCode.LEFT);
            final KeyCombination down = new KeyCodeCombination(KeyCode.DOWN);
            final KeyCombination ctrlX = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
            boolean arrowKeys = false;
            

            @Override
            public void handle(KeyEvent t) {
                
                int position = findPosition(textArea, textArea.getSelectedText());
                if (up.match(t) || right.match(t) || left.match(t) || down.match(t)) {
                    arrowKeys = true;
                }
                if (ctrlV.match(t)) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    if(clipboard.hasString()){
                        PasteCommand pasteCommand = new PasteCommand(textArea, position, clipboard.getString());
                        cmdMgr.executeCommand(pasteCommand);                        
                    }
                }else if (ctrlX.match(t)) {
                    CutCommand cutCommand = new CutCommand(textArea, textArea.getSelectedText(), position);
                    cmdMgr.executeCommand(cutCommand);
                }else if (t.getCode() == KeyCode.BACK_SPACE) {
                    System.out.println("text: " + textArea.getText() + ", select range:"
                            + textArea.getSelectedText() + ", current index: " + textArea.getCaretPosition());
                    if (textArea.getSelectedText().length() >= 1) {
                        BackSpaceCommand backspaceCommand = new BackSpaceCommand(textArea,
                                textArea.getCaretPosition(), textArea.getSelectedText());
                        cmdMgr.executeCommand(backspaceCommand);

                    }else {
                        BackSpaceCommand backspaceCommand = new BackSpaceCommand(textArea,
                                textArea.getCaretPosition() - 1, textArea.getText().substring(position - 1, position));
                        cmdMgr.executeCommand(backspaceCommand);
                    }

                } else if (t.getCode() == KeyCode.DELETE) {
                    DeleteCommand deleteCommand = new DeleteCommand(textArea, textArea.getCaretPosition(), t.getText());
                    cmdMgr.executeCommand(deleteCommand);

                } else if (!t.getText().equals(null) && !arrowKeys) {
                    WriteCommand writeCommand = new WriteCommand(textArea, textArea.getCaretPosition(), t.getText());
                    cmdMgr.executeCommand(writeCommand);
                }

            }
        });

//        textArea.addEventFilter(Ev, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent t) {
//
//                System.out.println("event: " + t);
//                System.out.println("text: " + textArea.getText());
////                WriteCommand newCmd = new WriteCommand(textArea, textArea.getCaretPosition(), e.getText());
////                cmdMgr.executeCommand(newCmd);
//
//            }
//        });
    }

    //Dosya açmak için kullanılan metot
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
                errorFactory.getAlert("filereaderror").error();
            }

        }
        textManipulator.resetUndoStack();
        textManipulator.resetRedoStack();

    }

    //Dosyayı kaydetmek için kullanılan metot
    public void saveFile() {

        if (!this.filePath.equals("")) {
            try {
                FileWriter writer = new FileWriter(this.filePath);
                writer.write(textArea.getText());
                writer.close();
            } catch (Exception e) {
                errorFactory.getAlert("filesaveerror").error();
            }
        } else {
            this.saveAsFile();      //Eğer dosya ilk kez oluşturulmuşsa Save As çalışır.
        }

    }

    // Dosyayı farklı kaydetmek için kullanılan metot
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

    //Açık olan dosyayı kapatıp ekrana temiz bir sayfa gelmesini sağlar
    public void closeFile() {
        this.newFile();
    }

    //Yeni sayfa açmaya yarar
    public void newFile() {
        filePath = "";
        this.updateTitle("*New");
        textArea.replaceText("");
        textManipulator.resetUndoStack();
        textManipulator.resetRedoStack();
    }

    //find butonuna basıldığında çalışır
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

    //Replace buttonuna basıldığında çalışır
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

    //Single Transpositon buttonuna basıldığında çalışır
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

    //kelimeleri single transposition için kontrol etmeye yarayan metot
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

    //find ve replace menüleri içindeki find text fonksiyonunu çalıştırır.
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
            infoFactory.getInformation("TextNotFound").info();
        }

        lastSearched = what;

    }

    //replace menüsündeki replace fonksiyonunu çalıştırır
    public void replaceText(String current, String replace) {
        int start, stop;
        start = textManipulator.findText(current, textArea.getText(), 0);

        if (start == -1) {
            infoFactory.getInformation("TextNotFound").info();
        } else {
            stop = start + current.length();
            textArea.replaceText(start, stop, replace);
        }

    }

    //Replace menüsündeki replace all fonksiyonunu çalıştırır
    public void replaceAllText(String current, String replace) {
        int start = textManipulator.findText(current, textArea.getText(), 0);
        if (start == -1) {
            infoFactory.getInformation("TextNotFound").info();
        }
        String currentText = textArea.getText();
        currentText = currentText.replaceAll(current, replace);
        textArea.replaceText(currentText);
    }

    //Undo fonksiyonunu çalıştırır
    public void undo() {
        cmdMgr.undoCommand();
    }


    //Redo fonksiyonunu çalıştırır
    public void redo() {
        cmdMgr.redoCommand();
    }
    
    private static int findPosition(InlineCssTextArea textArea, String selectedText){
        int position1;
        int position2;
        position1 = textArea.getText().indexOf(selectedText);
        position2 = textArea.getCaretPosition();
        if(position1>position2)return position2;
        return position1;
    }

}
