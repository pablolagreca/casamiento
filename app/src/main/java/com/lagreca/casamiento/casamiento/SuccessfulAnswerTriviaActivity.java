package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class SuccessfulAnswerTriviaActivity extends AppCompatActivity {

    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_answer_trivia);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Felicitaciones!").setMessage("Has contestado correctamente.  ");
        final AlertDialog alert = dialog.create();
        alert.show();

// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                    exitDialogWrongAnswers();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 5000);
    }

    private void exitDialogWrongAnswers() {
        videoview = (VideoView) findViewById(R.id.videoView1);

        videoview.setVideoPath(
                "https://docs.google.com/uc?authuser=1&id=0B5QOkF_ymQ_0a2ZtcW5BUmtuV0E&export=download");

//        videoview.setVideoPath("raw/wronganswervideo.3gp");

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                Intent intent = new Intent(BadAnsweredTriviaActivity.this, SeeQuestionActivity.class);
//                startActivity(intent);
            }
        });

        videoview.start();

    }
}
