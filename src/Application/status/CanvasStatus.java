package Application.status;

/**
 * Created by lzx on 2017/7/27.
 */
public class CanvasStatus {

    //current canvas property
    private double canvasWidth;
    private double canvasHeight;

    public CanvasStatus(){

        //initialize current canvas properties
        canvasWidth = 0;
        canvasHeight = 0;

    }


    public double getCanvasWidth() {
        return canvasWidth;
    }

    public void setCanvasWidth(double canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasHeight(double canvasHeight) {
        this.canvasHeight = canvasHeight;
    }
}
