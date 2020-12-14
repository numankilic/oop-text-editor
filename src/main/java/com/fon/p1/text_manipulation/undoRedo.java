package com.fon.p1.text_manipulation;

import java.util.Stack;



public class undoRedo {
    
    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();
    
    public undoRedo() {
        
    }
    
    public void toStack(String writtenChar){
        System.out.println("Stack'e atilan char: " + writtenChar);
        undoStack.push(writtenChar);
        for (int i = 0; i < undoStack.size();i++){
            String ele = undoStack.pop();
            System.out.println("Stackten cikan char: " + ele);
        }
    }
    

}
