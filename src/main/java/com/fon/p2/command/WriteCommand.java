package com.fon.p2.command;

import org.fxmisc.richtext.InlineCssTextArea;


public class WriteCommand implements UndoableCommand {

    private int index;
    private String text;
    private InlineCssTextArea textArea;

    public WriteCommand(InlineCssTextArea textArea, int index, String text) {
        this.textArea = textArea;
        this.index = index;
        this.text = text;
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
