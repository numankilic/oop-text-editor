package com.fon.p2.command;

import org.fxmisc.richtext.InlineCssTextArea;


public class DeleteCommand implements UndoableCommand{
    
    private int index;
    private String text;
    private InlineCssTextArea textArea;
    
    public DeleteCommand(InlineCssTextArea textArea, int index, String text){
        this.index = index;
        this.textArea = textArea;
        this.text = text;
        System.out.println("Created Delete Command index: "+index + ", text: " + text);
    }

    @Override
    public void undo() {
        textArea.insertText(index, text);
    }

    @Override
    public void redo() {
        textArea.deleteText(index, index + text.length());
    }

    @Override
    public void execute() {
        
    }
    
}
