package com.fon.p1.application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pepper
 */
public class FindPopupController implements Initializable {

    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

//    private Function<String, Void> findFunction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onSearchBoxTextChanged() {
        if (!searchBox.getText().equals("")) {
            searchButton.setDisable(false);
        } else {
            searchButton.setDisable(true);
        }
    }

    public void onSearchButtonAction() throws Exception {
        //        System.out.println(EditorController.textArea.getText() +"__-" + searchBox.getText());
//        this.findFunction.apply(searchBox.getText());

    }

    public void saveFindTextFunction(Function<String, Void> findFunction) {
//        this.findFunction = findFunction;
    }
}
