/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.command;

/**
 *
 * @author pepper
 */
public class CommandManager {

    private SizedStack commandStack = new SizedStack<UndoableCommand>(5);
    private SizedStack commandRedoStack = new SizedStack<UndoableCommand>(5);

    public void executeCommand(Command cmd) {
        cmd.execute();
        if (cmd instanceof UndoableCommand) {
            commandStack.push(cmd);
        }
    }

    public void undoCommand() {
        System.out.println("undo");
        if (commandStack.size() > 0) {
            UndoableCommand cmd = (UndoableCommand) commandStack.pop();
            cmd.undo();
            commandRedoStack.push(cmd);
        }
    }

    public void redoCommand() {
        System.out.println("redo");
        if (commandRedoStack.size() > 0) {
            UndoableCommand cmd = (UndoableCommand) commandRedoStack.pop();
            cmd.redo();
            commandStack.push(cmd);
        }
    }

    public void resetUndo() {
        commandStack.clear();
    }

    public void resetRedo() {
        commandRedoStack.clear();
    }
    
    public boolean isEmptyUndo(){
        return commandStack.isEmpty();
    }
    
    public boolean isEmptyRedo(){
        return commandRedoStack.isEmpty();
    }
}
