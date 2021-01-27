package com.fon.p2.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ReplacePopupController implements Initializable{
    
    @FXML
    private TextField searchBox;
    
    @FXML
    private TextField replaceBox;
    
    @FXML
    private Button findButton;
    
    @FXML
    private Button replaceButton;
    
    private IFindTextFunction findFunction;
    
    private IReplaceTextFunction replaceFunction;
    
    private IReplaceTextFunction replaceAllFunction;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void onSearchBoxTextChanged() {
        if (!searchBox.getText().equals("")) {
            findButton.setDisable(false);
        } else {
            findButton.setDisable(true);
        }
    }
    
    public void onFindButtonAction() throws Exception {
        this.findFunction.operation(searchBox.getText());
    }
    
    public void saveFindTextFunction(IFindTextFunction findTextFunction) {
        this.findFunction = findTextFunction;
    }
    
    public void onReplaceButtonAction() throws Exception{
        this.replaceFunction.operation(searchBox.getText(), replaceBox.getText());
    }
    
    public void saveReplaceFunction(IReplaceTextFunction replaceTextFunction){
        this.replaceFunction = replaceTextFunction;
    }
    
    public void onReplaceAllButtonAction() throws Exception{
        this.replaceAllFunction.operation(searchBox.getText(), replaceBox.getText());
    }
    
    public void saveReplaceAllFunction(IReplaceTextFunction replaceAllTextFunction){
        this.replaceAllFunction = replaceAllTextFunction;
    }
}
