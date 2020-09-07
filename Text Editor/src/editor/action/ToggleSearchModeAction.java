package editor.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

class ToggleSearchModeAction extends AbstractAction {

    ToggleSearchModeAction(String name) {
        super(name);
        putValue(Action.SELECTED_KEY, Boolean.FALSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
