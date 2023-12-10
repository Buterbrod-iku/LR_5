import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class laba5Z1 {
    static JFrame frame = new JFrame();
    static pictureBox image = new pictureBox();
    static sub_image sub = new sub_image();
    static  JLabel sub_im_text = new JLabel("Изменённая область");
    static int  box_x_beg;
    static int box_y_beg;
    static int box_x_end;
    static int box_y_end;
    static int x_center;
    static int y_center;
    static int brightness = 0;
    static double ratio = 1;
    static boolean isHold = false;
    static  boolean area = false;
    static public Rectangle fr = new Rectangle();


    public static void main(String[] args) {

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocation(700, 300);
        frame.setLayout(null);


        Font font = new Font("Verdana", Font.ITALIC, 18);

        sub_im_text.setFont(font);
        sub_im_text.setBounds(600, 0, 300, 25);
        sub_im_text.setVisible(false);
        frame.add(sub_im_text);

        image.setBounds(0, 0, image.x, image.y);
        frame.add(image);
        image.addMouseMotionListener(new mouse_move());
        image.addMouseListener(new mouse_hold());



        sub.setBounds(600, 0, 0, 0);
        sub.removeAll();
        frame.add(sub);

        frame.update(frame.getGraphics());

    }

    static class pictureBox extends JPanel
    {
        public BufferedImage im1;
        public BufferedImage im2;
        public int x;
        public int y;


        public pictureBox ()
        {
            try
            {
                im1 = ImageIO.read(new File("C:\\Users\\setInterval\\Desktop\\проекты\\java\\bg_1.jpg"));
                im2 = ImageIO.read(new File("C:\\Users\\setInterval\\Desktop\\проекты\\java\\bg_2.jpg"));
                x = im1.getWidth();
                y = im2.getHeight();
            }
            catch (IOException e)
            {
            }
        }

        public void paintComponent(Graphics g)
        {
            int x_beg = 0;
            int y_beg = 0;
            int x_end = 220;
            int y_end = 240;
            int x_pos = 300;
            int y_pos = 400;
            Graphics2D g2 = (Graphics2D) g;
            g.drawImage(im1, 0, 0, null);
            g.drawImage(im2, x_beg+x_pos, y_beg+y_pos, x_end+x_pos, y_end+y_pos, 330, 100, 550, 340,  null);
            if(isHold) {

                g2.draw(fr);
            }
            repaint();
        }
    }
    static class sub_image extends JPanel
    {
        public BufferedImage im;
        public int x_beg = 0;
        public int y_beg = 0;
        public int x_end = 0;
        public int y_end = 0;
        public sub_image ()
        {
            try
            {
                im = ImageIO.read(new File("C:\\Users\\setInterval\\Desktop\\проекты\\java\\bg_1.jpg"));
            }
            catch (IOException e)
            {
            }
        }

        public void paintComponent(Graphics g)
        {
            try
            {
                im = ImageIO.read(new File("C:\\Users\\setInterval\\Desktop\\проекты\\java\\bg_1.jpg"));
            }
            catch (IOException e)
            {
            }
            g.clearRect(0, 0,1000, 1000 );

            for(int x = x_beg; x < x_end; x++){
                for(int y = y_beg; y < y_end; y++){
                    Color c_old = new Color(im.getRGB(x, y));
                    int red = editColor(c_old.getRed(), brightness, ratio);
                    int green = editColor(c_old.getGreen(), brightness, ratio);
                    int blue = editColor(c_old.getBlue(), brightness, ratio);
                    Color c_new = new Color(red, green, blue);
                    im.setRGB(x, y, c_new.getRGB());

                }
            }
                g.drawImage(im, 0, 0, x_end-x_beg, y_end-y_beg, x_beg, y_beg, x_end, y_end,  null);

        }
    }
    public static class mouse_move implements MouseMotionListener {


        @Override
        public void mouseDragged(MouseEvent e) {
                if(area) {
                    box_x_end = e.getX();
                    box_y_end = e.getY();
                    box_x_beg = x_center;
                    box_y_beg = y_center;
                    if(x_center > box_x_end){
                        int temp = box_x_beg;
                        box_x_beg = box_x_end;
                        box_x_end = temp;
                    }
                    if(y_center > box_y_end){
                        int temp = box_y_beg;
                        box_y_beg = box_y_end;
                        box_y_end = temp;

                    }
                    fr.point.setLocation(box_x_beg, box_y_beg);
                    fr.setWidth(box_x_end - box_x_beg);
                    fr.setHeight(box_y_end - box_y_beg);
                    image.repaint();
                }

        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
    public static class mouse_hold implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            isHold = true;
            x_center = e.getX();
            y_center =e.getY();
            box_x_beg = x_center;
            box_y_beg = y_center;
            box_x_end = e.getX();
            box_y_end =e.getY();
            fr.point.setLocation(box_x_beg, box_y_beg);
            fr.setWidth(0);
            fr.setHeight(0);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
                isHold = false;
                sub.x_beg = box_x_beg;
                sub.y_beg = box_y_beg;
                sub.x_end = box_x_end;
                sub.y_end = box_y_end;
                sub.setSize(box_x_end - box_x_beg, box_y_end - box_y_beg);
                sub_im_text.setVisible(true);
                sub_im_text.setLocation(600, box_y_end-box_y_beg+10);
                sub.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            area = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            area = false;
        }
    }
    public static int editColor(int color, int brightness, double ratio){
        int result = 255 - color;
        if (result > 255) return  result;
        else  if(result < 0) return  result;
        return result;
    }


}