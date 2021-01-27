package com.fon.p2.command;

import org.fxmisc.richtext.InlineCssTextArea;


public class CutCommand implements UndoableCommand{
    
    private int index;
    private InlineCssTextArea textArea;
    String selectedText;
    
    public CutCommand(InlineCssTextArea textArea, String text, int index){
        this.textArea = textArea;
        selectedText = text;
        this.index = index;
    }

    @Override
    public void undo() {
       
        textArea.insertText(index, selectedText);
    }

    @Override
    public void redo() {
        textArea.deleteText(index, index+selectedText.length());
    }

    @Override
    public void execute() {
    }
    
}
