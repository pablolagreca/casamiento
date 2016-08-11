package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.lagreca.casamiento.casamiento.AppStateHelper.isVideoSeen;
import static com.lagreca.casamiento.casamiento.AppStateHelper.setVideoSeen;

public class SuccessfulAnswerTriviaActivity extends AppCompatActivity {

    public static final String SHOW_MAIN_VIDEO_PARAMETER = "SHOW_MAIN_VIDEO";
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_successful_answer_trivia);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        videoview = (VideoView) findViewById(R.id.videoView2);
        if (getIntent().getBooleanExtra(SHOW_MAIN_VIDEO_PARAMETER, false))
        {
            showMainVideo();
        }
        else
        {
            showWeddingVideo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        this.finish();
//        System.exit(0);
    }

    private void showWeddingVideo() {

        videoview.setVideoURI(Uri.parse("android.resource://com.lagreca.casamiento.casamiento/" + R.raw.aplausos));

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                float videoWidth = mediaPlayer.getVideoWidth();
                float videoHeight = mediaPlayer.getVideoHeight();
                float videoProportion = (float) videoWidth / (float) videoHeight;
                float screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                float screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                float screenProportion = (float) screenWidth / (float) screenHeight;

                android.widget.RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) videoview.getLayoutParams();

                if (videoProportion > screenProportion) {
                    lp.width = (int) screenWidth;
                    lp.height = (int) ((float) screenHeight / videoProportion);
                } else {
                    lp.width = (int) ((float) screenWidth / videoProportion);
                    lp.height = (int) screenHeight;
                }

//              uncomment for regular
                float hightIncrease = videoHeight > screenHeight ? videoHeight / screenHeight : screenHeight / videoHeight;
                float widthIncrease = videoWidth > screenWidth ? videoWidth / screenWidth : screenWidth / videoWidth;
                float increase = Math.min(hightIncrease, widthIncrease);
                lp.width = (int) (screenWidth * increase);
                lp.height = (int) (videoWidth * increase);

                videoview.setLayoutParams(lp);
            }
        });

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                showMainVideo();
            }
        });

        videoview.start();
    }

    private void showMainVideo() {
        videoview.setVideoURI(Uri.parse("android.resource://com.lagreca.casamiento.casamiento/" + R.raw.facebook2));
        MediaController controller = new MediaController(SuccessfulAnswerTriviaActivity.this);
        if (isVideoSeen(SuccessfulAnswerTriviaActivity.this))
        {
            controller.setVisibility(VISIBLE);
        }
        else
        {
            controller.setVisibility(GONE);
        }
        videoview.setMediaController(controller);
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                setVideoSeen(SuccessfulAnswerTriviaActivity.this, true);
                startActivity(new Intent(SuccessfulAnswerTriviaActivity.this, MainActivity.class));
            }
        });
        videoview.start();
    }
}
