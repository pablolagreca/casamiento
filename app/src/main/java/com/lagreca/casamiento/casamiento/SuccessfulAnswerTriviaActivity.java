package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.VideoView;

public class SuccessfulAnswerTriviaActivity extends AppCompatActivity {

    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_answer_trivia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        showWeddingVideo();
    }

    private void showWeddingVideo() {

        videoview = (VideoView) findViewById(R.id.videoView2);

        videoview.setVideoURI(Uri.parse("android.resource://com.lagreca.casamiento.casamiento/" + R.raw.weddingvideo));

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                int videoWidth = mediaPlayer.getVideoWidth();
                int videoHeight = mediaPlayer.getVideoHeight();
                float videoProportion = (float) videoWidth / (float) videoHeight;
                int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                float screenProportion = (float) screenWidth / (float) screenHeight;

                android.widget.RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) videoview.getLayoutParams();

                if (videoProportion > screenProportion) {
                    lp.width = screenWidth;
                    lp.height = (int) ((float) screenHeight / videoProportion);
                } else {
                    lp.width = (int) ((float)screenWidth / videoProportion);
                    lp.height = screenHeight;
                }
                videoview.setLayoutParams(lp);
            }
        });

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(SuccessfulAnswerTriviaActivity.this).setTitle("").setMessage("Te esperamos!!!");
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        videoview.start();
    }
}
