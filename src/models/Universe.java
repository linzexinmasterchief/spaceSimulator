package models;

import javafx.application.Platform;
import physics.GravityCalculate;

/**
 * Created by lzx on 2017/5/26.
 * this class is the core of the application,
 * it is in charge of the position system
 * and the gravity calculate (gravity calculate
 * class is only a module)
 */
public class Universe implements Runnable {

    //define the size of the universe
    private double width;
    private double height;

    //use a bufferStar for new input star
    private Star bufferStar;

    //install the gravity module
    private GravityCalculate gravityCalculate;

    //store all the stars in this universe
    private Star[] stars;

    //some properties of the universe
    private boolean isExit;
    private boolean isPause;
    private boolean isNewStarExist;

    //first constructor
    public Universe() {
        //give a default universe size
        width = 0;
        height = 0;

        //call the function to initialize all the stars in the universe
        initialize();
    }

    //second constructor
    public Universe(double width, double height) {
        //define the size of the universe based on the value input
        this.width = width;
        this.height = height;

        //call the function to initialize all the stars in the universe
        initialize();
    }

    //function used to initialize all the stars to their default value
    private void initialize() {
        //initialize the gravity module object
        gravityCalculate = new GravityCalculate(stars);

        //initialize the buffer star
        bufferStar = new Star();

        //initialize universe properties
        isExit = false;
        isPause = false;
        isNewStarExist = false;

        //initialize the star list of the universe
        stars = new Star[50];

        //initialize every star in the star list
        //this is very important and necessary, do not delete it
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }

    }

    //this is the function called on every physics cycle
    //kind of like "fixed update" in unity
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
            }
        }
    }

    //a whole bunch of getter and setters
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

    //the main thread cycle of the universe
    @Override
    public void run() {
        while (!isExit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //call the specific used function
            GameThread();
        }
    }
}
