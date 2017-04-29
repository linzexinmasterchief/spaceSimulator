import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class musicTest {
    public static void main(String[] args) throws Exception {
        // 用输入流打开一音频文件
        InputStream in = new FileInputStream("D:\\lizhou\\Music\\Acreix - Visions.mp3");//FIlename 是你加载的声音文件如(“game.wav”)
        // 从输入流中创建一个AudioStream对象
        AudioStream as = new AudioStream(in);
        //如果要实现循环播放，则用下面的三句取代上面的“AudioPlayer.player.start(as);”这句
        AudioData data = as.getData();
        ContinuousAudioDataStream gg = new ContinuousAudioDataStream(data);
        AudioPlayer.player.start(gg);// Play audio.
        //AudioPlayer.player.stop(as);//关闭音乐播放
    }
}