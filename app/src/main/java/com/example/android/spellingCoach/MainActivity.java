package com.example.android.spellingCoach;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;



import com.example.android.spellingCoach.Data.SpellingCoachDBManager;


public class MainActivity extends AppCompatActivity {
    private Button btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int numberOfLessonWords=25;
    private int numberOfQuizWords=10;
    private SpellingCoachDBManager mDBM=new SpellingCoachDBManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    public void lesson(View view){
        int numberOfNewWords=mDBM.getNumberOfNewWords(numberOfLessonWords);
        if (numberOfNewWords>0){
            Intent i = new Intent(MainActivity.this,Lesson.class);
            i.putExtra("NUMBEROFNEWWORDS",numberOfNewWords);
            i.putExtra("ACTIVITYTYPE",Lesson.ACTIVITYTYPE_NEWWORD);
            startActivity(i);
        } else{
            Intent i = new Intent(MainActivity.this,Lesson.class);
            i.putExtra("ACTIVITYTYPE",Lesson.ACTIVITYTYPE_LESSON);
            startActivity(i);
        }
    }
    public void newWords(View view){
        //TODO: Fix this method such that it runs the Lesson Activity with the newword option
        Intent i = new Intent(MainActivity.this,NewWords.class);
        startActivity(i);
    }

    public void startQuiz(View view){
        Intent i = new Intent(MainActivity.this,Lesson.class);
        i.putExtra("ACTIVITYTYPE",Lesson.ACTIVITYTYPE_QUIZ);
        i.putExtra("NUMBEROFQUIZWORDS",numberOfQuizWords);
        startActivity(i);
    }

    /** Start a new activity showing the list of words
     *
     */

    public void showWords(View view){
        Intent i = new Intent(MainActivity.this,WordList.class);
        startActivity(i);
    }
    public void wordDownload(View view){
        Intent i = new Intent(MainActivity.this,WordDownload.class);
        startActivity(i);
    }
    public void showDBTest(View view){
        Intent i = new Intent(MainActivity.this,DatabaseTest.class);
        startActivity(i);
    }
    /**
         * Showing google speech input dialog
         * */

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Speech Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }
}
