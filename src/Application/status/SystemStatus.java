package Application.status;

import javafx.scene.input.MouseButton;

/**
 * Created by lzx on 2017/7/13.
 * record current system status
 */
public class SystemStatus {

    //is create star menu out or not
    private boolean isCreateStarMenuOut;

    //is setting window out
    private boolean isSettingStageOut;

    //new star lock
    private boolean isNewStarExist;

    //drag line
    private double[] dragLine;



    //a variable used to store the scale between height and width
    private double HeightWidthScale;

    public SystemStatus(){
        //initialize crate star menu lock
        isCreateStarMenuOut = false;

        //initialize setting window lock
        isSettingStageOut = false;

        //close new star lock
        isNewStarExist = false;

        //initialize drag line
        dragLine = new double[4];

        //initialize the scale between height and width
        HeightWidthScale = 1;
    }

    public boolean isNewStarExist() {
        return isNewStarExist;
    }

    public void setNewStarExist(boolean newStarExist) {
        isNewStarExist = newStarExist;
    }

    public double getHeightWidthScale() {
        return HeightWidthScale;
    }

    public void setHeightWidthScale(double heightWidthScale) {
        HeightWidthScale = heightWidthScale;
    }

    public double[] getDragLine() {
        return dragLine;
    }

    public void setDragLine(double[] dragLine) {
        this.dragLine = dragLine;
    }


    public boolean isCreateStarMenuOut() {
        return isCreateStarMenuOut;
    }

    public void setCreateStarMenuOut(boolean createStarMenuOut) {
        isCreateStarMenuOut = createStarMenuOut;
    }

    public boolean isSettingStageOut() {
        return isSettingStageOut;
    }

    public void setSettingStageOut(boolean settingStageOut) {
        isSettingStageOut = settingStageOut;
    }
}
