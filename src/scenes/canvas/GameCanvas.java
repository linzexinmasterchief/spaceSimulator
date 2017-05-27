package scenes.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 */
public class GameCanvas extends Canvas implements Runnable {

    private Universe universe;

    private GraphicsContext gc;

    private int mouseEventX;
    private int mouseEventY;

    public GameCanvas() {
        universe = new Universe(1000, 1000);
        Thread universeThread = new Thread(universe);
        universeThread.start();

        mouseEventX = 0;
        mouseEventY = 0;

        gc = getGraphicsContext2D();

        double[] mouse_coordinate = new double[2];
        setOnMousePressed(me -> {
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
        });

        setOnMouseReleased(me -> {
            universe.getBufferStar().vectorX = (me.getX() - mouse_coordinate[0]) / 100;
            universe.getBufferStar().vectorY = (me.getY() - mouse_coordinate[1]) / 100;

            if (me.getButton() == MouseButton.PRIMARY) {
                universe.setNewStarExist(true);
                mouseEventX = (int) me.getX();
                mouseEventY = (int) me.getY();

                for (int i = 0; i < universe.getStars().length; i++) {
//                    checkBound(i);
                    if (!universe.getStars()[i].onScreen) {
                        universe.getStars()[i].initialize();
                        if (universe.getNewStarExist()) {
                            universe.getStars()[i] = new Star(universe.getBufferStar());
                            universe.getBufferStar().remove();

                            universe.getStars()[i].show(mouseEventX, mouseEventY);
                            universe.getStars()[i].onScreen = true;
                            universe.setNewStarExist(false);
                            drawShapes();
                        }
                    }
                }
            }
            if (me.getButton() == MouseButton.SECONDARY) {
                clear();
            }
        });

        drawShapes();
        Thread thread = new Thread(this);
        thread.start();

    }

    private void drawShapes() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        gc.setFill(Color.BLUE);

        for (Star star : universe.getStars()) {
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(star.centerX - star.r, star.centerY - star.r, star.r * 2, star.r * 2);
            }
        }
    }

    private void clear() {
        for (Star star : universe.getStars()) {
            star.remove();
        }
    }


//
//    private void checkBound(int i) {
//        if (stars[i].mass > 5000) {
//            stars[i].mass = 5000;
//        } else if (stars[i].mass < -5000) {
//            stars[i].mass = -5000;
//        }
//        if (stars[i].vectorX > 1000) {
//            stars[i].vectorX = 1000;
//        } else if (stars[i].vectorX < -1000) {
//            stars[i].vectorX = -1000;
//        }
//        if (stars[i].vectorY > 1000) {
//            stars[i].vectorY = 1000;
//        } else if (stars[i].vectorY < -1000) {
//            stars[i].vectorY = -1000;
//        }
//        if (stars[i].accelerationX > 500) {
//            stars[i].accelerationX = 500;
//        } else if (stars[i].accelerationX < -500) {
//            stars[i].accelerationX = -500;
//        }
//        if (stars[i].accelerationY > 500) {
//            stars[i].accelerationY = 500;
//        } else if (stars[i].accelerationY < -500) {
//            stars[i].accelerationY = -500;
//        }
//    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(this::drawShapes);
        }
    }
}
