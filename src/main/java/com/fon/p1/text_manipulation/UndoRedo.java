/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.text_manipulation;

/**
 *
 * @author Numan
 */
public class UndoRedo {

    public final int stackSize = 5;
    private SizedStack<String> undoStack;
    private SizedStack<String> redoStack;

    public UndoRedo() {
        this.undoStack = new SizedStack<>(stackSize);
        this.redoStack = new SizedStack<>(stackSize);
    }

    public void pushToUndo(String text) {
        undoStack.push(text);
    }

    public void pushToRedo(String text) {
        redoStack.push(text);
    }

    public String popFromUndo() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return null;
    }

    public String popFromRedo() {
        if (!redoStack.isEmpty()) {
            return redoStack.pop();
        }
        return null;
    }
    
    public void resetUndo() {
        if (!undoStack.isEmpty()) {
            undoStack.clear();
        }
    }
    

    public void resetRedo() {
        if (!redoStack.isEmpty()) {
            redoStack.clear();
        }
    }

}
