package com.lagreca.casamiento.casamiento;

import static java.lang.Math.max;

public class Question {

    private String text;
    private Answer[] answers;


    public Question(String text, Answer... answers) {
        this.text = text;
        this.answers = answers;
        int maxTextLenght = 0;
        for (Answer answer : answers) {
            maxTextLenght = max(maxTextLenght, answer.getText().length());
        }
        for (Answer answer : answers) {
            if (answer.getText().length() < maxTextLenght)
            {
                for (int i = 0; answer.getText().length() != maxTextLenght; i++)
                {
                    if (i % 2 == 0)
                    {
                        answer.setText(answer.getText() + " ");
                    }
                    else
                    {
                        answer.setText(" " + answer.getText());
                    }
                }
            }
        }
    }

    public String getText() {
        return text;
    }

    public Answer[] getAnswers() {
        return answers;
    }
}
