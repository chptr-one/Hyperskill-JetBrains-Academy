package life;

import javax.swing.*;
import java.awt.*;

class DrawingPanel extends JPanel {
    private final UniverseController controller;

    DrawingPanel(UniverseController controller) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 400));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean[][] cells = controller.getCells();

        int rows = cells.length;
        int cols = cells[0].length;
        int width = getWidth() / rows;
        int height = getHeight() / cols;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (cells[row][col]) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.lightGray);
                }
                g.fillRect(col * width + 1, row * height + 1, width - 1, height - 1);
            }
        }
    }
}
