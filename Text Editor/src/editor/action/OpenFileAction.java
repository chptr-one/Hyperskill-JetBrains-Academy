package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class OpenFileAction extends AbstractTextEditorAction {

    OpenFileAction(String name, TextEditor editor, Icon icon) {
        super(name, editor);
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnValue = editor.getFileChooser().showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = editor.getFileChooser().getSelectedFile();
            try {
                editor.getTextArea().read(Files.newBufferedReader(selectedFile.toPath()), null);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
