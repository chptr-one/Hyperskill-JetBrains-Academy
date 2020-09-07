package editor.gui;

import editor.action.ActionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel {
    private final JTextField searchField;
    private final JCheckBox buttonUseRegEx;

    ControlPanel(ActionFactory actionFactory) {
        JButton buttonOpen = new JButton(actionFactory.getOpenFileAction());
        JButton buttonSave = new JButton(actionFactory.getSaveFileAction());
        JButton buttonSearch = new JButton(actionFactory.getSearchAction());
        JButton buttonNext = new JButton(actionFactory.getNextMatchAction());
        JButton buttonPrev = new JButton(actionFactory.getPrevMatchAction());

        for (JButton b : List.of(buttonSave, buttonNext, buttonOpen, buttonPrev, buttonSearch)) {
            b.setText(null);
        }

        buttonUseRegEx = new JCheckBox(actionFactory.getToggleSearchModeAction());
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(buttonOpen);
        add(buttonSave);
        add(searchField);
        add(buttonSearch);
        add(buttonPrev);
        add(buttonNext);
        add(buttonUseRegEx);

        buttonOpen.setName("OpenButton");
        buttonSave.setName("SaveButton");
        buttonSearch.setName("StartSearchButton");
        buttonNext.setName("NextMatchButton");
        buttonPrev.setName("PreviousMatchButton");
        buttonUseRegEx.setName("UseRegExCheckbox");
        searchField.setName("SearchField");
    }

    public String getSearchString() {
        return searchField.getText();
    }

    public boolean getSearchMode() {
        return buttonUseRegEx.isSelected();
    }
}
