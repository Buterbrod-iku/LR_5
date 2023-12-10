import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
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
        Color[] lightColors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};

        public Background() {
            try {
                im = ImageIO.read(new File("C:\\Users\\setInterval\\Desktop\\проекты\\LR5_2\\gg.png"));
            } catch (IOException ignored) {
            }
        }

        public void paintComponent(Graphics g) {
            g.drawImage(im, 0, 0, 1000, 500, null);
            Graphics2D g2 = (Graphics2D) g;

            Color MyGreen = new Color(0, 180, 0);
            g2.setColor(MyGreen);
            g2.fillPolygon(new int[]{500, 450, 550}, new int[]{150, 250, 250}, 3);
            g2.fillPolygon(new int[]{500, 400, 600}, new int[]{250, 350, 350}, 3);
            g2.fillPolygon(new int[]{500, 350, 650}, new int[]{350, 450, 450}, 3);

            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(490, 450, 20, 50);

            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                int x = random.nextInt(200) + 400;
                int y = random.nextInt(300) + 150;
                Color lightColor = lightColors[random.nextInt(lightColors.length)];
                g2.setColor(lightColor);

                if ((y >= 150 && y <= 250 && x >= 500 - (y - 150) / 2 && x <= 500 + (y - 150) / 2)
                        || (y > 250 && y <= 350 && x >= 500 - (y - 250) && x <= 500 + (y - 250))
                        || (y > 350 && y <= 450 && x >= 500 - (y - 350) * 3/2 && x <= 500 + (y - 350) * 3/2)) {
                    g2.fillOval(x, y, 10, 10);
                }
            }
        }
    }
}
