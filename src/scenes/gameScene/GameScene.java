package scenes.gameScene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.gameScene.canvas.GameCanvas;

public class GameScene extends Scene {

    private static GameCanvas gameCanvas;
    private boolean clicked;

    //constructor
    public GameScene(Group root, double width, double height, Stage rootStage) {

        //construct the game scene as a scene
        super(root, width, height);

        setFill(Color.BLACK);

        clicked = false;
        //add a menu
        Button Menu01 = new Button("Button");
        Menu01.setMinWidth(200);
        root.getChildren().add(Menu01);

        //initialize the canvas
        //size
        gameCanvas = new GameCanvas(1000, 560);
        //add game canvas
        root.getChildren().add(gameCanvas);

        //add a menu button
        Button button = new Button("=");
        button.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null)));
        button.setOnMouseEntered(me -> button.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), null))));
        button.setOnMouseExited(me -> button.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null))));
        button.setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                gameCanvas.setTranslateX(200);
                gameCanvas.setWidth(gameCanvas.getWidth() - 200);
                gameCanvas.getCamera().setCenterX(gameCanvas.getCamera().getCenterX() + 200 * gameCanvas.getScaleX_OVERIDE());
            } else {
                gameCanvas.setWidth(gameCanvas.getWidth() + 200);
                gameCanvas.setTranslateX(0);
                gameCanvas.getCamera().setCenterX(gameCanvas.getCamera().getCenterX() - 200 * gameCanvas.getScaleX_OVERIDE());
            }
        });
        button.setTranslateX(10);
        button.setTranslateY(10);
        root.getChildren().add(button);
    }


    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}