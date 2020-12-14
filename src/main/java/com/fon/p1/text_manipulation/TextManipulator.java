/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

/**
 *
 * @author pepper
 */
public class TextManipulator {

    private TextSearchManager textSearchManager = new TextSearchManager();
    private undoRedo undoRedo = new undoRedo();

    public int findText(String wanted, String textArea, int from) {
        return textSearchManager.find(wanted, textArea, from);
    }

    public void replaceText(String current, String newText, String textArea){
        textArea.replace(current, newText);
    }
    
    public void toUndoRedo(String writtenString){
        undoRedo.toStack(writtenString);
    }
}
