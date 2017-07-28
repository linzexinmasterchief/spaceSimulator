package Application;

import Application.Engine.world;
import Application.stages.MainStage.GameStage;
import Application.stages.SettingStage.SettingStage;
import Application.status.CanvasStatus;
import Application.status.EngineStatus;
import Application.system.SystemSettings;
import Application.status.SystemStatus;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Created by lzx on 2017/3/21.
 * this is the root class, start stage
 */

public class Launcher extends Application {

    //object used to store world information
    private EngineStatus engineStatus;
    //object used to store system information
    private SystemStatus systemStatus;
    //object used to store canvas information
    private CanvasStatus canvasStatus;


    //game EngineSettings
    private SystemSettings systemSettings;

    private world world;

    private GameStage gameStage;
    //option menu
    private SettingStage settingStage;

    //launch the application
    public static void main(String[] args) {
        launch(args);
    }

    //override the default constructor
    @Override
    //initialize the application
    public void start(Stage stage) {
        //initialize world status
        engineStatus = new EngineStatus();
        //initialize system status
        systemStatus = new SystemStatus();
        //initialize canvas status
        canvasStatus = new CanvasStatus();

        //initialize system EngineSettings
        systemSettings = new SystemSettings();

        //create an object of the stage
        stage = new GameStage(this);
        //I am totally unsure if this piece of weired code would be a problem
        gameStage = (GameStage) stage;

        //setting window
        settingStage = new SettingStage(400, 400, 400, 400);
        settingStage.setOnCloseRequest(event -> systemStatus.setSettingStageOut(false));
        systemStatus.setSettingStageOut(false);

        //the position is critical
        world = new world(this);

        //add the window
        stage.show();
    }

    public SystemStatus getSystemStatus(){
        return systemStatus;
    }

    public world getWorld(){
        return world;
    }

    public GameStage getGameStage(){
        return gameStage;
    }

    public SettingStage getSettingStage(){
        return settingStage;
    }

    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    public void setSystemSettings(SystemSettings systemSettings) {
        this.systemSettings = systemSettings;
    }

    public EngineStatus getEngineStatus() {
        return engineStatus;
    }

    public void setEngineStatus(EngineStatus engineStatus) {
        this.engineStatus = engineStatus;
    }

    public CanvasStatus getCanvasStatus() {
        return canvasStatus;
    }

    public void setCanvasStatus(CanvasStatus canvasStatus) {
        this.canvasStatus = canvasStatus;
    }
}
