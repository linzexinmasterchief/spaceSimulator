package Application.logicUnit.worldComponents.operation;

import Application.logicUnit.World;
import Application.logicUnit.worldComponents.worldSettings.Speed;
import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.CanvasStatus;
import Application.status.Mouse;
import javafx.application.Platform;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import models.systemComponentModels.ThreadModel;

/**
 * Created by lzx on 2017/7/13.
 * thread which controls every operation from mouse and keyboard
 */
public class OperationThread extends ThreadModel {

    private GameScene gameScene;
    private CanvasStatus canvasStatus;

    //create reference to the mouse
    private Mouse mouse;

    public OperationThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = world.getLauncher().getGameStage().getGameScene();

        canvasStatus = world.getLauncher().getCanvasStatus();
        mouse = world.getLauncher().getMouse();
    }

    //determine if a new star should be created
    //left click to new a star
    private void addNewStar(){
        //open the new star lock
        systemStatus.setNewStarExist(true);

        //give the buffer star speed based on the distance mouse dragged
        world.getBufferStar().velocityX = (systemStatus.getDragLine()[2] - systemStatus.getDragLine()[0])
                / Speed.getDragSpeedConstant()
                //times camera enlarge scale
                * world.getCamera().getWidth() / world.getCamera().getOriginalWidth();
        world.getBufferStar().velocityY = (systemStatus.getDragLine()[3] - systemStatus.getDragLine()[1])
                / Speed.getDragSpeedConstant()
                //times camera enlarge scale
                * world.getCamera().getHeight() / world.getCamera().getOriginalHeight();

        //check if the new star lock is opened to avoid unnecessary star list iterations
        //check if there is empty star slot for a new star
        if (systemStatus.isNewStarExist()) {
            for (int i = 0; i < world.getUniverse().getStars().length; i++) {
                //not on screen means it is safe to clear
                if (systemStatus.isNewStarExist() & !world.getUniverse().getStars()[i].inUniverse) {

                    //prepare the empty slot for new star
                    world.getUniverse().getStars()[i].remove();
                    world.getUniverse().getStars()[i].initialize();

                    //give buffer star properties
                    world.getBufferStar().mass = gameScene.getMass();
                    world.getBufferStar().r = gameScene.getRadius();

                    //give the properties of buffer star to the empty star slot
                    world.getUniverse().getStars()[i] = new Star(world.getBufferStar());
                    //remove the buffer star (clear the values to default)
                    world.getBufferStar().remove();

                    //add the star according to the size of window(camera)
                    //and the enlarge scales
                    world.getUniverse().getStars()[i].add(
                            //convert the coordinate on screen to coordinate in the universe
                            //it's hard to explain the math, but it will be easy to understand
                            //once you draw it out on the paper, be careful changing it anyway
                            (world.getCamera().getCenterX() - world.getUniverse().getWidth() / 2)
                                    + (world.getUniverse().getWidth() - world.getCamera().getWidth()) / 2
                                    + systemStatus.getDragLine()[0] * world.getGraphicsThreadModule().getScaleX(),
                            (world.getCamera().getCenterY() - world.getUniverse().getHeight() / 2)
                                    + (world.getUniverse().getHeight() - world.getCamera().getHeight()) / 2
                                    + systemStatus.getDragLine()[1] * world.getGraphicsThreadModule().getScaleY()
                    );

                    //change the slot property from empty to full
                    world.getUniverse().getStars()[i].inUniverse = true;

                    //close the new star lock
                    systemStatus.setNewStarExist(false);

                    //refresh the screen
                    world.getGraphicsThreadModule().drawShapes();
                }
            }
        }
        mouse.setMouseReleased(false);
    }


    @Override
    public void run() {
        while (!isExit()) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mouse.isMousePressed()) {
                mouse.setMouseReleased(false);
                switch (mouse.getActivatedMouseButton()) {
                    case PRIMARY:
                        systemStatus.getDragLine()[2] = mouse.getMouse_coordinate()[0];
                        systemStatus.getDragLine()[3] = mouse.getMouse_coordinate()[1];
                        break;
                }
            }

            if (mouse.isMouseReleased()) {
                mouse.setMousePressed(false);
                switch (mouse.getActivatedMouseButton()) {
                    case PRIMARY:
                        addNewStar();
                        systemStatus.setDragLine(new double[]{
                                0,
                                0,
                                0,
                                0
                        });
                        break;
                    case SECONDARY:
                        //execute clear command
                        world.getPhysicsThreadModule().clear();
                        break;
                    case MIDDLE:
                        //change pause value if middle button pressed
                        world.getPhysicsThreadModule().setPause(!world.getPhysicsThreadModule().isPause());
                        break;
                }
            }

            if (mouse.isMouseScrolled()) {

                mouse.setMouseReleased(false);
                mouse.setMousePressed(false);

                double cameraWidthChangingSpeed = world.getCamera().getWidth() / world.getCamera().getOriginalWidth() * 2;
                double cameraHeightChangingSpeed = world.getCamera().getHeight() / world.getCamera().getOriginalHeight() * 2;
                //on mouse wheel rolling back (minimize)
                if (mouse.getMouseScrollValue() < 0) {
                    if (world.getCamera().getHeight() < world.getUniverse().getHeight()
                            & world.getCamera().getWidth() < world.getUniverse().getWidth()) {

                        //change the size of the camera (+)
                        world.getCamera().setWidth(world.getCamera().getWidth() + Speed.getSizeChangeSpeed() * cameraWidthChangingSpeed);
                        world.getCamera().setHeight(world.getCamera().getHeight()
                                + Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale() * cameraHeightChangingSpeed);

                        //move the camera to the mouse coordinate to create an effect
                        world.getCamera().setCenterX(world.getCamera().getCenterX()
                                - (mouse.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                                / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed() * cameraWidthChangingSpeed
                        );
                        world.getCamera().setCenterY(world.getCamera().getCenterY()
                                - (mouse.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                                / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed() * cameraHeightChangingSpeed
                        );
                    }
                } else if (mouse.getMouseScrollValue() > 0) {
                    //on mouse wheel rolling back (enlarge)
                    world.getCamera().setWidth(world.getCamera().getWidth() - Speed.getSizeChangeSpeed() * cameraWidthChangingSpeed);
                    world.getCamera().setHeight(world.getCamera().getHeight()
                            - Speed.getSizeChangeSpeed() * systemStatus.getHeightWidthScale() * cameraHeightChangingSpeed
                    );

                    //move the camera to the mouse coordinate to create an effect
                    world.getCamera().setCenterX(world.getCamera().getCenterX()
                            + (mouse.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                            / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed() * cameraWidthChangingSpeed
                    );

                    world.getCamera().setCenterY(world.getCamera().getCenterY()
                            + (mouse.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                            / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed() * cameraHeightChangingSpeed
                    );
                }

                //calculate the scale between camera and original camera
                world.getGraphicsThreadModule().setScaleX(world.getCamera().getWidth() / world.getCamera().getOriginalWidth());
                world.getGraphicsThreadModule().setScaleY(world.getCamera().getHeight() / world.getCamera().getOriginalHeight());

                //refresh the screen
                world.getGraphicsThreadModule().drawShapes();
            }

            mouse.setMouseReleased(false);
            mouse.setMouseScrolled(false);

            gameScene.getCreateStarMenu().getSettingBtn().setVisible(systemStatus.isCreateStarMenuOut());
            gameScene.getCreateStarMenu().getMassSlider().setVisible(systemStatus.isCreateStarMenuOut());
            gameScene.getCreateStarMenu().getRaiusSlider().setVisible(systemStatus.isCreateStarMenuOut());

            if (systemStatus.isSettingStageOut()) {
                Platform.runLater(() -> world.getLauncher().getSettingStage().show());
            }else {
                Platform.runLater(() -> world.getLauncher().getSettingStage().hide());
            }

            Platform.runLater(() -> gameScene.getStatusBar().update());
        }

    }
}
