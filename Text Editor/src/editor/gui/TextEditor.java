package editor.gui;

import editor.action.ActionFactory;
import editor.search.*;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {
    private final JFileChooser fileChooser;
    private final TextArea textArea;
    private final ControlPanel controlPanel;
    private SearchResult searchResult;

    public TextEditor() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(new Dimension(640, 480));
        setTitle("Simple text editor");

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        add(fileChooser);

        ActionFactory actionFactory = new ActionFactory(this);

        Menu menu = new Menu(actionFactory);
        setJMenuBar(menu);

        controlPanel = new ControlPanel(actionFactory);
        add(controlPanel, BorderLayout.NORTH);

        textArea = new TextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void search(String text, String toFind, boolean searchMode) {
        if (text != null && toFind != null && !"".equals(text) && !"".equals(toFind)) {
            searchResult = new SearchEngine(text, toFind, searchMode).search();
            showNextMatch();
        }
    }

    public void showNextMatch() {
        if (searchResult == null) return;
        if (searchResult.hasResult()) {
            SearchResultItem result = searchResult.getNext();
            textArea.selectText(result.getIndex(), result.getFoundText().length());
        }
    }

    public void showPrevMatch() {
        if (searchResult == null) return;
        if (searchResult.hasResult()) {
            SearchResultItem result = searchResult.getPrev();
            textArea.selectText(result.getIndex(), result.getFoundText().length());
        }
    }
}
