using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace laba5Z1
{
    public partial class Form1 : Form
    {
        bool isHold;
        bool area;
        bool draw_sub_im;

        int x_center;
        int y_center;

        int x_beg;
        int y_beg;

        int x_end;
        int y_end;

        int brightness = 0;
        double ratio = 1;
        public Form1()
        {
            InitializeComponent();
        }

        void Work()
        {
            Bitmap im_1 = new Bitmap("D:\\visual studio\\cs\\laba5Z1\\eola.jpg");
            Bitmap im_sub = new Bitmap("D:\\visual studio\\cs\\laba5Z1\\eola.jpg");
            Bitmap im_2 = new Bitmap("D:\\visual studio\\cs\\laba5Z1\\ganyu.jpg");
            int w = im_1.Width;
            int h = im_1.Height;

            Rectangle rec = new Rectangle(0, 0, w, h);
            Rectangle rec_1 = new Rectangle(300, 500, 200, 200);
            Rectangle rec_2 = new Rectangle(320, 110, 210, 210);

            Bitmap bmp = new Bitmap(w, h);
            pictureBox1.Image = bmp;
            pictureBox1.Width = w; 
            pictureBox1.Height = h;
            Graphics g = Graphics.FromImage(bmp);

            if (!isHold)g.Clear(Color.Empty);
            g.DrawImage(im_1, rec);
            GraphicsUnit gUnit = GraphicsUnit.Pixel;
            g.DrawImage(im_2, rec_1, rec_2, gUnit);

            Rectangle frame = new Rectangle();
            if (isHold)
            {
                frame.X = x_beg;
                frame.Y = y_beg;
                frame.Width = x_end - x_beg;
                frame.Height = y_end - y_beg;
                Pen pen = new Pen(Color.Black);
                g.DrawRectangle(pen, frame);
            }
            Rectangle sub_rec_1 = new Rectangle(0, 0, x_end - x_beg, y_end - y_beg);
            Rectangle sub_rec_2 = new Rectangle(x_beg, y_beg, x_end - x_beg, y_end - y_beg);

            if (draw_sub_im && (x_beg != x_end || y_beg != y_end))
            {
                if (isInt(textBox1.Text)) brightness = Convert.ToInt32(textBox1.Text);
                if(isInt(textBox2.Text)) ratio = Convert.ToInt32(textBox2.Text);

                draw_sub_im = false;
                Bitmap sub_bmp = new Bitmap(x_end - x_beg, y_end - y_beg);
                pictureBox2.Width = w; 
                pictureBox2.Height = h;
                pictureBox2.Image = sub_bmp;
                Graphics sub_g = Graphics.FromImage(sub_bmp);

                for (int i = x_beg; i <= Math.Min(x_beg, im_sub.Width); i++)
                {
                    for (int j = y_beg; j <= Math.Min(y_beg, im_sub.Height); j++)
                    {
                        Color c =  im_sub.GetPixel(i, j);
                        int red = editColor(c.R, brightness, ratio);
                        int green = editColor(c.G, brightness, ratio);
                        int blue = editColor(c.B, brightness, ratio);
                        c = Color.FromArgb(red, green, blue);
                        im_sub.SetPixel(i, j, c);
                    }
                }
                sub_g.DrawImage(im_sub, sub_rec_1, sub_rec_2, gUnit);

                label3.Location = new System.Drawing.Point(pictureBox2.Location.X, y_end-y_beg+20);
                label3.Visible = true;
            }
            else label3.Visible = false;
        }

        private void Form1_Load_1(object sender, EventArgs e)
        {
            Work();
        }
        private void pictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            x_center = e.X;
            y_center = e.Y;
            x_beg = e.X;
            y_beg = e.Y;
            x_end = e.X;
            y_end = e.Y;
            isHold = true;
            Work();
            pictureBox1.Refresh();
        }
        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {
            isHold = false;
            draw_sub_im = true;
            Work();
            pictureBox1.Refresh();
            pictureBox2.Refresh();
            x_beg = 0;
            y_beg = 0;
            x_end = 0;
            y_end = 0;
            x_end = 0;
        }
        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            if (isHold && area)
            {
                x_end = e.X;
                y_end = e.Y;
                x_beg = x_center;
                y_beg = y_center;
                if (x_center > x_end)
                {
                    int temp = x_beg;
                    x_beg = x_end;
                    x_end = temp;
                }
                if (y_center > y_end)
                {
                    int temp = y_beg;
                    y_beg = y_end;
                    y_end = temp;

                }
                Work();
                pictureBox1.Refresh();
            }
        }

        private void pictureBox1_MouseEnter(object sender, EventArgs e)
        {
            area = true;
        }

        private void pictureBox1_MouseLeave(object sender, EventArgs e)
        {
            area = false;
        }

        public static int editColor(int color, int brightness, double ratio)
        {
            int result = (int)Math.Round((color + brightness) * ratio);
            if (result > 255) return 255;
            else if (result < 0) return 0;
            return result;
        }

        public bool isDouble(String str)
        {
            try
            {
                double num = Convert.ToDouble(str);
            }
            catch (FormatException e)
            {
                return false;
            }
            return true;

        }
        public bool isInt(String str)
        {
            try
            {
                double num = Convert.ToInt32(str);
            }
            catch (FormatException e)
            {
                return false;
            }
            return true;

        }
    }
}
