package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private final UniverseController controller;

    private final TextPanel textPanel;
    private final DrawingPanel drawingPanel;
    private final ActionsPanel actionsPanel;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game of life");
        setSize(300, 300);
        setLocationRelativeTo(null);

        controller = new UniverseController(this);
        textPanel = new TextPanel(controller);
        drawingPanel = new DrawingPanel(controller);
        actionsPanel = new ActionsPanel(controller);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(textPanel);
        textPanel.setAlignmentX(LEFT_ALIGNMENT);
        leftPanel.add(actionsPanel);
        actionsPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(leftPanel, BorderLayout.WEST);
        add(drawingPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

        controller.start();
    }

    @Override
    public void repaint() {
        super.repaint();
        textPanel.repaint();
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
    }
}