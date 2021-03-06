package com.fon.p2.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class FindPopupController implements Initializable {

    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    private IFindTextFunction findFunction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void onSearchBoxTextChanged() {
        if (!searchBox.getText().equals("")) {
            searchButton.setDisable(false);
        } else {
            searchButton.setDisable(true);
        }
    }

    public void onSearchButtonAction() throws Exception {
        this.findFunction.operation(searchBox.getText());
    }

    public void saveFindTextFunction(IFindTextFunction findTextFunction) {
        this.findFunction = findTextFunction;
    }
}
