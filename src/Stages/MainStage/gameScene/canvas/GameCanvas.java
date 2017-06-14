package Stages.MainStage.gameScene.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Star;
import Stages.MainStage.gameScene.GameScene;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 * also, this is the only class that have the ability
 * of displaying graphics, which means, all of the
 * displaying command and modules should be called
 * in this class
 */
public class GameCanvas extends Canvas implements Runnable {

    //create reference to rootScene
    private GameScene gameScene;

    //define a graphic context to operate the graphics on the screen
    private GraphicsContext gc;
    //for canvas to stop update (exit)
    private boolean isExit;

    //new star lock
    private boolean isNewStarExist;

    //variables used to store mouse operations
    private double mouseEventX;
    private double mouseEventY;
    //an array used to store the coordinate of the mouse
    private double[] mouse_coordinate;

    //a variable used to store the scale between height and width
    private double HeightWidthScale;
    //determine how fast the star moves with the same drag distance
    private int dragSpeedConstant = 100;
    //determine how fast the camera enlarge/minify
    private int sizeChangeSpeed = 20;
    //determine how fast the camera moves to the mouse coordinate when scrolling on a point
    private int cameraMoveSpeed = 20;

    private double scaleX = 1;
    private double scaleY = 1;


    //constructor
    public GameCanvas(double width, double height, GameScene rootScene) {
        //width and height is pretty necessary for things like
        //"this.getWidth()" to work properly
        super(width, height);

        gameScene = rootScene;

        //initialize the scale between height and width
        HeightWidthScale = this.getHeight() / this.getWidth();

        //initialize the variable used to store mouse operations
        mouseEventX = 0;
        mouseEventY = 0;
        mouse_coordinate = new double[2];

        //initialize exit condition
        isExit = false;

        //close new star lock
        isNewStarExist = false;

        //let the graphical context be a pointer to the real screen context
        //they are pointing to the same object so the operations on graphical
        //context variable will directly have effects on the real screen
        gc = getGraphicsContext2D();

        //listen the operations of mouse press
        setOnMousePressed(me -> {
            //get the mouse press coordinate
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();
        });

        //set operations on mouse release
        //new and clear
        setOnMouseReleased(me -> {

            //determine if a new star should be created
            //left click to new a star
            if (me.getButton() == MouseButton.PRIMARY) {
                //open the new star lock
                isNewStarExist = true;

                //get the mouse release coordinate
                mouseEventX = me.getX();
                mouseEventY = me.getY();

                //give the buffer star speed based on the distance mouse dragged
                gameScene.getRootEngine().getBufferStar().vectorX = (mouseEventX - mouse_coordinate[0]) / dragSpeedConstant;
                gameScene.getRootEngine().getBufferStar().vectorY = (mouseEventY - mouse_coordinate[1]) / dragSpeedConstant;

                //check if the new star lock is opened
                //to avoid unnecessary star list iterations
                //check if there is empty star slot for a new star
                if (isNewStarExist) {
                    for (int i = 0; i < gameScene.getRootEngine().getStars().length; i++) {
                        //not on screen means it is safe to clear
                        if (isNewStarExist & !gameScene.getRootEngine().getStars()[i].inUniverse) {

                            //prepare the empty slot for new star
                            gameScene.getRootEngine().getStars()[i].remove();
                            gameScene.getRootEngine().getStars()[i].initialize();

                            //give buffer star properties
                            gameScene.getRootEngine().getBufferStar().mass = gameScene.getMass();

                            //give the properties of buffer star to the empty star slot
                            gameScene.getRootEngine().getStars()[i] = new Star(gameScene.getRootEngine().getBufferStar());
                            //remove the buffer star (clear the values to default)
                            gameScene.getRootEngine().getBufferStar().remove();

                            //add the star according to the size of window(camera)
                            //and the enlarge scales
                            gameScene.getRootEngine().getStars()[i].add(
                                    //convert the coordinate on screen to coordinate in the universe
                                    //it's hard to explain the math, but it will be easy to understand
                                    //once you draw it out on the paper, be careful changing it anyway
                                    (gameScene.getRootEngine().getCamera().getCenterX() - gameScene.getRootEngine().getUniverse().getWidth() / 2)
                                            + (gameScene.getRootEngine().getUniverse().getWidth() - gameScene.getRootEngine().getCamera().getWidth()) / 2
                                            + mouseEventX * scaleX,
                                    (gameScene.getRootEngine().getCamera().getCenterY() - gameScene.getRootEngine().getUniverse().getHeight() / 2)
                                            + (gameScene.getRootEngine().getUniverse().getHeight() - gameScene.getRootEngine().getCamera().getHeight()) / 2
                                            + mouseEventY * scaleY
                            );

                            //change the slot property from empty to full
                            gameScene.getRootEngine().getStars()[i].inUniverse = true;

                            //close the new star lock
                            isNewStarExist = false;

                            //refresh the screen
                            drawShapes();
                        }
                    }
                }
            }

            //right click the mouse to clean the screen
            //remove all the stars from the universe
            if (me.getButton() == MouseButton.SECONDARY) {
                clear();
            }
        });

        //set operations on mouse scrolled
        //enlarge and minimize
        setOnScroll(se -> {

            //get the mouse coordinate
            mouseEventX = se.getX();
            mouseEventY = se.getY();

            //on mouse wheel rolling back (minimize)
            if (se.getDeltaY() < 0) {

                //change the size of the camera (+)
                gameScene.getRootEngine().getCamera().setWidth(gameScene.getRootEngine().getCamera().getWidth() + sizeChangeSpeed);
                gameScene.getRootEngine().getCamera().setHeight(gameScene.getRootEngine().getCamera().getHeight() + sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                gameScene.getRootEngine().getCamera().setCenterX(gameScene.getRootEngine().getCamera().getCenterX() - (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                gameScene.getRootEngine().getCamera().setCenterY(gameScene.getRootEngine().getCamera().getCenterY() - (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            } else {
                //on mouse wheel rolling back (enlarge)
                gameScene.getRootEngine().getCamera().setWidth(gameScene.getRootEngine().getCamera().getWidth() - sizeChangeSpeed);
                gameScene.getRootEngine().getCamera().setHeight(gameScene.getRootEngine().getCamera().getHeight() - sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                gameScene.getRootEngine().getCamera().setCenterX(gameScene.getRootEngine().getCamera().getCenterX() + (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                gameScene.getRootEngine().getCamera().setCenterY(gameScene.getRootEngine().getCamera().getCenterY() + (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            }

            //calculate the scale between camera and original camera
            scaleX = gameScene.getRootEngine().getCamera().getWidth() / gameScene.getRootEngine().getCamera().getOriginalWidth();
            scaleY = gameScene.getRootEngine().getCamera().getHeight() / gameScene.getRootEngine().getCamera().getOriginalHeight();
        });

        //initialize the graphic thread
        Thread graphicThread = new Thread(this);
        //launch the graphic thread
        graphicThread.start();

    }

    //screen painting function
    private void drawShapes() {

        //fill the back ground with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());

        //change the color of pen to blue to paint the stars
        gc.setFill(Color.RED);

        //iterate the star list to draw all the exist stars in the universe
        for (Star star : gameScene.getRootEngine().getStars()) {
            star.onScreen = true;
            star.onScreen = !(star.centerX < gameScene.getRootEngine().getCamera().getCenterX() - gameScene.getRootEngine().getCamera().getWidth() / 2 - star.r
                    || star.centerX > gameScene.getRootEngine().getCamera().getCenterX() + gameScene.getRootEngine().getCamera().getWidth() / 2 + star.r
                    || star.centerY < gameScene.getRootEngine().getCamera().getCenterY() - gameScene.getRootEngine().getCamera().getHeight() / 2 - star.r
                    || star.centerY > gameScene.getRootEngine().getCamera().getCenterY() + gameScene.getRootEngine().getCamera().getHeight() / 2 + star.r);

            //calculate the actual display size according to the scale
            double starWidth = star.r * 2 / scaleX;
            double starHeight = star.r * 2 / scaleY;

            //make sure there is always a small point displaying for every star, even the scale is crazily large
            if (starWidth < 1) {
                starWidth = 1;
            }
            if (starHeight < 1) {
                starHeight = 1;
            }

            //only display the star if it is on screen
            //in the camera range
            if (star.onScreen) {
                getGraphicsContext2D().fillOval(

                        //a little math here, should be reliable after 6 changes
                        //also, this is related to the math above when the user
                        //new a star, they are reverse functions to each other

                        //this is convert the universe coordinates to display coordinates
                        (
                                star.centerX - (gameScene.getRootEngine().getCamera().getCenterX() - gameScene.getRootEngine().getUniverse().getWidth() / 2)
                                        - (gameScene.getRootEngine().getUniverse().getWidth() - gameScene.getRootEngine().getCamera().getWidth()) / 2
                        )
                                / scaleX
                                - starWidth / 2,
                        (
                                star.centerY - (gameScene.getRootEngine().getCamera().getCenterY() - gameScene.getRootEngine().getUniverse().getHeight() / 2)
                                        - (gameScene.getRootEngine().getUniverse().getHeight() - gameScene.getRootEngine().getCamera().getHeight()) / 2
                        )
                                / scaleY
                                - starHeight / 2,
                        starWidth,
                        starHeight
                );
            }
        }
    }

    //function designed for screen cleaning
    //calling it will remove all the stars in the universe
    private void clear() {
        for (Star star : gameScene.getRootEngine().getStars()) {
            star.remove();
        }
    }

    //getters and setters
    public void setExit(boolean exit) {
        isExit = exit;
    }

    //the graphic thread operation function
    @Override
    public void run() {
        while (!isExit) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //send the rendering job to a background thread
            Platform.runLater(this::drawShapes);
        }
    }
}
