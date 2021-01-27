package com.fon.p2.text_manipulation;

import java.io.FileNotFoundException;


public class TextManipulator {

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

    public boolean isValidWord(String word) {
        return this.WLManager.isValid(word);
    }

    public String getValidForm(String word) {
        return this.WLManager.getTrueFormForSingleTransposition(word);
    }

}
