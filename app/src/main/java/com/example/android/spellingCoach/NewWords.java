package com.example.android.spellingCoach;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spellingCoach.Data.SpellingCoachContract.*;
import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

import java.util.ArrayList;

import static com.example.android.spellingCoach.R.id.spellWord;

public class NewWords extends AppCompatActivity {
    private ArrayList<SpellWord> words;
    private SpellWord currentWord;
    private Speller speller;
    private Button firstLetter, secondLetter, thirdLetter, fourthLetter;
    private TextView answerText,wordText, wordHints;
    private int currentWordIndex=0,numberOfWords=5;
    MediaPlayer mediaPlayer;
    private SpellingCoachDBManager mDBM;
    private boolean listCompleted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_words);
        setContentView(R.layout.activity_try_word);

        answerText = (TextView) findViewById(R.id.spelledTxt);
        wordText=(TextView) findViewById(spellWord);
        wordHints = (TextView) findViewById(R.id.wordHints);
        firstLetter = (Button) findViewById(R.id.firstLetter);
        secondLetter = (Button) findViewById(R.id.secondLetter);
        thirdLetter = (Button) findViewById(R.id.thirdLetter);
        fourthLetter = (Button) findViewById(R.id.fourthLetter);

        mDBM=new SpellingCoachDBManager(this);
        int numberOfNewWords=this.getIntent().getIntExtra("NUMBEROFNEWWORDS",1);
        words =mDBM.getNewWordArrayList(numberOfNewWords);
        numberOfWords=words.size();
    }

    @Override
    protected void onResume() {
        super.onResume();
        newWord();
    }

    protected void onDestroy(){
        super.onDestroy();
        if (listCompleted){
            Intent i = new Intent(NewWords.this,Lesson.class);
            i.putExtra("ACTIVITYTYPE",Lesson.ACTIVITYTYPE_LESSON);
            startActivity(i);
        }
    }

    public void playAudio(View view) {
        play();
    }

    public void showDef(View view){
        wordHints.setText(currentWord.getMeaning());
    }

    public void showOrigin(View view){
        wordHints.setText(currentWord.getOrigin());
    }
    public void showSentence(View view){
        wordHints.setText(currentWord.getExample());
    }

    public void pickLetter(View view) {
        switch (view.getId()){
            case R.id.firstLetter:
                speller.enterLetter(firstLetter.getText().toString());
                break;
            case R.id.secondLetter:
                speller.enterLetter(secondLetter.getText().toString());
                break;
            case R.id.thirdLetter:
                speller.enterLetter(thirdLetter.getText().toString());
                break;
            case R.id.fourthLetter:
                speller.enterLetter(fourthLetter.getText().toString());
                break;
        }
        answerText.setText(speller.getSpelledWord(true));
        setButtons(speller.getLetters());
    }
    public void setButtons(char[] letters) {
        firstLetter.setText(Character.toString(letters[0]));
        secondLetter.setText(Character.toString(letters[1]));
        thirdLetter.setText(Character.toString(letters[2]));
        fourthLetter.setText(Character.toString(letters[3]));
    }

    public void setButtonsEnabled(boolean enabled){
        firstLetter.setEnabled(enabled);
        secondLetter.setEnabled(enabled);
        thirdLetter.setEnabled(enabled);
        fourthLetter.setEnabled(enabled);
    }
    public void restart(View view){
        answerText.setText("");
        newWord();
    }
    public void checkAnswer(View view) {
        answerText.setText("");
        if (speller.checkSpelling()) {
            Toast.makeText(getApplicationContext(), "CORRECT! Word Level 4.", Toast.LENGTH_SHORT).show();
            currentWord.setWordLevel(WordsUserInfo.WORDLEVEL_LEVEL4);
            currentWord.incrementNumberOfSpells();
            currentWord.incrementNumberOfCorrectSpells();
            currentWord.incrementNumberOfLevelCorrect();
        } else {
            Toast.makeText(getApplicationContext(), "INCORRECT! Word Level 1.", Toast.LENGTH_SHORT).show();
            currentWord.setWordLevel(WordsUserInfo.WORDLEVEL_NEWWORD);
        }
        mDBM.updateWordLevel(currentWord);
        currentWordIndex++;
        if (currentWordIndex<numberOfWords){newWord();}
        else {
            listCompleted=true;
            finish();
        }
    }

    public void play(){
        mediaPlayer=MediaPlayer.create(this, getResources().getIdentifier(currentWord.getAudioFile(), "raw", "com.example.android.spellingCoach"));
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer=null;
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    private void newWord(){
        currentWord=words.get(currentWordIndex);
        speller = new Speller(currentWord.getWord());
        setButtons(speller.getLetters());
        play();
    }



}
