package scenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import scenes.canvas.GameCanvas;

public class GameScene extends Scene {

    private static GameCanvas gameCanvas;

    public GameScene(Group root, double width, double height) {
        super(root, width, height);
        setFill(Color.BLACK);
        gameCanvas = new GameCanvas(1000, 560);
        gameCanvas.setWidth(1000);
        gameCanvas.setHeight(560);
        root.getChildren().add(gameCanvas);
    }

}