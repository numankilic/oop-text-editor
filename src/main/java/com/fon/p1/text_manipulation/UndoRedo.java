/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

import java.util.Stack;

/**
 *
 * @author Numan
 */
public class UndoRedo {
    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();
    
    public UndoRedo() {
        
    }
    
    public void toStack(String writtenChar){
        //System.out.println("Stack'e atilan char: " + writtenChar);
        undoStack.push(writtenChar);
//        for (int i = 0; i < undoStack.size();i++){
//            String ele = undoStack.pop();
            //System.out.println("Stackten cikan char: " + ele);
        }
    
    public String popStack(){
        if(undoStack.empty()){
            return "";
        }
        String deletedText = undoStack.pop();
        redoStack.push(deletedText);

        return redoStack.pop();
        }
    
    }
    

    

