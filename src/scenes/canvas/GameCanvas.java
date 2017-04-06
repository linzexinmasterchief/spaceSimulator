package scenes.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;

import static javafx.scene.paint.Color.BLACK;

/**
 * Created by lzx on 2017/4/6.
 */
public class GameCanvas extends Canvas {

    public static GraphicsContext gc;

    public static Star[] stars;

    public static double speedX = 0;
    public static double speedY = 0;

    public GameCanvas(double width, double height) {
        super(width, height);

        stars = new Star[50];

        Star earth = new Star();
        earth.setCenterX(250);
        earth.setCenterY(280);
        stars[0] = earth;

        setTranslateX(200);
        setTranslateY(0);
        setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - stars[0].getCenterX()) / 20;
                speedY = (me.getY() - 20 - stars[0].getCenterY()) / 20;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                stars[0].setCenterX(me.getX() - 20);
                stars[0].setCenterY(me.getY() - 20);
                speedX = speedY = 0;
            }
        });
        setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - stars[0].getCenterX()) / 100;
                speedY = (me.getY() - 20 - stars[0].getCenterY()) / 100;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                stars[0].setCenterX(me.getX() - 20);
                stars[0].setCenterY(me.getY() - 20);
                speedX = speedY = 0;
            }
        });

        gc = getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 825, 561);
        drawShapes(gc);

    }

    public static void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 825, 561);
        gc.setFill(Color.BLUE);
        gc.fillOval(stars[0].getCenterX(), stars[0].getCenterY(), 40, 40);
    }

}
