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

    public TextManipulator() throws FileNotFoundException {
        this.WLManager = new WordListManager();
    }

    public int findText(String wanted, String textArea, int from) {
        return textArea.indexOf(wanted, from);

    }

    public void replaceText(String current, String newText, String textArea) {
        textArea.replace(current, newText);
    }

    public void pushUndoStack(String text) {
        undoRedo.pushToUndo(text);
    }

    public String undo() {
        return undoRedo.popFromUndo();
    }

    public void pushRedoStack(String kpa) {
        undoRedo.pushToRedo(kpa);
    }
    
    public void resetUndoStack() {
        undoRedo.resetUndo();
    }

    public void resetRedoStack() {
        undoRedo.resetRedo();
    }

    public String redo() {
        return undoRedo.popFromRedo();
    }

    public boolean isValidWord(String word) {
        return this.WLManager.isValid(word);
    }

    public String getValidForm(String word) {
        return this.WLManager.getTrueFormForSingleTransposition(word);
    }

}
