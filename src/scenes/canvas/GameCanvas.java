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


    public GameCanvas(double width, double height) {
        super(width, height);

        Star earth = new Star();
        Star.x = 250;
        Star.y = 280;

        stars = new Star[10];
        stars[0] = earth;

        for (int i = 1; i < stars.length; i++) {
            stars[i] = new Star();
            Star.x = i;
            Star.y = i;
        }

        setTranslateX(200);
        setTranslateY(0);
        setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < stars.length; i++) {
                    Star.speedX = (me.getX() - Star.r - Star.x) / 20;
                    Star.speedY = (me.getY() - Star.r - Star.y) / 20;
                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    Star.x = me.getX() - Star.r;
                    Star.y = me.getY() - Star.r;
                    Star.speedX = Star.speedY = 0;
                }
            }
        });
        setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                for (int i = 0; i < stars.length; i++) {
                    Star.speedX = (me.getX() - Star.r - Star.x) / 100;
                    Star.speedY = (me.getY() - Star.r - Star.y) / 100;
                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    Star.x = me.getX() - Star.r;
                    Star.y = me.getY() - Star.r;
                    Star.speedX = Star.speedY = 0;
                }
            }
            System.out.println(Star.x + ", " + Star.y);
        });

        gc = getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 800, 560);
        drawShapes(gc);

    }

    public static void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 560);
        gc.setFill(Color.BLUE);
        for (int i = 0; i < stars.length; i++) {
            gc.fillOval(Star.x, Star.y, Star.r * 2, Star.r * 2);
        }
    }

    public static void GameThread() {
        for (int i = 0; i < stars.length; i++) {
            Star.x = Star.x + Star.speedX;
            Star.y = Star.y + Star.speedY;
        }
        drawShapes(gc);
    }


}
