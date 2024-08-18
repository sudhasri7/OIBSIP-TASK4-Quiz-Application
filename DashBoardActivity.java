package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DashBoardActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    int timerValue = 20;
    ProgressBar progressBar;
    ArrayList<ModelClass> listOfQuestion;
    ModelClass modelClass;
    int index = 0;
    TextView cardQuestions, optiona, optionb, optionc, optiond;
    CardView cardOa, cardOb, cardOc, cardOd;

    int correctCount = 0;
    int wrongCount = 0;
    LinearLayout btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        listOfQuestion = new ArrayList<>();

        listOfQuestion.add(new ModelClass("Fukushima Daiichi nuclear power plant is located in which country?", "China", "Japan", "South Korea", "Russia", "Japan"));
        listOfQuestion.add(new ModelClass("USA’s second biggest city is ", "Seattle", "Atlanta", " Newyork city", "Los Angeles", "Los Angeles"));
        listOfQuestion.add(new ModelClass("Which one of the following rivers is recharged by subsoil water", "Godavari", "Damodar", "Narmada", "Krishna", "Narmada"));
        listOfQuestion.add(new ModelClass("Name the continent where 'Tundra' type of climate is not found", "Europe", "Asia", " North America", " Africa", " Africa"));
        listOfQuestion.add(new ModelClass("At which particular place on earth are days and nights of equal length always?", " Prime Meridian", "Poles", "Equator", "No where", "Equator"));

        Collections.shuffle(listOfQuestion);
        modelClass = listOfQuestion.get(index);

        Hooks();
        setAllData();

        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashBoardActivity.this, R.style.dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.timeout_dialog);

                dialog.findViewById(R.id.btn_tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();

            }
        }.start();

    }

    private void setAllData() {
        cardQuestions.setText(modelClass.getQuestion());
        optiona.setText(modelClass.getA());
        optionb.setText(modelClass.getB());
        optionc.setText(modelClass.getC());
        optiond.setText(modelClass.getD());
    }

    private void Hooks() {
        progressBar = findViewById(R.id.quiz_timer);

        cardQuestions = findViewById(R.id.card_question);
        optiona = findViewById(R.id.optionA);
        optionb = findViewById(R.id.optionB);
        optionc = findViewById(R.id.optionC);
        optiond = findViewById(R.id.optionD);

        cardOa = findViewById(R.id.card_optionA);
        cardOb = findViewById(R.id.card_optionB);
        cardOc = findViewById(R.id.card_optionC);
        cardOd = findViewById(R.id.card_optionD);

        btnNext = findViewById(R.id.btnNext);

    }

    public void Correct(CardView cardOa) {
        cardOa.setCardBackgroundColor(getResources().getColor(R.color.green));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount++;
                index++;
                modelClass = listOfQuestion.get(index);
                resetColor();
                setAllData();
            }
        });

    }

    public void Wrong(CardView cardOa) {
        cardOa.setCardBackgroundColor(getResources().getColor(R.color.red));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if (index < listOfQuestion.size() - 1) {
                    index++;
                    modelClass = listOfQuestion.get(index);
                    resetColor();
                    setAllData();

                } else {
                    result();
                }
            }
        });
    }

    private void result() {
        Intent intent = new Intent(DashBoardActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        startActivity(intent);

    }

    public void enableButton() {
        cardOa.setClickable(true);
        cardOb.setClickable(true);
        cardOc.setClickable(true);
        cardOd.setClickable(true);
    }

    public void disableButton() {
        cardOa.setClickable(false);
        cardOb.setClickable(false);
        cardOc.setClickable(false);
        cardOd.setClickable(false);
    }

    public void resetColor() {
        cardOa.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOb.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOc.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOd.setCardBackgroundColor(getResources().getColor(R.color.white));
    }

    public void optionAClick(View view) {


        if (modelClass.getA().equals(modelClass.getAnswer())) {
            cardOa.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQuestion.size() - 1) {
                Correct(cardOa);
            } else {
                result();
            }
        } else {
            Wrong(cardOa);
        }
    }

    public void optionBClick(View view) {


        if (modelClass.getB().equals(modelClass.getAnswer())) {
            cardOb.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQuestion.size() - 1) {
                Correct(cardOb);
            } else {
                result();
            }
        } else {
            Wrong(cardOb);
        }
    }

    public void optionCClick(View view) {


        if (modelClass.getC().equals(modelClass.getAnswer())) {
            cardOc.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQuestion.size() - 1) {
                Correct(cardOc);
            } else {
                result();
            }
        } else {
            Wrong(cardOc);
        }
    }

    public void optionDClick(View view) {


        if (modelClass.getD().equals(modelClass.getAnswer())) {
            cardOd.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQuestion.size() - 1) {
                Correct(cardOd);
            } else {
                result();
            }
        } else {
            Wrong(cardOd);
        }
    }
}