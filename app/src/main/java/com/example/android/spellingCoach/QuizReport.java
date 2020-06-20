package com.example.android.spellingCoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_report);
        int quizquestions  = this.getIntent().getIntExtra("QUIZQUESTIONS",0);
        int correctquestions  = this.getIntent().getIntExtra("CORRECTQUESTIONS",0);
        int incorrectquestions  = quizquestions-correctquestions;
        TextView nQuizQuestion = (TextView) findViewById(R.id.nQuizQuestions);
        TextView nQuizCorrect =(TextView) findViewById(R.id.nQuizCorrect);
        TextView nQuizIncorrect = (TextView) findViewById(R.id.nQuizIncorrect);
        nQuizQuestion.setText(Integer.toString(quizquestions));
        nQuizCorrect.setText(Integer.toString(correctquestions));
        nQuizIncorrect.setText(Integer.toString(incorrectquestions));

    }
    public void close(View view){
        finish();
    }
}
