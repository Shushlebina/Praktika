package zadanie_7;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class HelloDrawingSwing extends JFrame {

    public HelloDrawingSwing() {
        initUI();
    }

    private void initUI() {
        setTitle("Emoji Drawing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        DrawingPanel panel = new DrawingPanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HelloDrawingSwing::new);
    }
}

class DrawingPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Создаем лицо
        Ellipse2D face = new Ellipse2D.Double(50, 50, 200, 200);
        g2d.setColor(Color.YELLOW);
        g2d.fill(face);

        // Создаем глаза
        Ellipse2D leftEye = new Ellipse2D.Double(90, 100, 40, 50);
        g2d.setColor(Color.WHITE);
        g2d.fill(leftEye);

        Ellipse2D rightEye = new Ellipse2D.Double(170, 100, 40, 50);
        g2d.fill(rightEye);

        // Создаем зрачки в глазах
        Ellipse2D leftPupil = new Ellipse2D.Double(110, 115, 16, 16);
        g2d.setColor(Color.BLACK);
        g2d.fill(leftPupil);

        Ellipse2D rightPupil = new Ellipse2D.Double(190, 115, 16, 16);
        g2d.fill(rightPupil);

        // Создаем нос
        Rectangle2D nose = new Rectangle2D.Double(145, 150, 10, 10);
        g2d.setColor(Color.ORANGE);
        g2d.fill(nose);

        // Создаем рот
        Ellipse2D mouth = new Ellipse2D.Double(140, 180, 20, 30);
        g2d.setColor(Color.BLACK);
        g2d.fill(mouth);

        // Создаем 13 кругов вокруг головы
        for (int i = 0; i < 13; i++) {
            double angle = Math.toRadians(360 / 13 * i);
            double x = 150 + 120 * Math.cos(angle) - 25;
            double y = 150 + 120 * Math.sin(angle) - 25;

            Ellipse2D smallCircle = new Ellipse2D.Double(x, y, 50, 50);
            g2d.setColor(Color.RED);
            g2d.fill(smallCircle);
        }
    }
}