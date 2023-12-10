import java.awt.geom.Point2D;

public class MyPoint extends Point2D {
    private double x;
    private double y;
    MyPoint() {

    }
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
