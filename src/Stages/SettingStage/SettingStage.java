package Stages.SettingStage;

import javafx.stage.Stage;

/**
 * Created by lzx on 2017/6/14.
 */
public class SettingStage extends Stage{

    public SettingStage(double centerX, double centerY, double width, double height){
        setX(centerX - width / 2);
        setY(centerY - height / 2);
        setWidth(width);
        setHeight(height);


    }

}
