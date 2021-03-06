package com.fon.p2.application;

import com.fon.p2.strategy.SaveAs;
import com.fon.p2.strategy.SaveFunctions;
import com.fon.p2.strategy.SaveNormal;
import com.fon.p2.strategy.Strategy;
import com.fon.p2.abstractFactory.AbstractFactory;
import com.fon.p2.abstractFactory.FactoryProducer;
import com.fon.p2.text_manipulation.TextManipulator;
import com.fon.p2.command.BackSpaceCommand;
import com.fon.p2.command.CommandManager;
import com.fon.p2.command.CutCommand;
import com.fon.p2.command.DeleteCommand;
import com.fon.p2.command.PasteCommand;
import com.fon.p2.command.WriteCommand;
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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import org.fxmisc.richtext.InlineCssTextArea;

public class EditorController implements Initializable {

    @FXML
    private Stage controllerStage;

    @FXML
    private AnchorPane editorAnchor;

    @FXML
    private InlineCssTextArea textArea;

    // dosya işlemlerinde kullanılıyor.
    private String filePath = "";

    private TextManipulator textManipulator;

    private int lastFindIndex = 0;
    private String lastSearched = "";

    // Aramayı baştan sona doğru yapacaksa true aksi halde false.
    private boolean isFindingDown = true;

    // Varsayılan textArea stili
    private String textAreaDefaultStyle = "-fx-font-size: 18px; -fx-padding: 10px; -fx-fill: #000000;";

    private boolean shouldResetStyle = false;

    //////
    private CommandManager cmdMgr = new CommandManager();
    public boolean isChange = false;
    private AbstractFactory errorFactory = FactoryProducer.getFactory("error");
    private AbstractFactory infoFactory = FactoryProducer.getFactory("information");
    private SaveFunctions saveNormalFunction = new SaveFunctions(new SaveNormal());
    private SaveFunctions saveAsFunction = new SaveFunctions(new SaveAs());

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

                int position = textArea.getCaretPosition();
                String selectedText = textArea.getSelectedText();
                boolean shouldDeleteBeforeWrite = false;
                if (selectedText.length() > 0) {
                    shouldDeleteBeforeWrite = true;
                    String selected = position + selectedText.length() <= textArea.getText().length() ? textArea.getText().substring(position, position + selectedText.length()) : "";
                    if (!selected.equals(selectedText)) {
                        position = position - selectedText.length();
                    }
                }else {
                    selectedText = t.getText();
                }

                if (up.match(t) || right.match(t) || left.match(t) || down.match(t)) {
                    arrowKeys = true;
                }
                if (ctrlV.match(t)) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    if (clipboard.hasString()) {
                        PasteCommand pasteCommand = new PasteCommand(textArea, position, clipboard.getString());
                        cmdMgr.executeCommand(pasteCommand);
                    }
                } else if (ctrlX.match(t)) {
                    CutCommand cutCommand = new CutCommand(textArea, textArea.getSelectedText(), position);
                    cmdMgr.executeCommand(cutCommand);
                } else if (t.getCode() == KeyCode.BACK_SPACE) {
                    if (textArea.getSelectedText().length() >= 1) {
                        BackSpaceCommand backspaceCommand = new BackSpaceCommand(textArea, position, textArea.getSelectedText());
                        cmdMgr.executeCommand(backspaceCommand);
                    } else {
                        if (textArea.getText().length() != 0) {
                            BackSpaceCommand backspaceCommand = new BackSpaceCommand(textArea, position - 1, textArea.getText().substring(position - 1, position));
                            cmdMgr.executeCommand(backspaceCommand);
                        }
                    }
                } else if (t.getCode() == KeyCode.DELETE) {
                    DeleteCommand deleteCommand = new DeleteCommand(textArea, position, t.getText());
                    cmdMgr.executeCommand(deleteCommand);

                } else if (!selectedText.equals("") && !t.isShortcutDown() && !t.isShiftDown()) {
                    if (shouldDeleteBeforeWrite) {
                        DeleteCommand deleteCommand = new DeleteCommand(textArea, position, selectedText);
                        cmdMgr.executeCommand(deleteCommand);
                    }
                    WriteCommand writeCommand = new WriteCommand(textArea, position, t.getText());
                    cmdMgr.executeCommand(writeCommand);
                }

            }
        });

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
        cmdMgr.resetUndo();
        cmdMgr.resetRedo();
    }

    //Dosyayı kaydetmek için kullanılan metot
    public void saveFile() {
        if (this.filePath.equals("")) {
            saveAsFile();
        } else {
            this.filePath = saveNormalFunction.startSave(this.filePath, textArea.getText());
        }
    }

    // Dosyayı farklı kaydetmek için kullanılan metot
    public void saveAsFile() {
        this.filePath = saveAsFunction.startSave(filePath, textArea.getText());
        String fileName = this.filePath.substring(this.filePath.lastIndexOf("\\") + 1);
        this.updateTitle(fileName);
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
        cmdMgr.resetUndo();
        cmdMgr.resetRedo();
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
        return this.textManipulator.isValidWord(word.toLowerCase());
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
        if (cmdMgr.isEmptyUndo()) {

            infoFactory.getInformation("NothingToUndo").info();
            return;
        }
        cmdMgr.undoCommand();
    }

    //Redo fonksiyonunu çalıştırır
    public void redo() {
        if (cmdMgr.isEmptyRedo()) {
            infoFactory.getInformation("NothingToRedo").info();
            return;
        }
        cmdMgr.redoCommand();
    }
}
