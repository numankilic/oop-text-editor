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
public class TextSearh {
    
    /**
     * Wanted verilen text içerisinde aranır.
     * -1 bulunamadığında
     * @param wanted
     * @return 
     */
    public static int find(String wanted, String textArea, int from){
        return textArea.indexOf(wanted, from);
    }
    
    
}
