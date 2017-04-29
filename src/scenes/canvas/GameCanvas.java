package scenes.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 */
public class GameCanvas extends Canvas implements Runnable {

    public static int InputMass;
    public static double InputVectorX;
    public static double InputVectorY;
    public static double InputAccelerationX;
    public static double InputAccelerationY;
    private static GraphicsContext gc;
    private static GravityCalculate gravityCalculate;
    private static Star[] stars = new Star[50];
    private static boolean NEW;
    private static int MEX;
    private static int MEY;

    public GameCanvas(double width, double height) {
        super(width, height);
        InputMass = 0;
        InputVectorX = 0;
        InputVectorY = 0;
        InputAccelerationX = 0;
        InputAccelerationY = 0;

        NEW = false;
        MEX = 0;
        MEY = 0;

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
        setTranslateX(200);
        setTranslateY(0);

        setOnMouseDragged(me -> requestFocus());
        setOnMouseClicked(me -> {
            requestFocus();
            if (me.getButton() == MouseButton.PRIMARY) {
                NEW = true;
                MEX = (int) me.getX();
                MEY = (int) me.getY();
            }
        });

        gc = getGraphicsContext2D();

        drawShapes();
        gravityCalculate = new GravityCalculate(stars);
        Thread thread = new Thread(this);
        thread.start();

    }

    private void drawShapes() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 800, 560);
        gc.setFill(Color.BLUE);

        for (Star star : stars) {
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(star.x, star.y, star.r * 2, star.r * 2);
            }
        }
    }

    public void clear() {
        for (Star star : stars) {
            star.remove();
        }
    }

    private void GameThread() {
        for (int i = 0; i < stars.length; i++) {
            checkBound(i);
            if (stars[i].x > 810 + (2 * stars[i].r)
                    | stars[i].y > 570 + (2 * stars[i].r)
                    | stars[i].x < -10 - (2 * stars[i].r)
                    | stars[i].y < -10 - (2 * stars[i].r)) {
                stars[i].remove();
            }
            if (stars[i].onScreen) {
                stars[i].move();
                final int F = i;
                Platform.runLater(() -> {
                    gravityCalculate.synchronize(stars);
                    gravityCalculate.gravityAcceleration(stars[F]);
                });

            } else {
                stars[i].initialize();
                if (NEW) {
                    stars[i].show(MEX - stars[i].r, MEY - stars[i].r);
                    stars[i].mass = InputMass;
                    stars[i].speedX = InputVectorX;
                    stars[i].speedY = InputVectorY;
                    stars[i].accelerationX = InputAccelerationX;
                    stars[i].accelerationY = InputAccelerationY;
                    NEW = false;
                }
            }
        }
        Platform.runLater(this::drawShapes);
    }

    private void checkBound(int i) {
        if (stars[i].mass > 5000) {
            stars[i].mass = 5000;
        } else if (stars[i].mass < -5000) {
            stars[i].mass = -5000;
        }
        if (stars[i].speedX > 1000) {
            stars[i].speedX = 1000;
        } else if (stars[i].speedX < -1000) {
            stars[i].speedX = -1000;
        }
        if (stars[i].speedY > 1000) {
            stars[i].speedY = 1000;
        } else if (stars[i].speedY < -1000) {
            stars[i].speedY = -1000;
        }
        if (stars[i].accelerationX > 500) {
            stars[i].accelerationX = 500;
        } else if (stars[i].accelerationX < -500) {
            stars[i].accelerationX = -500;
        }
        if (stars[i].accelerationY > 500) {
            stars[i].accelerationY = 500;
        } else if (stars[i].accelerationY < -500) {
            stars[i].accelerationY = -500;
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameThread();
        }
    }
}
