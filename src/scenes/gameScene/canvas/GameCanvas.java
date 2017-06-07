package scenes.gameScene.canvas;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import models.Camera;
import models.Star;
import models.Universe;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 * also, this is the only class that have the ability
 * of displaying graphics, which means, all of the
 * displaying command and modules should be called
 * in this class
 */
public class GameCanvas extends Canvas implements Runnable {

    //define a universe to display
    private Universe universe;

    //define a camera used for display
    private Camera camera;

    //define a graphic context to operate the graphics on the screen
    private GraphicsContext gc;
    //for canvas to stop update (exit)
    private boolean isExit;

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
    private int cameraMoveSpeed = 10;

    private double scaleX = 1;
    private double scaleY = 1;


    //constructor
    public GameCanvas(double width, double height) {
        //width and height is pretty necessary for things like
        //"this.getWidth()" to work properly
        super(width, height);

        //initialize the universe to display
        universe = new Universe(2000, 2000);
        //initialize the physics thread of the universe
        Thread universeThread = new Thread(universe);
        //start the physics thread of the universe
        universeThread.start();

        //initialize the camera
        camera = new Camera(
                this.getWidth(),
                this.getHeight(),
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );

        //initialize the scale between height and width
        HeightWidthScale = this.getHeight() / this.getWidth();

        //initialize the variable used to store mouse operations
        mouseEventX = 0;
        mouseEventY = 0;
        mouse_coordinate = new double[2];

        //initialize exit condition
        isExit = false;

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
                universe.setNewStarExist(true);

                //get the mouse release coordinate
                mouseEventX = me.getX();
                mouseEventY = me.getY();

                //give the buffer star speed based on the distance mouse dragged
                universe.getBufferStar().vectorX = (mouseEventX - mouse_coordinate[0]) / dragSpeedConstant;
                universe.getBufferStar().vectorY = (mouseEventY - mouse_coordinate[1]) / dragSpeedConstant;

                //check if the new star lock is opened
                //to avoid unnecessary star list iterations
                if (universe.getNewStarExist()) {
                    //check if there is empty star slot for a new star
                    for (int i = 0; i < universe.getStars().length; i++) {
                        if (universe.getNewStarExist()) {
                            //not on screen means it is safe to clear
                            if (!universe.getStars()[i].onScreen) {

                                //prepare the empty slot for new star
                                universe.getStars()[i].remove();
                                universe.getStars()[i].initialize();

                                //give the properties of buffer star to the empty star slot
                                universe.getStars()[i] = new Star(universe.getBufferStar());
                                //remove the buffer star (clear the values to default)
                                universe.getBufferStar().remove();

                                //show the star according to the size of window(camera)
                                //and the enlarge scales
                                universe.getStars()[i].show(
                                        //convert the coordinate on screen to coordinate in the universe
                                        //it's hard to explain the math, but it will be easy to understand
                                        //once you draw it out on the paper, be careful changing it anyway
                                        (camera.getCenterX() - universe.getWidth() / 2)
                                                + (universe.getWidth() - camera.getWidth()) / 2
                                                + mouseEventX * scaleX,
                                        (camera.getCenterY() - universe.getHeight() / 2)
                                                + (universe.getHeight() - camera.getHeight()) / 2
                                                + mouseEventY * scaleY
                                );

                                //change the slot property from empty to full
                                universe.getStars()[i].onScreen = true;

                                //close the new star lock
                                universe.setNewStarExist(false);

                                //refresh the screen
                                drawShapes();
                            }
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
                camera.setWidth(camera.getWidth() + sizeChangeSpeed);
                camera.setHeight(camera.getHeight() + sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                camera.setCenterX(camera.getCenterX() - (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                camera.setCenterY(camera.getCenterY() - (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            } else {
                //on mouse wheel rolling back (enlarge)
                camera.setWidth(camera.getWidth() - sizeChangeSpeed);
                camera.setHeight(camera.getHeight() - sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                camera.setCenterX(camera.getCenterX() + (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                camera.setCenterY(camera.getCenterY() + (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            }

            //calculate the scale between camera and original camera
            scaleX = camera.getWidth() / camera.getOriginalWidth();
            scaleY = camera.getHeight() / camera.getOriginalHeight();
        });

        //paint the screen the first time to have a graphic instead of blank
        drawShapes();
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
        for (Star star : universe.getStars()) {
            star.onScreen = true;
            star.onScreen = !(star.centerX < camera.getCenterX() - camera.getWidth() / 2 - star.r
                    || star.centerX > camera.getCenterX() + camera.getWidth() / 2 + star.r
                    || star.centerY < camera.getCenterY() - camera.getHeight() / 2 - star.r
                    || star.centerY > camera.getCenterY() + camera.getHeight() / 2 + star.r);

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
                                star.centerX - (camera.getCenterX() - universe.getWidth() / 2)
                                        - (universe.getWidth() - camera.getWidth()) / 2
                        )
                                / scaleX
                                - starWidth / 2,
                        (
                                star.centerY - (camera.getCenterY() - universe.getHeight() / 2)
                                        - (universe.getHeight() - camera.getHeight()) / 2
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
        for (Star star : universe.getStars()) {
            star.remove();
        }
    }

    //the graphic thread operation function
    @Override
    public void run() {
        while (!isExit) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //send the rendering job to a background thread
            Platform.runLater(this::drawShapes);
        }
    }
}
