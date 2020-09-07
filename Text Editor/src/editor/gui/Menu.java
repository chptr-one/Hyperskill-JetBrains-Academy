package editor.gui;

import editor.action.ActionFactory;

import javax.swing.*;

class Menu extends JMenuBar {

    Menu(ActionFactory actionFactory) {
        JMenu menuFile = new JMenu("File");
        JMenuItem menuOpen = new JMenuItem(actionFactory.getOpenFileAction());
        JMenuItem menuSave = new JMenuItem(actionFactory.getSaveFileAction());
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(e -> {
            System.exit(0);
        });

        menuFile.add(menuOpen);
        menuFile.add(menuSave);
        menuFile.addSeparator();
        menuFile.add(menuExit);

        JMenu menuSearch = new JMenu("Search");
        JMenuItem menuStartSearch = new JMenuItem(actionFactory.getSearchAction());
        JMenuItem menuNextMatch = new JMenuItem(actionFactory.getNextMatchAction());
        JMenuItem menuPrevMatch = new JMenuItem(actionFactory.getPrevMatchAction());
        JMenuItem menuUseRegEx = new JCheckBoxMenuItem(actionFactory.getToggleSearchModeAction());

        menuSearch.add(menuStartSearch);
        menuSearch.add(menuNextMatch);
        menuSearch.add(menuPrevMatch);
        menuSearch.add(menuUseRegEx);

        menuFile.setName("MenuFile");
        menuOpen.setName("MenuOpen");
        menuSave.setName("MenuSave");
        menuExit.setName("MenuExit");
        menuSearch.setName("MenuSearch");
        menuStartSearch.setName("MenuStartSearch");
        menuNextMatch.setName("MenuNextMatch");
        menuPrevMatch.setName("MenuPreviousMatch");
        menuUseRegEx.setName("MenuUseRegExp");

        this.add(menuFile);
        this.add(menuSearch);
    }
}
