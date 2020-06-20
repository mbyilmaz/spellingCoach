package com.example.android.spellingCoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

import java.util.ArrayList;

public class WordList extends AppCompatActivity {
//TODO: If we add play buttons to this list, we must relased the mediaplayer both before and after the play
// so that a played resource is released or a partially played resource is stopped and released when another
// item is clicked on.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        SpellingCoachDBManager mDBM=new SpellingCoachDBManager(this);

        //ArrayList<SpellWord> words = new ArrayList<SpellWord>();
        ArrayList<SpellWord> words =mDBM.getSpellWordArrayList();

        WordAdapter wordsAdapter = new WordAdapter(this, words);

        ListView listView = (ListView) findViewById(R.id.word_list);

        listView.setAdapter(wordsAdapter);
    }
}
