package editor.gui;

import javax.swing.*;

class TextArea extends JTextArea {
    TextArea() {
        setEditable(true);
        setLineWrap(true);
        setName("TextArea");
    }

    public void selectText(int startIndex, int endIndex) {
        setCaretPosition(startIndex + endIndex);
        select(startIndex, startIndex + endIndex);
        requestFocusInWindow();
    }
}
