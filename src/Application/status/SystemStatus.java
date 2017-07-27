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

    //mouse coordinate
    private double[] mouse_coordinate;
    //mouse scroll length
    private double mouseScrollValue;
    //activated mouse button
    private MouseButton activatedMouseButton;
    //mouse physicsStatus
    private boolean MousePressed;
    private boolean MouseReleased;
    private boolean MouseScrolled;

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

        //initialize the variable used to store mouse operations
        mouse_coordinate = new double[2];
        mouseScrollValue = 0;
        activatedMouseButton = MouseButton.NONE;
        MousePressed = false;
        MouseReleased = false;
        MouseScrolled = false;

        //initialize the scale between height and width
        HeightWidthScale = 1;
    }

    public boolean isMousePressed() {
        return MousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        MousePressed = mousePressed;
    }

    public boolean isMouseReleased() {
        return MouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        MouseReleased = mouseReleased;
    }

    public boolean isMouseScrolled() {
        return MouseScrolled;
    }

    public void setMouseScrolled(boolean mouseScrolled) {
        MouseScrolled = mouseScrolled;
    }

    public boolean isNewStarExist() {
        return isNewStarExist;
    }

    public void setNewStarExist(boolean newStarExist) {
        isNewStarExist = newStarExist;
    }

    public double[] getMouse_coordinate() {
        return mouse_coordinate;
    }

    public void setMouse_coordinate(double[] mouse_coordinate) {
        this.mouse_coordinate = mouse_coordinate;
    }

    public double getHeightWidthScale() {
        return HeightWidthScale;
    }

    public void setHeightWidthScale(double heightWidthScale) {
        HeightWidthScale = heightWidthScale;
    }

    public MouseButton getActivatedMouseButton() {
        return activatedMouseButton;
    }

    public void setActivatedMouseButton(MouseButton activatedMouseButton) {
        this.activatedMouseButton = activatedMouseButton;
    }

    public double[] getDragLine() {
        return dragLine;
    }

    public void setDragLine(double[] dragLine) {
        this.dragLine = dragLine;
    }

    public double getMouseScrollValue() {
        return mouseScrollValue;
    }

    public void setMouseScrollValue(double mouseScrollValue) {
        this.mouseScrollValue = mouseScrollValue;
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
