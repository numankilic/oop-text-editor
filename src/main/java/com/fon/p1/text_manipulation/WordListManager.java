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
public class WordListManager {

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

    // Binary search ile wordlist üzerinde arama yapar.
    public int find(String word) {
        // word > strList[i] listenin sağına bak
        // word < strList[i] listenin soluna bak
        // word == strList[i] i değerini döndür

        int left = 0;
        int right = strList.size() - 1;
        
        while (left <= right) {
            int middle = left + (right - left) / 2;
            
            if (word.compareToIgnoreCase(strList.get(middle)) == 0) {
                return middle;
            }else if(word.compareToIgnoreCase(strList.get(middle)) > 0) {
                left = middle + 1;
            }else{
                right = middle - 1;
            }
        }
        
        return -1;
    }

    public boolean isValid(String word) {
        int resFind = this.find(word);
        if(resFind != -1){
            return true;
        }
        
        return false;
    }

    public String getTrueForm(String word) {
        String result = "";
        for(int i = 0; i < strList.size(); i++){
            if(word.length() != strList.get(i).length()){
                continue;
            }
            
            
        }
        
        return result;
    }

    public ArrayList<String> getStrList() {
        return strList;
    }
}
