package com.example.android.spellingCoach;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spellingCoach.Data.SpellingCoachContract.*;
import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

import java.util.ArrayList;

//TODO: Include letter sounds
//TODO: When spelling is incorrect, show the correct spelling.
public class Lesson extends AppCompatActivity {
    private ArrayList<SpellWord> words;
    private SpellWord currentWord;
    private Speller speller;
    private Button firstLetter, secondLetter, thirdLetter, fourthLetter;
    private TextView answerText,wordText, wordHints;
    private ImageView spellResult;
    private int numberCorrect=0, currentWordIndex,numberOfWords;
    private int [] quizWordsindex;
    MediaPlayer mediaPlayer;
    private SpellingCoachDBManager mDBM;

    public static final int ACTIVITYTYPE_LESSON=1;
    public static final int ACTIVITYTYPE_NEWWORD=2;
    public static final int ACTIVITYTYPE_QUIZ=3;
    private int activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_lesson);
        setContentView(R.layout.activity_try_word);

        answerText = (TextView) findViewById(R.id.spelledTxt);
        wordText=(TextView) findViewById(R.id.spellWord);
        wordHints = (TextView) findViewById(R.id.wordHints);
        firstLetter = (Button) findViewById(R.id.firstLetter);
        secondLetter = (Button) findViewById(R.id.secondLetter);
        thirdLetter = (Button) findViewById(R.id.thirdLetter);
        fourthLetter = (Button) findViewById(R.id.fourthLetter);
        spellResult = (ImageView) findViewById(R.id.spellResult);

        mDBM=new SpellingCoachDBManager(this);
        activityType  = this.getIntent().getIntExtra("ACTIVITYTYPE",ACTIVITYTYPE_LESSON);
        startActivity();
    }

    protected void startActivity(){
        ActionBar actionBar=getSupportActionBar();
        currentWordIndex=0;
        switch(activityType) {
            case ACTIVITYTYPE_LESSON:
                actionBar.setTitle("Lesson Activity");
                words =mDBM.getLessonWordsList();
                numberOfWords=words.size();
                break;
            case ACTIVITYTYPE_NEWWORD:
                actionBar.setTitle("New Word!");
                int numberOfNewWords=this.getIntent().getIntExtra("NUMBEROFNEWWORDS",1);
                Toast.makeText(getApplicationContext(), "No Problem "+numberOfNewWords, Toast.LENGTH_SHORT).show();
                words =mDBM.getNewWordArrayList(numberOfNewWords);
                numberOfWords=words.size();
                break;
            case ACTIVITYTYPE_QUIZ:
                actionBar.setTitle("Quiz Activity");
                numberOfWords=this.getIntent().getIntExtra("NUMBEROFQUIZWORDS",5);
                words =mDBM.getQuizWordsList();
                quizWordsindex=pickWords(numberOfWords,words.size());
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        newWord();
    }

    protected void onDestroy(){
        super.onDestroy();
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
        switch (activityType){
            case ACTIVITYTYPE_LESSON:
                checkLessonAnswer(speller.checkSpelling());
                break;
            case ACTIVITYTYPE_NEWWORD:
                checkNewWordAnswer(speller.checkSpelling());
                break;
            case ACTIVITYTYPE_QUIZ:
                checkQuizAnswer(speller.checkSpelling());
                break;
        }
        //mDBM.updateWordLevel(currentWord);
        mDBM.updateWordData(currentWord);
        spellResult.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spellResult.setVisibility(View.INVISIBLE);
                currentWordIndex++;
                if (currentWordIndex<numberOfWords){newWord();}
                else {
                    if (activityType==ACTIVITYTYPE_NEWWORD){
                        activityType=ACTIVITYTYPE_LESSON;
                        startActivity();
                        newWord();
                    }
                    else if(activityType==ACTIVITYTYPE_QUIZ){
                        Intent i = new Intent(Lesson.this,QuizReport.class);
                        i.putExtra("QUIZQUESTIONS",numberOfWords);
                        i.putExtra("CORRECTQUESTIONS",numberCorrect);
                        startActivity(i);
                        finish();
                    }
                    else {
                        finish();
                    }
                }
            }
        }, 1000);
    }

    public void checkLessonAnswer(boolean spellCorrect){
        if (spellCorrect) {
            currentWord.upgradeWordLevel();
            Toast.makeText(getApplicationContext(), "CORRECT! Word Level "+currentWord.getWordLevel()+".", Toast.LENGTH_SHORT).show();
        } else {
            currentWord.downgradeWordLevel();
            Toast.makeText(getApplicationContext(), "INCORRECT! Word Level "+currentWord.getWordLevel()+".", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkNewWordAnswer(boolean spellCorrect){
        if (spellCorrect) {
            Toast.makeText(getApplicationContext(), "CORRECT! Word Level 4.", Toast.LENGTH_SHORT).show();
            currentWord.setWordLevel(WordsUserInfo.WORDLEVEL_LEVEL4);
            currentWord.incrementNumberOfSpells();
            currentWord.incrementNumberOfCorrectSpells();
            currentWord.incrementNumberOfLevelCorrect();
        } else {
            Toast.makeText(getApplicationContext(), "INCORRECT! Word Level 1.", Toast.LENGTH_SHORT).show();
            currentWord.setWordLevel(WordsUserInfo.WORDLEVEL_NEWWORD);
        }
    }
    public void checkQuizAnswer(boolean spellCorrect){
        if (spellCorrect) {
            currentWord.upgradeWordLevel();
            Toast.makeText(getApplicationContext(), "CORRECT!", Toast.LENGTH_SHORT).show();
            numberCorrect++;
        } else {
            currentWord.downgradeWordLevel();
            Toast.makeText(getApplicationContext(), "INCORRECT!", Toast.LENGTH_SHORT).show();
        }
    }

    public void play(){
        mediaPlayer= MediaPlayer.create(this, getResources().getIdentifier(currentWord.getAudioFile(), "raw", "com.example.android.spellingCoach"));
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
        if (activityType==ACTIVITYTYPE_QUIZ){
            currentWord=words.get(quizWordsindex[currentWordIndex]);
        }
        else {
            currentWord=words.get(currentWordIndex);
        }
        speller = new Speller(currentWord);
        setButtons(speller.getLetters());
        switch (currentWord.getWordLevel()){
            case WordsUserInfo.WORDLEVEL_NOTINTRODUCED:
                //New word introduction scheme. The word is only played and not shown. User than spells the word.
                wordText.setText("");
                answerText.setVisibility(View.VISIBLE);
                break;
            case WordsUserInfo.WORDLEVEL_NEWWORD:
                //Lowest word level case. The word is visible on the screen and the user is spelling the same word.
                answerText.setVisibility(View.VISIBLE);
                wordText.setText(currentWord.getWord());
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL2:
                //Second word level case. The word appears on the screen for several seconds and disappears. Than user spells the word.
                setButtonsEnabled(false);
                answerText.setVisibility(View.VISIBLE);
                wordText.setText(currentWord.getWord());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wordText.setText("");
                        setButtonsEnabled(true);
                    }
                }, 3000);
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL3:
                //Third word level case. The word is only played and not shown. User than spells the word.
                wordText.setText("");
                answerText.setVisibility(View.VISIBLE);
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL4:
                //Fourth word level case. The word in only played and the spelling is not shown on screen.
                wordText.setText("");
                //answerText.setVisibility(View.INVISIBLE);
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL5:
                //Fifth word level case. The word is only played and the spelling is not shown on screen.
                //The letter choices will test whether student can avoid common mistakes.
                //For example in spelling the first word of auction, one choice will be "O" to see if the
                //student can pick the correct letter "A" rather than "O". Or for a word ending with "OUS" or "OUIS"
                //an option to pick "I" will be given at the same time as an option to pick "S"
                //TODO: Need to implement challange letter choices. Probably in Speller class.
                wordText.setText("");
                answerText.setVisibility(View.INVISIBLE);
                break;
        }
        play();
    }

    private int [] pickWords(int nofWords,int numberofWordsInList){
        if (numberofWordsInList<nofWords){
            nofWords=numberofWordsInList;
            numberOfWords=nofWords;
        }
        int [] pickedWordsIndex=new int[nofWords];
        for(int i=0;i<nofWords;i++) {
            pickedWordsIndex[i]=-1;
            while (pickedWordsIndex[i]==-1){
                pickedWordsIndex[i]=getRandomPos(numberofWordsInList);
                for(int j=0;j<i;j++){
                    if (pickedWordsIndex[i]==pickedWordsIndex[j]){
                        pickedWordsIndex[i]=-1;
                        break;
                    }
                }
            }
        }

        //pickedWordsIndex=new int[]{5,1,2,3,4};
        return pickedWordsIndex;
    }
    private int getRandomPos(int numberOfPositions) {
        return (int )(Math.floor(Math.random() * numberOfPositions));
    }
}
