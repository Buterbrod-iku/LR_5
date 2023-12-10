import java.awt.*;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Rectangle2D {
    public MyPoint point =new MyPoint();
    private double width;
    private double height;

    public Rectangle(){

    }
    public Rectangle(double x, double y, double w, double h) {
        point.setLocation(x, y);
        this.width = w;
        this.height = h;
    }


    @Override
    public void setRect(double x, double y, double w, double h) {
        point.setLocation(x, y);
        this.width = w;
        this.height = h;

    }

    @Override
    public int outcode(double x, double y) {
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        return null;
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        return null;
    }

    @Override
    public double getX() {
        return point.getX();
    }


    @Override
    public double getY() {
        return point.getY();
    }

    @Override
    public double getWidth() {
        return width;
    }
    public  void setWidth(double w){
        this.width = w;
    }

    @Override
    public double getHeight() {
        return height;
    }
    public void setHeight(double h){
        this.height = h;
    }

    @Override
    public boolean isEmpty() {
        //if (width < 0 && height < 0) return  false;
        return true;
    }
}
