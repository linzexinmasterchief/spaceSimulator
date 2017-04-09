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
    public static Star[] stars = new Star[50];
    public static double InputMass;
    public static double InputVectorX;
    public static double InputVectorY;

    public GameCanvas(double width, double height) {
        super(width, height);
        InputMass = 0;
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
        setTranslateX(200);
        setTranslateY(0);

        setOnMouseDragged(me -> {
            requestFocus();
            if (me.getButton() == MouseButton.PRIMARY) {
//                for (int i = 0; i < stars.length; i++) {
//                    stars[i].accelerationX = (me.getX() - stars[i].r - stars[i].x) / stars[i].mass;
//                    stars[i].accelerationY = (me.getY() - stars[i].r - stars[i].y) / stars[i].mass;
//                }
            }
        });
        setOnMouseClicked(me -> {
            requestFocus();
            if (me.getButton() == MouseButton.PRIMARY) {
//                for (int i = 0; i < stars.length; i++) {
//                    stars[i].accelerationX = (me.getX() - stars[i].r - stars[i].x) / stars[i].mass;
//                    stars[i].accelerationY = (me.getY() - stars[i].r - stars[i].y) / stars[i].mass;
//                }
            } else if (me.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < stars.length; i++) {
                    if (stars[i].onScreen == false) {
                        stars[i].show(me.getX() - stars[i].r, me.getY() - stars[i].r);
                        stars[i].mass = InputMass;
                        stars[i].speedX = InputVectorX;
                        stars[i].speedY = InputVectorY;
                        drawShapes(gc);
                        return;
                    }
                }
            }
        });

        gc = getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 800, 560);
        drawShapes(gc);
    }

    public void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 560);
        gc.setFill(Color.BLUE);

        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen) {
                gc.fillOval(stars[i].x, stars[i].y, stars[i].r * 2, stars[i].r * 2);
            }
        }
    }

    public void gravityAcceleration(Star s) {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].onScreen & stars[i] != s) {
                double xDiff = stars[i].x - s.x;
                double yDiff = stars[i].y - s.y;
                double distance = xDiff * xDiff + yDiff * yDiff;
                if (distance != 0 & s.mass != 0) {
                    s.accelerationX += 0.6673 * stars[i].mass * s.mass / distance;
                    s.accelerationY += 0.6673 * stars[i].mass * s.mass / distance;
                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].remove();
        }
    }
    public void GameThread() {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].x > 800 | stars[i].y > 560 | stars[i].x < 0 | stars[i].y < 0) {
                stars[i].remove();
            }
            if (stars[i].onScreen) {
                gravityAcceleration(stars[i]);
                stars[i].move();
            }
        }
        drawShapes(gc);
    }

}
