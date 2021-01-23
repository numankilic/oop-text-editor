/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.command;

import org.fxmisc.richtext.InlineCssTextArea;

/**
 *
 * @author Numan
 */
public class PasteCommand implements UndoableCommand{
    
    InlineCssTextArea textArea;
    int index;
    String text;
    
    public PasteCommand(InlineCssTextArea textArea, int index, String text){
        this.index = index;
        this.textArea = textArea;
        this.text = text;
        
    }
    

    @Override
    public void undo() {       
        textArea.deleteText(index, index+text.length());       
    }

    @Override
    public void redo() {
        textArea.insertText(index, text);
    }

    @Override
    public void execute() {
    }
    
}
