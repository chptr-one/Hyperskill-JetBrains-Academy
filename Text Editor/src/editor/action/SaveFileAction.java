package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class SaveFileAction extends AbstractTextEditorAction {

    public SaveFileAction(String name, TextEditor editor, Icon icon) {
        super(name, editor);
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnValue = editor.getFileChooser().showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = editor.getFileChooser().getSelectedFile();
            try {
                editor.getTextArea().write(Files.newBufferedWriter(selectedFile.toPath()));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
