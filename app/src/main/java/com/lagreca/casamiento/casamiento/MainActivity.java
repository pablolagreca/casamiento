package com.lagreca.casamiento.casamiento;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private String mensaje = "Se aproxima una fecha muy especial para nosotros y pensamos que la mejor " +
            "manera de empezar a vivirla es desafiándote a ver cuanto conoces de nuestra historia. #" +
            "Empeza a divertirte contestando algunas preguntas y descubrí que tan importante sos para nosotros.\n Haz clic aquí y comenza!.";

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
//        this.finish();
//        System.exit(0);
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
        gadgetSoundProducer = new SoundProducer(this, R.raw.gadget);
        gadgetSoundProducer.loop().play();
        if (AppStateHelper.isVideoSeen(this))
        {
            messageShown.set(true);
            Button playAgainButton = (Button) findViewById(R.id.playagain);
            Button seeVideo = (Button) findViewById(R.id.seeVideo);
            playAgainButton.setVisibility(VISIBLE);
            seeVideo.setVisibility(VISIBLE);
        }
        else
        {
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
                            }, null, false);
                }
            }, 5000);
        }
    }

    public void seeVideo(View view)
    {
        gadgetSoundProducer.stop();
        Intent intent = new Intent(this, SuccessfulAnswerTriviaActivity.class);
        intent.putExtra(SuccessfulAnswerTriviaActivity.SHOW_MAIN_VIDEO_PARAMETER, true);
        startActivity(intent);
    }

    public void seeQuestions(View view) {
        //TODO remove comment
//        if (messageShown.get())
//        {
        gadgetSoundProducer.stop();
        Intent intent = new Intent(this, SeeQuestionActivity.class);
        startActivity(intent);
//        }
    }

}
