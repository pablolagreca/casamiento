package com.lagreca.casamiento.casamiento;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.util.Collections.shuffle;

public class SeeQuestionActivity extends AppCompatActivity {

    private static final int NUMBER_OF_QUESTIONS_TO_ASK = 5;

    private Question currentQuestion;
    private int nextQuestionIndex = 0;
    private int incorrectAnswers = 0;

    private final List<Integer> photoIdentifiers = new ArrayList<>();
    private SoundProducer bennyHillSoundProducer;
    private SoundProducer criCriCriSoundProducer;
    private Handler handler;
    private MyCountDownTimer myCountDownTimer;
    private boolean showingDialog;


    @Override
    protected void onPause() {
        super.onPause();
        bennyHillSoundProducer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bennyHillSoundProducer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bennyHillSoundProducer.stop();
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        bennyHillSoundProducer.play();
    }

    private void recreatePhotoIdentifiers() {
        for (int i = 0; i <= 50; i++) {
            Integer resId = getResources().getIdentifier("f" + String.valueOf(i), "drawable", "com.lagreca.casamiento.casamiento");
            if (resId != null) {
                photoIdentifiers.add(resId);
            }
        }
        shuffle(photoIdentifiers);
    }

    private Question[] questions = new Question[]
            {
                    new Question("¿En que evento se conocieron los novios?", new Answer("Boliche"), new Answer("Colacion"), new Answer("Cumpleaños", true)),
                    new Question("¿En que año se conocieron los novios?", new Answer("2002"), new Answer("2006"), new Answer("2008", true)),
                    new Question("¿Que disfrutan hacer los novios en su tiempo libre?", new Answer("Ver peliculas", true), new Answer("Ir a bailar"), new Answer("Salir a comer")),
                    new Question("¿Cual es el lugar preferido para vacacionar de los novios?", new Answer("Playa", true), new Answer("Montaña"), new Answer("Nieve")),
                    new Question("¿Quien ficho a quien?", new Answer("Pablo a Noelia"), new Answer("Noelia a Pablo", true), new Answer("Ambos a la vez")),
                    new Question("¿Como llama el novio a la novia cariñosamente?", new Answer("Pupi"), new Answer("Mamu", true), new Answer("Chuli")),
//                    new Question("¿Como se llaman las madres de los novios?", new Answer("Julia y Raquel"), new Answer("Liliana y Beatriz"), new Answer("Raquel y Julia"), new Answer("Liliana y Raquel", true))
                    new Question("¿Cuantos años hace que se conoce la pareja?", new Answer("6"), new Answer("7"), new Answer("8", true)),
                    new Question("¿Donde fue la primera salida de los novios?", new Answer("Al cine", true), new Answer("A cenar"), new Answer("A bailar")),
                    new Question("¿Que tipo de comida prefieren los novios?", new Answer("China"), new Answer("Arabe"), new Answer("Mexicana", true)),
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        shuffleArray(questions);
        setContentView(R.layout.activity_see_question);
        recreatePhotoIdentifiers();
        myCountDownTimer = new MyCountDownTimer(15000l, 1000l, (TextView) findViewById(R.id.timer));
        addNextQuestionAndReturnIfThereAreMoreQuestions();
        bennyHillSoundProducer = new SoundProducer(this, R.raw.bennyhill).loop().play();
        handler = new Handler();
    }

