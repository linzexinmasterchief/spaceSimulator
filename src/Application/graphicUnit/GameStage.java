package Application.graphicUnit;

import Application.Launcher;
import Application.graphicUnit.mainStageComponents.GameScene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/7/6.
 * game stage
 */
public class GameStage extends Stage{

    private Launcher launcher;

    //create an object of the game scene
    private GameScene gameScene;

    public GameStage(Launcher starter){

        launcher = starter;


        //initialize components
        gameScene = new GameScene(new Group(), launcher.getScreenSize().width, launcher.getScreenSize().height - 35, this);

        //give the stage an start scene
        setScene(gameScene);
        //set non-resizable to avoid problems caused by different resolutions
        //this may be removed if I find out how to deal with it
        //give the application a title
        setTitle("SpaceSimulator");
        //exit on close
        setOnCloseRequest(event -> {
            System.exit(0);
        });
        //make sure the stage is completely filled with game scene
        sizeToScene();

        //icon
        getIcons().add(new Image("/resources/icon.png"));

        sizeToScene();

    }

    public GameScene getGameScene(){
        return gameScene;
    }

    public Launcher getLauncher(){
        return launcher;
    }

}
