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

    //toggle esc
    private static boolean isSimulationPaused;

    //a variable used to store the scale between height and width
    private static float HeightWidthScale;

    //store screen height
    private static float screenHeight;

    //store screen width
    private static float screenwidth;

    public SystemStatus(){
        //initialize crate star menu lock
        isCreateStarMenuOut = false;

        //initialize setting window lock
        isSettingStageOut = false;

        //close new star lock
        isNewStarExist = false;

        //init simulation not paused
        isSimulationPaused = false;

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

    public static boolean isSimulationPaused() {
        return isSimulationPaused;
    }

    public static void setSimulationPaused(boolean isSimulationPaused) {
        SystemStatus.isSimulationPaused = isSimulationPaused;
    }

    public static float getHeightWidthScale() {
        return HeightWidthScale;
    }

    public static void setHeightWidthScale(float heightWidthScale) {
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

    public static float getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(float screenHeight) {
        SystemStatus.screenHeight = screenHeight;
    }

    public static float getScreenwidth() {
        return screenwidth;
    }

    public static void setScreenwidth(float screenwidth)
    {
        SystemStatus.screenwidth = screenwidth;
    }
}
