/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import org.fxmisc.richtext.InlineCssTextArea;

/**
 *
 * @author pepper
 */
public class WriteCommand implements UndoableCommand {

    private int index;
    private String text;
    private InlineCssTextArea textArea;

    public WriteCommand(InlineCssTextArea textArea, int index, String text) {
        this.textArea = textArea;
        this.index = index;
        this.text = text;
        System.out.println("Created Write Command index: " + index + ", text: " + text);
    }

    public void execute() {
        // will be executed any way
    }

    public void undo() {
        textArea.deleteText(index, index + text.length());
    }

    public void redo() {
        textArea.insertText(index, text);
    }

}
