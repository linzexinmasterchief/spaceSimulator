package models;

import javafx.application.Platform;
import physics.GravityCalculate;

/**
 * Created by lzx on 2017/5/26.
 */
public class Universe implements Runnable {
    private double width;
    private double height;

    private Star bufferStar;
    private GravityCalculate gravityCalculate;
    private Star[] stars;

    private boolean isExit;
    private boolean isPause;
    private boolean isNewStarExist;

    public Universe() {
        width = 0;
        height = 0;

        initialize();
    }

    public Universe(double width, double height) {
        this.width = width;
        this.height = height;

        initialize();
    }

    private void initialize() {
        gravityCalculate = new GravityCalculate(stars);

        bufferStar = new Star();

        isExit = false;
        isPause = false;
        isNewStarExist = false;

        stars = new Star[50];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }

    }

    private void GameThread() {
        if (isPause) {
            return;
        }

        for (int i = 0; i < stars.length; i++) {
//            checkBound(i);
//
            if (stars[i].centerX - stars[i].r > width + 10 + (2 * stars[i].r)
                    | stars[i].centerY - stars[i].r > height + 10 + (2 * stars[i].r)
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
//            } else {
//                stars[i].initialize();
//                if (isNewStarExist) {
//                    stars[i] = new Star(bufferStar);
//                    bufferStar.remove();
//
//                    stars[i].onScreen = true;
//                    isNewStarExist = false;
//                }
            }
        }
    }

    public Star[] getStars() {
        return stars;
    }

    public Star getBufferStar() {
        return bufferStar;
    }

    public boolean getNewStarExist() {
        return isNewStarExist;
    }

    public void setNewStarExist(boolean isNewStarExist) {
        this.isNewStarExist = isNewStarExist;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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
