package com.lagreca.casamiento.casamiento;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pablolagreca on 7/21/16.
 */
public class SoundProducer {

    private MediaPlayer mPlayer;

    public SoundProducer(AppCompatActivity activity, int soundId)
    {
        mPlayer = MediaPlayer.create(activity, soundId);
    }

    public SoundProducer play()
    {
        mPlayer.start();
        return this;
    }

    public SoundProducer loop()
    {
        mPlayer.setLooping(true);
        return this;
    }

    public SoundProducer onComplete(final Runnable runnable)
    {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                runnable.run();
            }
        });
        return this;
    }

    public SoundProducer pause()
    {
        if (mPlayer != null)
        {
            mPlayer.pause();
        }
        return this;
    }

    public SoundProducer stop() {
        if (mPlayer != null)
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        return this;
    }

}
