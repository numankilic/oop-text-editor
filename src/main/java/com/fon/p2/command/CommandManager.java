package com.fon.p2.command;


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
