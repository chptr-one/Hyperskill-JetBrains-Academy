package editor.action;

import editor.gui.TextEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActionFactory {
    private final AbstractAction toggleSearchModeAction;
    private final AbstractAction openFileAction;
    private final AbstractAction saveFileAction;
    private final AbstractAction searchAction;
    private final AbstractAction nextMatchAction;
    private final AbstractAction prevMatchAction;

    public ActionFactory(TextEditor editor) {
        String iconsDir = System.getProperty("user.dir") + "/resources/icons/";
        ImageIcon iconOpen = new ImageIcon(iconsDir + "icons8-open-document-50.png");
        ImageIcon iconSave = new ImageIcon(iconsDir + "icons8-save-50.png");
        ImageIcon iconSearch = new ImageIcon(iconsDir + "icons8-search-50.png");
        ImageIcon iconNext = new ImageIcon(iconsDir + "icons8-next-50.png");
        ImageIcon iconPrev = new ImageIcon(iconsDir + "icons8-previous-50.png");

        for (ImageIcon icon : List.of(iconOpen, iconSave, iconSearch, iconNext, iconPrev)) {
            Image image = icon.getImage();
            icon.setImage(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }

        toggleSearchModeAction = new ToggleSearchModeAction("Use regex");
        openFileAction = new OpenFileAction("Open file", editor, iconOpen);
        saveFileAction = new SaveFileAction("Save file", editor, iconSave);
        searchAction = new SearchAction("Start search", editor, iconSearch);
        nextMatchAction = new NextMatchAction("Next match", editor, iconNext);
        prevMatchAction = new PrevMatchAction("Previous match", editor, iconPrev);
    }

    public AbstractAction getNextMatchAction() {
        return nextMatchAction;
    }

    public AbstractAction getPrevMatchAction() {
        return prevMatchAction;
    }

    public AbstractAction getToggleSearchModeAction() {
        return toggleSearchModeAction;
    }

    public AbstractAction getOpenFileAction() {
        return openFileAction;
    }

    public AbstractAction getSaveFileAction() {
        return saveFileAction;
    }

    public AbstractAction getSearchAction() {
        return searchAction;
    }
}
