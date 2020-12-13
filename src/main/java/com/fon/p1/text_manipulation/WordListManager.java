/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pepper
 */
public class WordListManager{
    
    private ArrayList<String> strList = new ArrayList<String>();
  
    public WordListManager() throws FileNotFoundException {
        this.init();
    }

    public WordListManager(String absPath) throws FileNotFoundException {
        this.init(absPath);
    }

    public void init() throws FileNotFoundException {
        this.init("words.txt");
    }

    public void init(String absPath) throws FileNotFoundException {
        File myObj = new File(absPath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            strList.add(data.trim());
        }
        myReader.close();
    }

    public boolean isValid(String word) {
        return false;
    }

    public String getTrueForm(String word) {
        String c = "";
        return c;
    }
}
