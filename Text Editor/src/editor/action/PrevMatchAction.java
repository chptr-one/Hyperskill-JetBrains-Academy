package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

class PrevMatchAction extends AbstractTextEditorAction {
    public PrevMatchAction(String name, TextEditor editor, Icon icon) {
        super(name, editor);
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editor.showPrevMatch();
    }
}
