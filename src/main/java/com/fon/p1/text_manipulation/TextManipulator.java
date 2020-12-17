/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

import java.io.FileNotFoundException;

/**
 *
 * @author pepper
 */
public class TextManipulator {

    private UndoRedo undoRedo = new UndoRedo();
    private WordListManager WLManager;

    public TextManipulator() throws FileNotFoundException{
        this.WLManager = new WordListManager();
    }

    public int findText(String wanted, String textArea, int from) {
        return textArea.indexOf(wanted, from);

    }

    public void replaceText(String current, String newText, String textArea) {
        textArea.replace(current, newText);
    }

    public void toUndoRedo(String writtenString) {
        undoRedo.toStack(writtenString);
    }

    public String bringText() {
        return undoRedo.popStack();
    }
    
    public boolean isValidWord(String word){
        return this.WLManager.isValid(word);
    }
    
    public String getValidForm(String word){
        return this.WLManager.getTrueFormForSingleTransposition(word);
    }
    
}