    private boolean addNextQuestionAndReturnIfThereAreMoreQuestions() {
        if (nextQuestionIndex >= questions.length || nextQuestionIndex >= NUMBER_OF_QUESTIONS_TO_ASK || incorrectAnswers > 0) {
            return false;
        }
        TextView questionTextView = (TextView) findViewById(R.id.content);
        currentQuestion = questions[nextQuestionIndex++];
        questionTextView.setText(String.format("[%s/%s]\n%s", nextQuestionIndex, NUMBER_OF_QUESTIONS_TO_ASK, currentQuestion.getText()));
        questionTextView.setGravity(1);

        myCountDownTimer.start();

        GridLayout canvasGridLayout = (GridLayout) findViewById(R.id.buttonLayout);
        canvasGridLayout.setRowCount(currentQuestion.getAnswers().length);
        canvasGridLayout.removeAllViews();

        setBackgroundImage();

        List<Button> buttons = new ArrayList<>();
        int maxTextLenght = 0;
        for (Answer answer : currentQuestion.getAnswers()) {
            Button button = new Button(this);
            buttons.add(button);
//            button.setBackgroundResource(R.color.darkBlue);
//            button.setBackgroundColor(colorStateList.getDefaultColor());
//            button.setBackgroundColor(getResources().getColor(R.color.darkBlue));
            button.setTextColor(Color.WHITE);
            button.setPadding(0, 10, 0, 10);
            button.setText(answer.getText());
            canvasGridLayout.addView(button);
            final Question criCriCriForQuestion = currentQuestion;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (criCriCriForQuestion == currentQuestion) {
                        criCriCriSoundProducer = new SoundProducer(SeeQuestionActivity.this, R.raw.cricricri).play();
                    }
                }
            }, 5000);

            if (answer.isCorrect()) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showingDialog) {
                            return;
                        }
                        myCountDownTimer.cancel();
                        myCountDownTimer.reset();
                        disableCriCriSoundProducer();
                        questionCorrectlyAnswered(view);
                    }
                });
            } else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showingDialog) {
                            return;
                        }
                        myCountDownTimer.cancel();
                        myCountDownTimer.reset();
                        disableCriCriSoundProducer();
                        questionIncorrectlyAnswered(view);
                    }
                });
            }
            maxTextLenght = max(answer.getText().length(), maxTextLenght);
        }
        for (Button button : buttons) {
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) button.getLayoutParams();
            params.width = 600;
        }
        return true;
    }

    private void disableCriCriSoundProducer() {
        currentQuestion = null;
        if (criCriCriSoundProducer != null) {
            criCriCriSoundProducer.stop();
        }
    }

    private void setBackgroundImage() {
        ImageView imageView = (ImageView) findViewById(R.id.questionFoto);
        imageView.setImageResource(photoIdentifiers.get(nextQuestionIndex));
    }

    private void questionIncorrectlyAnswered(View view) {
        incorrectAnswers++;
        showIncorrectAnswerDialog();
    }

    private void processNextQuestionOrTriviaResult() {
        boolean moreQuestions = addNextQuestionAndReturnIfThereAreMoreQuestions();
        if (!moreQuestions) {
            bennyHillSoundProducer.pause();
            if (incorrectAnswers > 0) {
                showIncorrectAnswerDialog();
            } else {
                showingDialog = true;
                Intent intent = new Intent(SeeQuestionActivity.this, SuccessfulAnswerTriviaActivity.class);
                startActivity(intent);
//                RelativeLayout shellLayout = (RelativeLayout) findViewById(R.id.shellLayoutSeeQuestion);
//                shellLayout.bringToFront();
//                new ShellMessageHelper().createShellMessageTextView(SeeQuestionActivity.this,
//                        shellLayout,
//                        "Y aqui comienza el vivieron felices para siempre...",
//                        new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }, null);

            }
        }
    }

    private void showIncorrectAnswerDialog() {
        incorrectAnswers = 0;
        nextQuestionIndex = 0;
        showingDialog = true;
        myCountDownTimer.cancel();
        bennyHillSoundProducer.stop();
        if (criCriCriSoundProducer != null)
        {
            criCriCriSoundProducer.stop();
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout shellLayout = (RelativeLayout) findViewById(R.id.shellLayoutSeeQuestion);
                shellLayout.bringToFront();
                new ShellMessageHelper().createShellMessageTextView(SeeQuestionActivity.this,
                        shellLayout,
                        "Lamentablemente no conoces a los novios lo suficiente. \nVolve a intentarlo!",
                        new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SeeQuestionActivity.this, SeeQuestionActivity.class);
                                startActivity(intent);
                            }
                        }, 10);
            }
        });

        new SoundProducer(this, R.raw.fallochiste).onComplete(new Runnable() {
            @Override
            public void run() {
                new SoundProducer(SeeQuestionActivity.this, R.raw.sosburro).play();
            }
        }).play();
    }

    static void shuffleArray(Question[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Question a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void questionCorrectlyAnswered(View view) {
        processNextQuestionOrTriviaResult();
    }

    public class MyCountDownTimer extends CountDownTimer {
        private final TextView textView;

        public MyCountDownTimer(long startTime, long interval, TextView textView) {
            super(startTime, interval);
            this.textView = textView;
        }

        @Override
        public void onFinish() {
            showIncorrectAnswerDialog();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText("" + (int) (Math.ceil(millisUntilFinished / 1000f) - 1));
        }

        public void reset() {
            this.cancel();
        }
    }
}
