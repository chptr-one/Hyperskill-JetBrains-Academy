package life;

import javax.swing.*;

class TextPanel extends JPanel {
    private final UniverseController controller;
    private final JLabel generationLabel;
    private final JLabel aliveLabel;

    TextPanel(UniverseController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        generationLabel = new JLabel("Generation #1");
        generationLabel.setName("GenerationLabel");
        add(generationLabel);

        aliveLabel = new JLabel("Alive: 0");
        aliveLabel.setName("AliveLabel");
        add(aliveLabel);

        setVisible(true);
    }

    @Override
    public void repaint() {
        if (controller != null) {
            generationLabel.setText("Generation #" + controller.getGeneration());
            aliveLabel.setText("Alive: " + controller.getAlive());
        }
        super.repaint();
    }
}
