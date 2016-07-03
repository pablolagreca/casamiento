package com.lagreca.casamiento.casamiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void seeQuestions(View view) {
        Intent intent = new Intent(this, SeeQuestionActivity.class);
        startActivity(intent);
    }
}
