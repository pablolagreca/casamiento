package com.lagreca.casamiento.casamiento;

import android.content.Intent;
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
            "manera de empezar a vivirla es desafiandote a ver cuanto conoces de nuestra historia. " +
            "Empeza a divertite contestantdo algunas pregunta. Descubri que tan importante sos para nosotros.";

    private AtomicBoolean messageShown = new AtomicBoolean(false);
    private SoundProducer gadgetSoundProducer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gadgetSoundProducer = new SoundProducer(this, R.raw.gadget);
        gadgetSoundProducer.loop().play();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout shellLayout = (RelativeLayout) findViewById(R.id.shellLayout);
//                shellLayout.setBackgroundColor(Color.BLACK);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

                final TextView textView = new TextView(MainActivity.this);
                textView.setBackgroundResource(R.drawable.rounded_border_text_view);
//                textView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
                textView.setPadding(100, 100, 100, 100);
                textView.setLayoutParams(params);
                shellLayout.addView(textView);

                final Queue<Character> queue = new LinkedList();
                for (int i = 0; i < mensaje.length(); i ++)
                {
                    queue.offer(mensaje.charAt(i));
                }

                final Random random = new Random(System.currentTimeMillis());

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Character character = queue.poll();
                        if (character != null)
                        {
                            SpannableString spanString = new SpannableString(String.valueOf(character));
                            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
                            textView.append(spanString);
                            int timeToWait = random.nextInt(50) + 50;
                            if (".".equals(String.valueOf(textView.getText().charAt(textView.getText().length() - 1))))
                            {
                                timeToWait = 600;
                            }
                            new Handler().postDelayed(this, timeToWait);
                        }
                        else
                        {
                            messageShown.set(true);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (".".equals(String.valueOf(textView.getText().charAt(textView.length() - 1))))
//                                    {
//                                        CharSequence text = textView.getText();
//                                        text = text.subSequence(0, text.length() - 2);
//                                        SpannableString spanString = new SpannableString(String.valueOf(text));
//                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
//                                        textView.append(spanString);
//
//                                    }
//                                    else
//                                    {
//                                        textView.append(".");
//                                    }
//                                }
//                            }, 200);
                        }
                    }
                };
                new Handler().postDelayed(runnable, 100);
            }
        }, 5000);
    }

    public void seeQuestions(View view) {
//        if (messageShown.get())
//        {
        gadgetSoundProducer.stop();
            Intent intent = new Intent(this, SeeQuestionActivity.class);
            startActivity(intent);
//        }
    }
}
