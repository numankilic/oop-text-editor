package com.fon.p2.command;

import org.fxmisc.richtext.InlineCssTextArea;


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
