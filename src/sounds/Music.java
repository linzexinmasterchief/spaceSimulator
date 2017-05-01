package sounds;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;

public class Music implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                // 1.wav 文件放在java project 下面
                FileInputStream fileau = new FileInputStream("src\\sounds\\bgm.wav");
                AudioStream as = new AudioStream(fileau);
                AudioPlayer.player.start(as);
                Thread.sleep(21925);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}