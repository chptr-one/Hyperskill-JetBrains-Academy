package life;

import javax.swing.*;
import java.awt.*;

class ActionsPanel extends JPanel {
    JToggleButton toggleButton;
    JButton resetButton;

    ActionsPanel(UniverseController controller) {
        toggleButton = new JToggleButton("Pause");
        toggleButton.setName("PlayToggleButton");
        toggleButton.setPreferredSize(new Dimension(80,30));
        toggleButton.addActionListener(l -> {
            controller.togglePause();
            if (toggleButton.getText().equals("Pause")) {
                toggleButton.setText("Play");
            } else {
                toggleButton.setText("Pause");
            }
        });

        resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.setPreferredSize(new Dimension(80,30));
        resetButton.addActionListener(l -> {
            controller.restart();
        });

        add(resetButton);
        add(toggleButton);

        setVisible(true);
    }
}
