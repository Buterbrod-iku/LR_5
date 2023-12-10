import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class laba5Z2 {
    static JFrame frame = new JFrame();
    static Background background = new Background();
    static Timer tm;

    static JButton startStop = new JButton("Start/Stop");
    static JTextField speedField = new JTextField("200");

    public static void main(String[] args) {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocation(700, 300);
        frame.setLayout(null);

        background.setBounds(0, 0, 1000, 500);
        frame.add(background);

        startStop.setBounds(100, 550, 100, 20);
        frame.add(startStop);

        speedField.setBounds(100, 580, 100, 20);
        frame.add(speedField);

        frame.update(frame.getGraphics());

        startStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tm != null && tm.isRunning()) {
                    tm.stop();
                    startStop.setText("Start");
                } else {
                    int speed = Integer.parseInt(speedField.getText());
                    tm = new Timer(speed, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            background.repaint();
                        }
                    });
                    tm.start();
                    startStop.setText("Stop");
                }
            }
        });
    }

    static class Background extends JPanel {
        public BufferedImage im;
        boolean isLightOn = false;

        public Background() {
            try {
                im = ImageIO.read(new File("forest2.jpg"));
            } catch (IOException ignored) {
            }
        }

        public void paintComponent(Graphics g) {
            g.drawImage(im, 0, 0, 1000, 500, null);
            Graphics2D g2 = (Graphics2D) g;

            // Draw the bulb
            g2.setColor(Color.YELLOW);
            g2.fillOval(450, 200, 100, 100);

            // Draw the base
            g2.setColor(Color.GRAY);
            g2.fillRect(475, 300, 50, 50);

            // Draw the filament
            g2.setColor(isLightOn ? Color.ORANGE : Color.DARK_GRAY);
            g2.drawLine(480, 250, 515, 250);

            g2.setColor(isLightOn ? Color.ORANGE : Color.DARK_GRAY);
            g2.drawLine(480, 250, 480, 300);

            g2.setColor(isLightOn ? Color.ORANGE : Color.DARK_GRAY);
            g2.drawLine(515, 250, 515, 300);

            isLightOn = !isLightOn;
        }
    }
}