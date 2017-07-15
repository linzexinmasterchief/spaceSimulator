package models.UIComponents;

import javafx.scene.control.Slider;

/**
 * Created by lzx on 2017/6/14.
 * a menu slider model
 */
public class MenuSliderModel extends Slider {

    public MenuSliderModel(double yPos, double start){
        setMinWidth(150);
        setVisible(false);
        setShowTickLabels(true);
        setShowTickMarks(true);
        setTranslateX(5);
        setMin(0);
        setValue(start);
        setMajorTickUnit(20);
        setMinorTickCount(5);
        setBlockIncrement(5);
        setTranslateY(yPos);
    }

}
