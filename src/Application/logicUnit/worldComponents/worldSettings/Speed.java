package Application.logicUnit.worldComponents.worldSettings;

/**
 * Created by lzx on 2017/7/13.
 * class used to store speed settings
 */
public class Speed {
    //determine how fast the star moves with the same drag distance
    //larger the value, slower the speed
    private static int dragSpeedConstant = 1000;
    //determine how fast the camera enlarge/minify
    private static int sizeChangeSpeed = 20;
    //determine how fast the camera moves to the mouse coordinate when scrolling on a point
    private static int cameraMoveSpeed = 20;
    //determine how fast the time goes in the universe (does not affect the speed of thread)
    private static int timeChangingSpeed = 1;

    public static int getDragSpeedConstant() {
        return dragSpeedConstant;
    }

    public static void setDragSpeedConstant(int dragSpeedConstant) {
        Speed.dragSpeedConstant = dragSpeedConstant;
    }

    public static int getSizeChangeSpeed() {
        return sizeChangeSpeed;
    }

    public static void setSizeChangeSpeed(int sizeChangeSpeed) {
        Speed.sizeChangeSpeed = sizeChangeSpeed;
    }

    public static int getCameraMoveSpeed() {
        return cameraMoveSpeed;
    }

    public static void setCameraMoveSpeed(int cameraMoveSpeed) {
        Speed.cameraMoveSpeed = cameraMoveSpeed;
    }
}
