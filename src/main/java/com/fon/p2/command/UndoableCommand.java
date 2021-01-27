package com.fon.p2.command;


public interface UndoableCommand extends Command {

    public void undo();
    public void redo();
}
