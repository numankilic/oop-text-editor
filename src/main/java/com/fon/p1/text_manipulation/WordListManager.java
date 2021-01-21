/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
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
    // Ardışık iterate olmadığı için iterator kullanılmadı.
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
            } else if (word.compareToIgnoreCase(strList.get(middle)) > 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    public boolean isValid(String word) {
        int resFind = this.find(word);
        if (resFind != -1) {
            return true;
        }

        return false;
    }

    public String getTrueFormForSingleTransposition(String word) {
        Iterator<String> it = strList.iterator();

        while (it.hasNext()) {

            String lookFor = it.next();
            if (word.length() != lookFor.length()) {
                continue;
            }

            // d  e  f  a  u  l  t
            // e  d  f  a  u  l  t
            // ___________________ (minus operation charaterwise)
            // x -x  0  0  0  0  0
            int diff[] = new int[word.length()];

            int count = 0;
            int currentAbs = -1;
            int currentAbsIndex = -1;
            boolean next = false;

            for (int j = 0; j < word.length(); j++) {
                int abs = word.charAt(j) - lookFor.charAt(j);
                if (abs != 0) {
                    count++;
                    diff[j] = abs;

                    if (currentAbs != -1) {
                        if (currentAbs == abs * -1 && lookFor.charAt(currentAbsIndex) == word.charAt(j)) {
                            continue;
                        } else {
                            next = true;
                            break;
                        }
                    } else {
                        currentAbs = abs;
                        currentAbsIndex = j;
                    }
                }
            }

            if (next) {
                continue;
            }

            if (count != 2) {
                return word;
            } else {
                return lookFor;
            }
        }

        return word;
    }

    public ArrayList<String> getStrList() {
        return strList;
    }
}
