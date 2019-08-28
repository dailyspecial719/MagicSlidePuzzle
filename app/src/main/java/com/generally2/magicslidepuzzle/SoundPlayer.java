package com.generally2.magicslidepuzzle;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int winSound;


    @SuppressWarnings("deprecation")
    public SoundPlayer(Context context){
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        winSound = soundPool.load(context, R.raw.tada, 1);

    }

    public void playWinSound(){
        soundPool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
