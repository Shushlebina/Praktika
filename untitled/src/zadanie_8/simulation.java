package zadanie_8;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.function.Predicate;

abstract class TFish {
    double x, y;
    double speed;
    double size;
    Color color;
    double direction;
    TFish next;

    abstract void draw(Graphics2D g);

    abstract Color look(FishSchool fishSchool);

    abstract void run();

    void eat(FishSchool fishSchool) {
        Predicate<TFish> predicate = fish -> fish != this && isInRange(fish);
        Color targetColor = look(fishSchool);
        if (targetColor != null) {
            fishSchool.removeIf(predicate.and(fish -> fish.color.equals(targetColor)));
            System.out.println("Fish eaten!");
        }
    }

    boolean isInRange(TFish otherFish) {
        double distance = Math.sqrt(Math.pow(x - otherFish.x, 2) + Math.pow(y - otherFish.y, 2));
        return distance < size * 2;
    }
}

class TPike extends TFish {
    public TPike(double x, double y, double speed, double size, double direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = Color.GREEN;
        this.direction = direction;
    }

    @Override
    void draw(Graphics2D g) {
        g.setColor(color);
        g.drawLine((int) x, (int) y, (int) (x + size), (int) y);
        g.drawLine((int) (x + size), (int) y, (int) (x + size / 2), (int) (y - size));
        g.drawLine((int) (x + size / 2), (int) (y - size), (int) x, (int) y);
    }

    @Override
    Color look(FishSchool fishSchool) {
        for (TFish fish : fishSchool) {
            if (fish != this && isInRange(fish)) {
                return fish.color;
            }
        }
        return null;
    }

    @Override
    void run() {
        x += speed * Math.cos(Math.toRadians(direction));
        y += speed * Math.sin(Math.toRadians(direction));

        if (Math.random() < 0.1) {
            direction = Math.random() * 360;
        }
    }
}

class TKarp extends TFish {
    public TKarp(double x, double y, double speed, double size, double direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = Color.RED;
        this.direction = direction;
    }

    @Override
    void draw(Graphics2D g) {
        g.setColor(color);
        int[] xPoints = {(int) x, (int) (x + size), (int) (x - size)};
        int[] yPoints = {(int) y, (int) (y + size), (int) (y + size)};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    Color look(FishSchool fishSchool) {
        return null;
    }

    @Override
    void run() {
        x += speed * Math.cos(Math.toRadians(direction));
        y += speed * Math.sin(Math.toRadians(direction));

        if (Math.random() < 0.1) {
            direction = Math.random() * 360;
        }
    }
}

class FishSchool implements Iterable<TFish> {
    TFish head;

    void addFish(TFish fish) {
        fish.next = head;
        head = fish;
    }

    void removeIf(Predicate<TFish> predicate) {
        TFish currentFish = head;
        TFish previousFish = null;

        while (currentFish != null) {
            if (predicate.test(currentFish)) {
                if (previousFish != null) {
                    previousFish.next = currentFish.next;
                } else {
                    head = currentFish.next;
                }
                return;
            }

            previousFish = currentFish;
            currentFish = currentFish.next;
        }
    }

    @Override
    public Iterator<TFish> iterator() {
        return new FishIterator();
    }

    private class FishIterator implements Iterator<TFish> {
        private TFish current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public TFish next() {
            TFish fish = current;
            current = current.next;
            return fish;
        }
    }
}

class AquariumPanel extends JPanel {
    private FishSchool fishSchool;

    public void setFishSchool(FishSchool fishSchool) {
        this.fishSchool = fishSchool;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (TFish fish : fishSchool) {
            fish.draw(g2d);
        }
    }
}

public class simulation {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aquarium Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AquariumPanel aquariumPanel = new AquariumPanel();
            frame.getContentPane().add(aquariumPanel);
            frame.setSize(800, 600);
            frame.setVisible(true);

            FishSchool fishSchool = new FishSchool();
            for (int i = 0; i < 6; i++) {
                fishSchool.addFish(new TPike(Math.random() * 800, Math.random() * 600, 2, 20, Math.random() * 360));
            }
            for (int i = 0; i < 10; i++) {
                fishSchool.addFish(new TKarp(Math.random() * 800, Math.random() * 600, 3, 30, Math.random() * 360));
            }

            aquariumPanel.setFishSchool(fishSchool);

            Timer timer = new Timer(50, e -> {
                for (TFish fish : fishSchool) {
                    fish.run();
                    fish.eat(fishSchool);
                }
                aquariumPanel.repaint();
            });
            timer.start();
        });
    }
}