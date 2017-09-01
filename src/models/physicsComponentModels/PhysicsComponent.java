package models.physicsComponentModels;
/**
 * Created by lzx on 2017/9/1.
 */
public interface PhysicsComponent{

    //x and y is the coordinate of the top left corner
    float x = 0;
    float y = 0;
    float width = 0;
    float height = 0;

    void setX(float value);
    default float getX(){
        return x;
    }

    void setY(float value);
    default float getY(){
        return y;
    }

    //use the value of x to work out the value of centerX
    void setCenterX(float value);
    default float getCenterX(){
        return x + (width / 2);
    }

    //use the value of y to work out the value of centerY
    void setCenterY(float value);
    default float getCenterY(){
        return y + (height / 2);
    }

    void setWidth(float value);
    default float getWidth(){
        return width;
    }

    void setHeight(float value);
    default float getHeight(){
        return height;
    }
}
