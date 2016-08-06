package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class BadAnsweredTriviaActivity extends AppCompatActivity {

    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bad_answered_trivia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new SoundProducer(this, R.raw.fallochiste).onComplete(new Runnable() {
            @Override
            public void run() {
                new SoundProducer(BadAnsweredTriviaActivity.this, R.raw.sosburro).play();
            }
        }).play();


// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                if (alert.isShowing()) {
//                    alert.dismiss();
                    exitDialogWrongAnswers();
                    Intent intent = new Intent(BadAnsweredTriviaActivity.this, SeeQuestionActivity.class);
                    startActivity(intent);
//                }
            }
        };

//        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                handler.removeCallbacks(runnable);
//            }
//        });

        handler.postDelayed(runnable, 5000);
    }

    private void exitDialogWrongAnswers() {
//        videoview = (VideoView) findViewById(R.id.videoView1);
//
//        videoview.setVideoURI(Uri.parse("android.resource://com.lagreca.casamiento.casamiento/" + R.raw.wronganswervideo));
//
//
//        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                Intent intent = new Intent(BadAnsweredTriviaActivity.this, SeeQuestionActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        videoview.start();

    }
}
