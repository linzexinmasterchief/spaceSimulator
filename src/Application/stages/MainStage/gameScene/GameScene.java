package Application.stages.MainStage.gameScene;

import Application.stages.MainStage.GameStage;
import Application.stages.MainStage.gameScene.canvas.GameCanvas;
import Application.stages.SettingStage.SettingStage;
import javafx.scene.Group;
import javafx.scene.Scene;
import models.UIComponents.BottomStatusBar;
import models.UIComponents.CircularButton;
import models.UIComponents.MenuButtonPrefab;
import models.UIComponents.MenuSliderPrefab;

public class GameScene extends Scene {

    private boolean clicked;

    //create reference to root game stage
    private GameStage gameStage;

    //give access to the canvas
    private GameCanvas gameCanvas;

    //menu components
    private MenuButtonPrefab settingBtn;
    private MenuSliderPrefab massSlider;
    private MenuSliderPrefab raiusSlider;

    private BottomStatusBar bottomStatusBar;
    //menu toggle
    private CircularButton menuToggleButton;

    //option menu
    private SettingStage settingWindow;
    private boolean settingToggled;

    //constructor
    public GameScene(Group root, double width, double height, GameStage gameStage) {

        //construct the game scene as a scene
        super(root, width, height);
        this.gameStage = gameStage;

        clicked = false;

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
        settingBtn = new MenuButtonPrefab("setting");
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

        massSlider = new MenuSliderPrefab(40, 10);
        root.getChildren().add(massSlider);

        raiusSlider = new MenuSliderPrefab(80, 5);
        raiusSlider.setMax(20);
        root.getChildren().add(raiusSlider);

        //add a menuButton
        menuToggleButton = new CircularButton("+");
        menuToggleButton.setTranslateX(5);
        menuToggleButton.setTranslateY(5);
        menuToggleButton.setMinWidth(24);
        menuToggleButton.setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //menuButton change
                menuToggleButton.setTranslateX(170);
                menuToggleButton.setText("-");
                //pop out
                settingBtn.setVisible(true);
                massSlider.setVisible(true);
                raiusSlider.setVisible(true);

            } else {
                //menuButton change
                menuToggleButton.setTranslateX(5);
                menuToggleButton.setText("+");
                //goes back
                settingBtn.setVisible(false);
                massSlider.setVisible(false);
                raiusSlider.setVisible(false);
            }
        });
        root.getChildren().add(menuToggleButton);

        //add bottom status bar
        bottomStatusBar = new BottomStatusBar(this);
        MenuButtonPrefab[] statusBar = bottomStatusBar.getStatusElements();
        for (MenuButtonPrefab aStatusBar : statusBar) {
            if (aStatusBar != null) {
                root.getChildren().add(aStatusBar);
            }
        }
    }


    private boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public BottomStatusBar getStatusBar(){
        return bottomStatusBar;
    }

    public double getMass(){
        return massSlider.getValue();
    }

    public double getRadius(){
        return raiusSlider.getValue();
    }

}