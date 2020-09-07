package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;

abstract class AbstractTextEditorAction extends AbstractAction {
    protected final TextEditor editor;

    protected AbstractTextEditorAction(String name, TextEditor editor) {
        super(name);
        this.editor = editor;
    }
}
