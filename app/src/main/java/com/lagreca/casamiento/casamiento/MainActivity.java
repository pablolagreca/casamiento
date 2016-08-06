package com.lagreca.casamiento.casamiento;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private String mensaje = "Se aproxima una fecha muy especial para nosotros y pensamos que la mejor " +
            "manera de empezar a vivirla es desafiandote a ver cuanto conoces de nuestra historia. #" +
            "Empeza a divertirte contestando algunas preguntas y descubri que tan importante sos para nosotros.\n Haz click aqui y comenza!.";

    private AtomicBoolean messageShown = new AtomicBoolean(false);
    private SoundProducer gadgetSoundProducer;

    @Override
    protected void onStop() {
        super.onStop();
        gadgetSoundProducer.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gadgetSoundProducer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gadgetSoundProducer.stop();
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        gadgetSoundProducer.play();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        gadgetSoundProducer = new SoundProducer(this, R.raw.gadget);
        gadgetSoundProducer.loop().play();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout shellLayout = (RelativeLayout) findViewById(R.id.shellLayout);

                new ShellMessageHelper().createShellMessageTextView(MainActivity.this,
                        shellLayout,
                        mensaje,
                        new Runnable() {
                            @Override
                            public void run() {
                                messageShown.set(true);
                            }
                        }, null);
            }
        }, 5000);
    }

    public void seeQuestions(View view) {
        if (messageShown.get())
        {
            gadgetSoundProducer.stop();
            Intent intent = new Intent(this, SeeQuestionActivity.class);
            startActivity(intent);
        }
    }
}
