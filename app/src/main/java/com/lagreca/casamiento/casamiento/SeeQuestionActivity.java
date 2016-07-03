package com.lagreca.casamiento.casamiento;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SeeQuestionActivity extends AppCompatActivity {

    private static final int NUMBER_OF_QUESTIONS_TO_ASK = 5;

    private Question currentQuestion;
    private int nextQuestionIndex = 0;
    private int incorrectAnswers = 0;

    private static Question[] questions = new Question[]
            {
                    new Question("Cuantas pijas le entran en la cola a la novia", new Answer("1"), new Answer("2"), new Answer("3", true)),
                    new Question("A la novia, le gusta escupir o tragar", new Answer("Tragar"), new Answer("Escupir", true)),
                    new Question("Cuanto tardo en entregar la novia?. 1 minuto? 10 minutos? 1 hora?", new Answer("1"), new Answer("2"), new Answer("3", true))
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_question);
        addNextQuestion();

    }

    private boolean addNextQuestion() {
        if (nextQuestionIndex >= questions.length)
        {
            return false;
        }
        TextView questionTextView = (TextView) findViewById(R.id.content);
        currentQuestion = questions[nextQuestionIndex++];
        questionTextView.setText(currentQuestion.getText());
        LinearLayout canvasLinearLayout = (LinearLayout)findViewById(R.id.buttonLayout);
        canvasLinearLayout.removeAllViews();
        for (Answer answer : currentQuestion.getAnswers()) {
            Button button = new Button(this);
            button.setTextColor(Color.WHITE);
            button.setText(answer.getText());
            LinearLayout buttonLinearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLinearLayout.addView(button, buttonLayoutParams);

            canvasLinearLayout.addView(buttonLinearLayout);
            if (answer.isCorrect())
            {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionCorrectlyAnswered(view);
                    }
                });
            }
            else
            {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionIncorrectlyAnswered(view);
                    }
                });
            }
        }
        return true;
    }

    private void questionIncorrectlyAnswered(View view) {
        incorrectAnswers++;
        processNextQuestionOrTriviaResult();
    }

    private void processNextQuestionOrTriviaResult() {
        boolean moreQuestions = addNextQuestion();
        if (!moreQuestions) {
            if (incorrectAnswers > 0)
            {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Burrooooo!").setMessage("El codigo secreto no es valido. \nHasta no contestar bien no podras ver el mensaje secreto.");
//                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        exitDialogWrongAnswers();
//                    }
//                });
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
            else
            {
                Intent intent = new Intent(this, SuccessfulTriviaActivity.class);
                startActivity(intent);
            }
        }
    }

    private void exitDialogWrongAnswers() {
        Intent intent = new Intent(this, BadAnsweredTriviaActivity.class);
        startActivity(intent);
    }

    public void questionCorrectlyAnswered(View view) {
        processNextQuestionOrTriviaResult();
    }
}
