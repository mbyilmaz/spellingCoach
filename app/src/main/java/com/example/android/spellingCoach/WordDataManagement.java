package com.example.android.spellingCoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

public class WordDataManagement extends AppCompatActivity {
    private TextView dbm_WordID;
    private EditText dbm_Word, dbm_Meaning, dbm_Example, dbm_Origin,dbm_Audio;
    private SpellingCoachDBManager mDBM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_data_management);
        dbm_WordID = (TextView) findViewById(R.id.DBM_wordID);
        dbm_Word = (EditText) findViewById(R.id.DBM_word);
        dbm_Meaning = (EditText) findViewById(R.id.DBM_Meaning);
        dbm_Example = (EditText) findViewById(R.id.DBM_Example);
        dbm_Origin = (EditText) findViewById(R.id.DBM_Origin);
        dbm_Audio = (EditText) findViewById(R.id.DBM_Audio);
        mDBM=new SpellingCoachDBManager(this);
        //mDBM.fixEntry();
        displayWord(2);
    }

    public void updateEntry(View view){
        SpellWord updateWord= new SpellWord(Integer.parseInt(dbm_WordID.getText().toString()),
                                            dbm_Word.getText().toString(),
                                            dbm_Meaning.getText().toString(),
                                            dbm_Example.getText().toString(),
                                            dbm_Origin.getText().toString(),
                                            dbm_Audio.getText().toString());
        mDBM.updateWordtoDB(updateWord);
        //Toast.makeText(WordDataManagement.this,"The origin ID is "+mDBM.updateWordtoDB(updateWord),Toast.LENGTH_SHORT).show();
    }
    public void previousEntry(View view){
        int newID=Integer.parseInt(dbm_WordID.getText().toString())-1;
        if (newID>=1) {displayWord(newID);}
        else {Toast.makeText(WordDataManagement.this,"Reached the beginning of the word list",Toast.LENGTH_SHORT).show(); }
    }

    public void nextEntry(View view){
        int newID=Integer.parseInt(dbm_WordID.getText().toString())+1;
        displayWord(newID);
    }

    private void displayWord(int wordID){
        SpellWord pickedWord= mDBM.getSpellWord(Integer.toString(wordID));

        if (pickedWord!=null){
            dbm_WordID.setText(pickedWord.getWord_id());
            dbm_Word.setText(pickedWord.getWord());
            dbm_Meaning.setText(pickedWord.getMeaning());
            dbm_Example.setText(pickedWord.getExample());
            dbm_Origin.setText(pickedWord.getOrigin());
            dbm_Audio.setText(pickedWord.getAudioFile());
        }
        else{
            Toast.makeText(WordDataManagement.this,"Reached the end of the word list",Toast.LENGTH_SHORT).show();}
    }
}
