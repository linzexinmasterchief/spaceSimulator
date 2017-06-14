package models;

import javafx.scene.control.Slider;

/**
 * Created by lzx on 2017/6/14.
 * a menu slider model
 */
public class MenuSliderPrefab extends Slider {

    public MenuSliderPrefab(){
        setMinWidth(200);
        setVisible(false);
        setShowTickLabels(true);
        setShowTickMarks(true);
    }

}
