package scenes.gameScene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import scenes.gameScene.canvas.GameCanvas;

public class GameScene extends Scene {

    private static GameCanvas gameCanvas;

    //constructor
    public GameScene(Group root, double width, double height) {

        //construct the game scene as a scene
        super(root, width, height);

        //initialize the canvas
        //size
        gameCanvas = new GameCanvas(1000, 560);
        //add game canvas
        root.getChildren().add(gameCanvas);

        //add a menu
        MenuButton menuButton = new MenuButton("=");
        menuButton.getItems().addAll(new MenuItem("ha"));
        menuButton.setTranslateX(10);
        menuButton.setTranslateY(10);
        menuButton.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        menuButton.setTextFill(Color.WHITE);
        root.getChildren().add(menuButton);
    }

}