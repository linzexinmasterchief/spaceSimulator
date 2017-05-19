package scenes.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;
import physics.GravityCalculate;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 */
public class GameCanvas extends Canvas implements Runnable {

    private Star bufferStar;
    private GraphicsContext gc;
    private GravityCalculate gravityCalculate;
    private Star[] stars = new Star[50];

    private int mouseEventX;
    private int mouseEventY;

    private boolean isExit;
    private boolean isPause;
    private boolean isNewStarExist;

    public GameCanvas() {
        bufferStar = new Star();

        mouseEventX = 0;
        mouseEventY = 0;

        isExit = false;
        isPause = false;
        isNewStarExist = false;

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }

        double[] mouse_coordinate = new double[2];
        setOnMousePressed(me -> {
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
        });

        setOnMouseReleased(me -> {
            System.out.println("Mouse drag exited");
            bufferStar.vectorX = (me.getX() - mouse_coordinate[0]) / 100;
            bufferStar.vectorY = (me.getY() - mouse_coordinate[1]) / 100;

            if (me.getButton() == MouseButton.PRIMARY) {
                isNewStarExist = true;
                mouseEventX = (int) me.getX();
                mouseEventY = (int) me.getY();

                for (int i = 0; i < stars.length; i++) {
                    checkBound(i);
                    if (!stars[i].onScreen) {
                        stars[i].initialize();
                        if (isNewStarExist) {
                            stars[i] = new Star(bufferStar);
                            bufferStar.remove();

                            stars[i].show(mouseEventX, mouseEventY);
                            stars[i].onScreen = true;
                            isNewStarExist = false;
                            drawShapes();
                        }
                    }
                }
            }
            if (me.getButton() == MouseButton.SECONDARY) {
                clear();
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
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        gc.setFill(Color.BLUE);

        for (Star star : stars) {
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(star.centerX - star.r, star.centerY - star.r, star.r * 2, star.r * 2);
            }
        }
    }

    private void clear() {
        for (Star star : stars) {
            star.remove();
        }
    }

    private void GameThread() {
        if (isPause) {
            return;
        }
        for (int i = 0; i < stars.length; i++) {
            checkBound(i);

            if (stars[i].centerX - stars[i].r > this.getWidth() + 10 + (2 * stars[i].r)
                    | stars[i].centerY - stars[i].r > this.getHeight() + 10 + (2 * stars[i].r)
                    | stars[i].centerX - stars[i].r < -10 - (2 * stars[i].r)
                    | stars[i].centerY - stars[i].r < -10 - (2 * stars[i].r)) {
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
                if (isNewStarExist) {
                    stars[i] = new Star(bufferStar);
                    bufferStar.remove();

                    stars[i].show(mouseEventX, mouseEventY);
                    stars[i].onScreen = true;
                    isNewStarExist = false;
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
        if (stars[i].vectorX > 1000) {
            stars[i].vectorX = 1000;
        } else if (stars[i].vectorX < -1000) {
            stars[i].vectorX = -1000;
        }
        if (stars[i].vectorY > 1000) {
            stars[i].vectorY = 1000;
        } else if (stars[i].vectorY < -1000) {
            stars[i].vectorY = -1000;
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
        while (!isExit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameThread();
        }
    }
}
