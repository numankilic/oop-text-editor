/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import org.fxmisc.richtext.InlineCssTextArea;

/**
 *
 * @author Numan
 */
public class CutCommand implements UndoableCommand{
    
    private int index;
    private InlineCssTextArea textArea;
    String selectedText;
    //private TextArea textAreaaa;
    
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
