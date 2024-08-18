package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intuit.sdp.BuildConfig;

public class ResultActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView resultTxt;
    LinearLayout logOut;
    int correct, wrong;
    LinearLayout restartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        progressBar = findViewById(R.id.result_progress);
        resultTxt = findViewById(R.id.resultText);
        restartQuiz = findViewById(R.id.RestartQuiz);

        logOut = findViewById(R.id.logout);

        restartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);

        progressBar.setProgress(correct);
        resultTxt.setText(correct + "/5");

    }
}
