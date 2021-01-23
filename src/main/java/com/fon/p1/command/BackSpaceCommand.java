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
public class BackSpaceCommand implements UndoableCommand {

    private int index;
    private String text;
    private InlineCssTextArea textArea;

    public BackSpaceCommand(InlineCssTextArea textArea, int index, String text) {
        this.index = index;
        this.textArea = textArea;
        this.text = text;
        System.out.println("Created BackSpace Command index: " + index + ", text: " + text);
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
