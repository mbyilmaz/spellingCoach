package com.example.android.spellingCoach;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

//TODO: Ultimate goal VOICERECOGNITION
public class TryWord extends AppCompatActivity {
    private SpellWord word;
    private Speller speller;
    private Button firstLetter, secondLetter, thirdLetter, fourthLetter;
    private TextView answerText,wordHints;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_word);
        SpellingCoachDBManager mDBM=new SpellingCoachDBManager(this);
        String word_id = this.getIntent().getStringExtra("WORDID");
        word=mDBM.getSpellWord(word_id);

        answerText = (TextView) findViewById(R.id.spelledTxt);
        wordHints = (TextView) findViewById(R.id.wordHints);
        speller = new Speller(word.getWord());
        firstLetter = (Button) findViewById(R.id.firstLetter);
        secondLetter = (Button) findViewById(R.id.secondLetter);
        thirdLetter = (Button) findViewById(R.id.thirdLetter);
        fourthLetter = (Button) findViewById(R.id.fourthLetter);
        setButtons(speller.getLetters());
    }

    //TODO: In getting the identifier for the mediafile we are using getResources method. If we store the resource id (which is an integer)
    //in the SpellWord object rather then the filename, getResources() method will be unnecessary.
    public void playAudio(View view) {
        mediaPlayer=MediaPlayer.create(this, getResources().getIdentifier(word.getAudioFile(), "raw", "com.example.android.spellingCoach"));
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer=null;
            }
        });

        //Toast.makeText(this,"The audio file is "+word.getAudioFile(),Toast.LENGTH_SHORT).show();
    }

    public void showDef(View view){
        wordHints.setText(word.getMeaning());
    }

    public void showOrigin(View view){
        wordHints.setText(word.getOrigin());
    }
    public void showSentence(View view){
        wordHints.setText(word.getExample());
    }

    public void setButtons(char[] letters) {
        firstLetter.setText(Character.toString(letters[0]));
        secondLetter.setText(Character.toString(letters[1]));
        thirdLetter.setText(Character.toString(letters[2]));
        fourthLetter.setText(Character.toString(letters[3]));
    }

    public void pickLetter(View view) {
        if (view.getId() == R.id.firstLetter) {
            speller.enterLetter(firstLetter.getText().toString());
        } else if (view.getId() == R.id.secondLetter) {
            speller.enterLetter(secondLetter.getText().toString());
        } else if (view.getId() == R.id.thirdLetter) {
            speller.enterLetter(thirdLetter.getText().toString());
        } else if (view.getId() == R.id.fourthLetter) {
            speller.enterLetter(fourthLetter.getText().toString());
        }
        answerText.setText(speller.getSpelledWord(true));
        setButtons(speller.getLetters());
    }

    public void checkAnswer(View view) {

        if (speller.checkSpelling()) {
            answerText.setText("");
            answerText.setHint("CORRECT!");
            setButtons(speller.getLetters());
        } else {
            answerText.setText("");
            answerText.setHint("TRY AGAIN!");
            setButtons(speller.getLetters());

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}