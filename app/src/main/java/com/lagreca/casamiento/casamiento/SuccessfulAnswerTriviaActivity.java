package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
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
                    showWeddingVideo();
                }
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    private void showWeddingVideo() {

        videoview = (VideoView) findViewById(R.id.videoView2);

        videoview.setVideoURI(Uri.parse("android.resource://com.lagreca.casamiento.casamiento/" + R.raw.weddingvideo));


        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(SuccessfulAnswerTriviaActivity.this).setTitle("").setMessage("Te esperamos!!!");
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        //set in full screen
        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoview.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoview.setLayoutParams(params);

        videoview.start();

//        videoview = (VideoView) findViewById(R.id.videoView1);
//
//        videoview.setVideoPath(
//                "https://docs.google.com/uc?authuser=1&id=0B5QOkF_ymQ_0a2ZtcW5BUmtuV0E&export=download");
//
////        videoview.setVideoPath("raw/wronganswervideo.3gp");
//
//        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
////                Intent intent = new Intent(BadAnsweredTriviaActivity.this, SeeQuestionActivity.class);
////                startActivity(intent);
//            }
//        });
//
//        videoview.start();

    }
}
