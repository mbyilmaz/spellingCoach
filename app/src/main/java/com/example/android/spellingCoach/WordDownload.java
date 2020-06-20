package com.example.android.spellingCoach;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.spellingCoach.Data.NetworkUtils;
import com.example.android.spellingCoach.Data.XMLUtils;

import java.io.IOException;
import java.net.URL;

public class WordDownload extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mSearchResultsTextView;
    private TextView mErrorMessageTextView;
    private TextView mMWWord,mMWDefinition,mMWAudio;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_download);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_search_results_xml);
        mErrorMessageTextView= (TextView) findViewById(R.id.tv_error_message_display);
        mMWWord=(TextView) findViewById(R.id.tv_mwword);
        mMWDefinition=(TextView) findViewById(R.id.tv_mwdefinition);
        mMWAudio=(TextView) findViewById(R.id.tv_mwaudio);
        mProgressBar=(ProgressBar) findViewById(R.id.pb_loading_indicator);


    }

    private void makeWordSearchQuery() {
        String wordQuery = mSearchBoxEditText.getText().toString();
        URL wordSearchUrl = NetworkUtils.buildUrl(wordQuery);
        new WordQueryTask().execute(wordSearchUrl);

    }

    private void showXMLDataView(){
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility((View.VISIBLE));
    }
    private void showErrorMessage(){
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    public class WordQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String wordSearchResults = null;
            try {
                wordSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wordSearchResults;
        }

        @Override
        protected void onPostExecute(String wordSearchResults) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (wordSearchResults != null && !wordSearchResults.equals("")) {
                showXMLDataView();
                try{
                    SpellWord wordResult=XMLUtils.simpleParseTest(wordSearchResults);
                    if (wordResult!=null){
                        mMWWord.setText(wordResult.getWord());
                        mMWDefinition.setText(wordResult.getMeaning());
                        mMWAudio.setText(wordResult.getAudioFile());
                    }
                    //mSearchResultsTextView.setText(wordSearchResults);
                    URL wordAudioUrl = NetworkUtils.buildAudioUrl(wordResult.getAudioFile());
                    mSearchResultsTextView.setText(wordAudioUrl.toString());
                    new playWordTask().execute(wordAudioUrl);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                showErrorMessage();
            }

        }
    }

    public class playWordTask extends AsyncTask<URL,Void,String>{
        MediaPlayer mediaPlayer = new MediaPlayer();
        @Override
        protected String doInBackground(URL... params) {
            URL audioUrl = params[0];
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(audioUrl.toString());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String wordSearchResults) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
            });
        }

        //TODO:We need to add additional code to release mediaPlayer if the activity is stopped early. (within onStop method) See Lesson activity)
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeWordSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View view){

        //TODO: Save function needs to be implemented!
    }

}
