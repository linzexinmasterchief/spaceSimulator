package Application.myMath;

/**
 * Created by lzx on 2017/9/1.
 */
public class floatOperate {
    public static float toDecimal(float num, int dec){
        return (float)(Math.round(num * Math.pow(10, dec)) / Math.pow(10, dec));
    }
}
