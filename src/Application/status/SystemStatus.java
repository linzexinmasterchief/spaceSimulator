package Application.status;


/**
 * Created by lzx on 2017/7/13.
 * record current system status
 */
public class SystemStatus {

    //is create star menu out or not
    private static boolean isCreateStarMenuOut;

    //is setting window out
    private static boolean isSettingStageOut;

    //new star lock
    private static boolean isNewStarExist;

    //a variable used to store the scale between height and width
    private static double HeightWidthScale;

    //store screen height
    private static double screenHeight;

    //store screen width
    private static double screenwidth;

    public SystemStatus(){
        //initialize crate star menu lock
        isCreateStarMenuOut = false;

        //initialize setting window lock
        isSettingStageOut = false;

        //close new star lock
        isNewStarExist = false;

        //initialize the scale between height and width
        HeightWidthScale = 1;

        //initialize screen size
        screenHeight = 0;
        screenwidth = 0;
    }

    public static boolean isNewStarExist() {
        return isNewStarExist;
    }

    public static void setNewStarExist(boolean newStarExist) {
        isNewStarExist = newStarExist;
    }

    public static double getHeightWidthScale() {
        return HeightWidthScale;
    }

    public static void setHeightWidthScale(double heightWidthScale) {
        HeightWidthScale = heightWidthScale;
    }

    public static boolean isCreateStarMenuOut() {
        return isCreateStarMenuOut;
    }

    public static void setCreateStarMenuOut(boolean createStarMenuOut) {
        isCreateStarMenuOut = createStarMenuOut;
    }

    public static boolean isSettingStageOut() {
        return isSettingStageOut;
    }

    public static void setSettingStageOut(boolean settingStageOut) {
        isSettingStageOut = settingStageOut;
    }

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(double screenHeight) {
        SystemStatus.screenHeight = screenHeight;
    }

    public static double getScreenwidth() {
        return screenwidth;
    }

    public static void setScreenwidth(double screenwidth) {
        SystemStatus.screenwidth = screenwidth;
    }
}
