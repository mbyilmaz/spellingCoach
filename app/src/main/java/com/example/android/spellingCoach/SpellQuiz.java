package com.example.android.spellingCoach;

import android.media.MediaPlayer;
import android.os.Handler;
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


public class SpellQuiz extends AppCompatActivity {
    private ArrayList<SpellWord> words;
    private SpellWord currentWord;
    private Speller speller;
    private Button firstLetter, secondLetter, thirdLetter, fourthLetter;
    private TextView answerText,wordText, wordHints;
    private int numberCorrect,currentWordIndex=0,numberOfWords=5;
    private int [] quizWordsindex;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_try_word);
        answerText = (TextView) findViewById(R.id.spelledTxt);
        wordText=(TextView) findViewById(spellWord);
        wordHints = (TextView) findViewById(R.id.wordHints);
        firstLetter = (Button) findViewById(R.id.firstLetter);
        secondLetter = (Button) findViewById(R.id.secondLetter);
        thirdLetter = (Button) findViewById(R.id.thirdLetter);
        fourthLetter = (Button) findViewById(R.id.fourthLetter);

        SpellingCoachDBManager mDBM=new SpellingCoachDBManager(this);
        words =mDBM.getSpellWordArrayList();
        quizWordsindex=pickWords(numberOfWords,words.size());

    }

    @Override
    protected void onResume() {
        super.onResume();
        newWord();
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

    public void checkAnswer(View view) {
        if (currentWordIndex<numberOfWords-1){
            answerText.setText("");
            if (speller.checkSpelling()) {
                wordHints.setHint("CORRECT! NEXT WORD...");
                numberCorrect++;
            } else {
                wordHints.setHint("WRONG! NEXT WORD...");
            }
            currentWordIndex++;
            newWord();
        }
        else {
            if (speller.checkSpelling()) {
                wordHints.setHint("CORRECT! TEST IS FINISHED.");
                numberCorrect++;
            } else {
                wordHints.setHint("WRONG! TEST IS FINISHED.");
            }
            Toast.makeText(this,"You spelled "+numberCorrect+ " words out of "+numberOfWords+" correctly.",Toast.LENGTH_SHORT).show();
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
        currentWord=words.get(quizWordsindex[currentWordIndex]);
        speller = new Speller(currentWord.getWord());
        setButtons(speller.getLetters());
        play();
        switch (currentWord.getWordLevel()){
            case WordsUserInfo.WORDLEVEL_NEWWORD:
                //Lowest word level case. The word is visible on the screen and the user is spelling the same word.
                wordText.setText(currentWord.getWord());
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL2:
                //Second word level case. The word appears on the screen for several seconds and disappears. Than user spells the word.
                setButtonsEnabled(false);
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
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL4:
                //Fourth word level case. The word in only played and the spelling is not shown on screen.
                answerText.setVisibility(View.INVISIBLE);
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL5:
                //Fifth word level case. The word is only played and the spelling is not shown on screen.
                //The letter choices will test whether student can avoid common mistakes.
                //For example in spelling the first word of auction, one choice will be "O" to see if the
                //student can pick the correct letter "A" rather than "O". Or for a word ending with "OUS" or "OUIS"
                //an option to pick "I" will be given at the same time as an option to pick "S"
                answerText.setVisibility(View.INVISIBLE);
                break;

        }
    }

    private int [] pickWords(int numberofWords,int numberofWordsInList){
        int [] pickedWordsIndex=new int[numberofWords];
        for(int i=0;i<numberofWords;i++) {
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
