package Application.stages.MainStage.gameScene;

import Application.stages.MainStage.GameStage;
import Application.stages.MainStage.gameScene.UIprefabs.CreateStarMenuSwitch;
import Application.stages.MainStage.gameScene.canvas.GameCanvas;
import Application.stages.SettingStage.SettingStage;
import javafx.scene.Group;
import javafx.scene.Scene;
import Application.stages.MainStage.gameScene.UIprefabs.universeStatusBar.UniverseStatusBar;
import models.UIComponents.CircularButton;
import models.UIComponents.MenuButtonModel;
import models.UIComponents.MenuSliderModel;

public class GameScene extends Scene {

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    private GameCanvas gameCanvas;

    //menu components
    private MenuButtonModel settingBtn;
    private MenuSliderModel massSlider;
    private MenuSliderModel raiusSlider;

    private UniverseStatusBar UniverseStatusBar;
    //menu toggle
    private CreateStarMenuSwitch createStarMenuSwitch;

    //option menu
    private SettingStage settingWindow;
    private boolean settingToggled;

    //constructor
    public GameScene(Group root, double width, double height, GameStage gameStage) {

        //construct the game scene as a scene
        super(root, width, height);
        this.gameStage = gameStage;

        //initialize the canvas
        //size
        gameCanvas = new GameCanvas(1000, 560, this);
        //add game canvas
        root.getChildren().add(gameCanvas);

        //listener for activating the console
//        setOnKeyTyped(ke -> {
//            if (ke.getCharacter().equals('~')
//                    | ke.getCharacter().equals('`')) {
//
//            }
//        });

        //setting window
        settingWindow = new SettingStage(400, 400, 400, 400);
        settingWindow.setOnCloseRequest(event -> settingToggled = false);
        settingToggled = false;

        //add a menu
        //setting menuButton
        settingBtn = new MenuButtonModel("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(5);
        settingBtn.setMinWidth(150);
        settingBtn.setOnAction(ae -> {
            settingToggled = !settingToggled;
            if (settingToggled) {
                settingWindow.show();
            }else {
                settingWindow.hide();
            }
        });
        root.getChildren().add(settingBtn);

        massSlider = new MenuSliderModel(40, 10);
        root.getChildren().add(massSlider);

        raiusSlider = new MenuSliderModel(80, 5);
        raiusSlider.setMax(20);
        root.getChildren().add(raiusSlider);

        //add a menuButton
        createStarMenuSwitch = new CreateStarMenuSwitch("+",this);

        root.getChildren().add(createStarMenuSwitch);

        //add bottom physicsStatus bar
        UniverseStatusBar = new UniverseStatusBar(this);
        MenuButtonModel[] statusBar = UniverseStatusBar.getStatusElements();
        for (MenuButtonModel aStatusBar : statusBar) {
            if (aStatusBar != null) {
                root.getChildren().add(aStatusBar);
            }
        }
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public UniverseStatusBar getStatusBar(){
        return UniverseStatusBar;
    }

    public MenuButtonModel getSettingBtn(){
        return settingBtn;
    }

    public MenuSliderModel getMassSlider(){
        return massSlider;
    }

    public MenuSliderModel getRaiusSlider(){
        return raiusSlider;
    }

    public double getMass(){
        return massSlider.getValue();
    }

    public double getRadius(){
        return raiusSlider.getValue();
    }

}