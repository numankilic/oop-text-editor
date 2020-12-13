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

    /**
     * Wanted verilen text içerisinde aranır. -1 bulunamadığında
     *
     * @param wanted
     * @return
     */
    public int findText(String wanted, String textArea, int from) {
        return TextSearh.find(wanted, textArea, from);
    }

    public void replaceText(String current, String newText, String textArea){
        textArea.replace(current, newText);
    }
    
    
}
