package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

class SearchAction extends AbstractTextEditorAction {
    public SearchAction(String name, TextEditor editor, Icon icon) {
        super(name, editor);
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = editor.getTextArea().getText();
        String toFind = editor.getControlPanel().getSearchString();
        boolean searchMode = editor.getControlPanel().getSearchMode();

        editor.search(text, toFind, searchMode);
    }
}
