package models;

/**
 * Created by lzx on 2017/9/1.
 */
public interface Component {

    //x and y is the coordinate of the top left corner
    double x = 0;
    double y = 0;
    double width = 0;
    double height = 0;
//    double centerX = x + (width / 2);
//    double centerY = x + (height / 2);

    void setX(double value);
    default double getX(){
        return x;
    }

    void setY(double value);
    default double getY(){
        return y;
    }

    //use the value of x to work out the value of centerX
    void setCenterX(double value);
    default double getCenterX(){
        return x + (width / 2);
    }

    //use the value of y to work out the value of centerY
    void setCenterY(double value);
    default double getCenterY(){
        return y + (height / 2);
    }

    void setWidth(double value);
    default double getWidth(){
        return width;
    }

    void setHeight(double value);

    default double getHeight(){
        return height;
    }

}
