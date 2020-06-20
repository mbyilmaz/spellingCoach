package com.example.android.spellingCoach.Data;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.spellingCoach.R;


public class WordSpellReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_spell_report);
        String spelledWord  = this.getIntent().getStringExtra("SPELLEDWORD");
        TextView rSpelledWord = (TextView) findViewById(R.id.rSpelledWord);
        rSpelledWord.setText(spelledWord);


//        String text2 = text + CepVizyon.getPhoneCode() + "\n\n"
//                + getText(R.string.currentversion) + CepVizyon.getLicenseText();
//
//        Spannable spannable = new SpannableString(text2);
//
//        spannable.setSpan(new ForegroundColorSpan(Color.WHITE), text.length(), (text + CepVizyon.getPhoneCode()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        myTextView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
