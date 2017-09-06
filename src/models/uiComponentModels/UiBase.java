package models.uiComponentModels;

import javafx.scene.Group;

/**
 * Created by lzx on 2017/9/1.
 * used as a base class for ui components
 */
public class UiBase extends Group implements UiComponent {

    //x and y is the coordinate of the top left corner
    double x = 0;

    double y = 0;

    double width = 0;

    double height = 0;

    @Override
    public void setX(double value) {
        x = value;
    }

    @Override
    public void setY(double value) {
        y = value;
    }

    @Override
    public void setCenterX(double value) {
        x = value - (width / 2);
    }

    @Override
    public void setCenterY(double value) {
        y = value - (height / 2);
    }

    @Override
    public void setWidth(double value) {
        width = value;
    }

    @Override
    public void setHeight(double value) {
        width = value;
    }
}
