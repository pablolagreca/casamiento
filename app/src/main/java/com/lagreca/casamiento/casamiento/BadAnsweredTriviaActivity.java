package com.lagreca.casamiento.casamiento;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.io.IOException;
import java.util.List;

public class BadAnsweredTriviaActivity extends AppCompatActivity {

    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_answered_trivia);
        videoview = (VideoView) findViewById(R.id.videoView1);

        videoview.setVideoPath(
                "https://docs.google.com/uc?authuser=1&id=0B5QOkF_ymQ_0a2ZtcW5BUmtuV0E&export=download");
        videoview.start();
    }
}
