package com.example.android.spellingCoach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

public class WordInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
        SpellingCoachDBManager mDBM=new SpellingCoachDBManager(this);

        LinearLayout item = (LinearLayout)findViewById(R.id.word_info);
        View child = getLayoutInflater().inflate(R.layout.word_item, null);
        item.addView(child);

        final String word_id = this.getIntent().getStringExtra("WORDID");
        final SpellWord word=mDBM.getSpellWord(word_id);

        TextView wordTextView = (TextView) child.findViewById(R.id.spell_word);
        wordTextView.setText(word.getWord());
        TextView meaningTextView = (TextView) child.findViewById(R.id.word_meaning);
        meaningTextView.setText(word.getMeaning());

        Button tryButton=(Button) findViewById(R.id.try_word);
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WordInfo.this,TryWord.class);
                i.putExtra("WORDID",word.getWord_id());
                startActivity(i);
            }
        });
        //TextView meaningTextView = (TextView) child.findViewById(R.id.word_meaning);
        //meaningTextView.setText(currentWord.getMeaning());


    }
}
