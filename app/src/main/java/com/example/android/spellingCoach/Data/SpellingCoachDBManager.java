package com.example.android.spellingCoach.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.spellingCoach.SpellWord;
import com.example.android.spellingCoach.Data.SpellingCoachContract.*;

import java.util.ArrayList;


/**
 * Created by byilm on 4/25/2017.
 */

public class SpellingCoachDBManager {
    Context context;

    public SpellingCoachDBManager(Context context) {
        this.context = context;
    }

    public SpellWord getSpellWord(String id) {
        SpellWord newWord = null;
        String[] projection = {WordsEntry._ID,
                WordsEntry.COLUMN_WORD,
                WordsEntry.COLUMN_MEANING,
                WordsEntry.COLUMN_EXAMPLE,
                WordsEntry.COLUMN_ORIGIN,
                WordsEntry.COLUMN_AUDIOFILE};

        String [] userProjection = {WordsUserInfo.COLUMN_WORDLEVEL,
                                    WordsUserInfo.COLUMN_NUMBERTRIED,
                                    WordsUserInfo.COLUMN_NUMBERCORRECT,
                                    WordsUserInfo.COLUMN_NUMBERLEVELCORRECT};

        Cursor wordCursor = context.getContentResolver().query(Uri.withAppendedPath(WordsEntry.CONTENT_URI, id), projection, null, null, null);
        Cursor userInfoCursor= context.getContentResolver().query(Uri.withAppendedPath(WordsUserInfo.CONTENT_URI,id),userProjection,null,null,null);

        try {
            if (wordCursor.moveToFirst()) {
                newWord = new SpellWord(wordCursor.getInt(wordCursor.getColumnIndex(WordsEntry._ID)),
                        wordCursor.getString(wordCursor.getColumnIndex(WordsEntry.COLUMN_WORD)),
                        wordCursor.getString(wordCursor.getColumnIndex(WordsEntry.COLUMN_MEANING)),
                        wordCursor.getString(wordCursor.getColumnIndex(WordsEntry.COLUMN_EXAMPLE)),
                        getOrigin(wordCursor.getString(wordCursor.getColumnIndex(WordsEntry.COLUMN_ORIGIN))),
                        wordCursor.getString(wordCursor.getColumnIndex(WordsEntry.COLUMN_AUDIOFILE)));
                userInfoCursor.moveToFirst();
                newWord.setWordLevel(userInfoCursor.getString(userInfoCursor.getColumnIndex(WordsUserInfo.COLUMN_WORDLEVEL)));
                //newWord.setWordLevel(WordsUserInfo.WORDLEVEL_LEVEL2);
                newWord.setNumberOfSpells(userInfoCursor.getInt(userInfoCursor.getColumnIndex(WordsUserInfo.COLUMN_NUMBERTRIED)));
                newWord.setNumberOfCorrectSpells(userInfoCursor.getInt(userInfoCursor.getColumnIndex(WordsUserInfo.COLUMN_NUMBERCORRECT)));
                newWord.setNumberOfLevelCorrect(userInfoCursor.getInt(userInfoCursor.getColumnIndex(WordsUserInfo.COLUMN_NUMBERLEVELCORRECT)));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            wordCursor.close();
            userInfoCursor.close();
        }
        return newWord;
    }


    public ArrayList<SpellWord> getSpellWordArrayList() {
        ArrayList<SpellWord> spellWordArrayList = new ArrayList<SpellWord>();
        SpellWord newWord = null;
        String[] projection = {WordsEntry._ID,
                WordsEntry.COLUMN_WORD,
                WordsEntry.COLUMN_MEANING,
                WordsEntry.COLUMN_EXAMPLE,
                WordsEntry.COLUMN_ORIGIN,
                WordsEntry.COLUMN_AUDIOFILE};

        Cursor cursor = context.getContentResolver().query(WordsEntry.CONTENT_URI, projection, null, null, null);
        try {
            while (cursor.moveToNext()) {
                newWord = new SpellWord(cursor.getInt(cursor.getColumnIndex(WordsEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(WordsEntry.COLUMN_WORD)),
                        cursor.getString(cursor.getColumnIndex(WordsEntry.COLUMN_MEANING)),
                        cursor.getString(cursor.getColumnIndex(WordsEntry.COLUMN_EXAMPLE)),
                        getOrigin(cursor.getString(cursor.getColumnIndex(WordsEntry.COLUMN_ORIGIN))),
                        cursor.getString(cursor.getColumnIndex(WordsEntry.COLUMN_AUDIOFILE)));
                spellWordArrayList.add(newWord);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
        return spellWordArrayList;
    }

    //TODO: When we run out of new words the learning activity should not initiate new word activity!
    public ArrayList<SpellWord> getNewWordArrayList(int nNewWords) {
        ArrayList<SpellWord> spellWordArrayList = new ArrayList<SpellWord>();
        SpellWord newWord = null;
        Cursor newWordIDs= getWordIDs(WordsUserInfo.WORDLEVEL_NOTINTRODUCED);
        for (int i = 0; i < nNewWords; i++) {
            if (newWordIDs.moveToNext()){
                newWord=getSpellWord(newWordIDs.getString(newWordIDs.getColumnIndex(WordsUserInfo.COLUMN_ID)));
                newWord.setWordLevel(WordsUserInfo.WORDLEVEL_NOTINTRODUCED);
                spellWordArrayList.add(newWord);
            }
        }
        return spellWordArrayList;
    }


    public ArrayList<SpellWord> getLessonWordsList() {
        ArrayList<SpellWord> spellWordArrayList = new ArrayList<SpellWord>();
        SpellWord newWord = null;
        String[] projection = {WordsUserInfo.COLUMN_ID};
        String selection = "( " + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_NOTINTRODUCED + ") AND ("
                + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_WORDLEARNED + ")";
        Cursor lessonWordIDs = context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection,
                selection, null, null);
        for (int i = 0; i < lessonWordIDs.getCount(); i++) {
            if (lessonWordIDs.moveToNext()){
                newWord=getSpellWord(lessonWordIDs.getString(lessonWordIDs.getColumnIndex(WordsUserInfo.COLUMN_ID)));
                //newWord.setWordLevel(WordsUserInfo.WORDLEVEL_NOTINTRODUCED);
                spellWordArrayList.add(newWord);
            }
        }
        return spellWordArrayList;
    }

    public ArrayList<SpellWord> getQuizWordsList() {
        ArrayList<SpellWord> spellWordArrayList = new ArrayList<SpellWord>();
        SpellWord newWord = null;
        String[] projection = {WordsUserInfo.COLUMN_ID};
        String selection = "( " + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_NOTINTRODUCED + ") AND ("
                + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_NEWWORD + ") AND ("
                + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_LEVEL2 + ")";
        Cursor quizWordIDs = context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection,
                selection, null, null);
        for (int i = 0; i < quizWordIDs.getCount(); i++) {
            if (quizWordIDs.moveToNext()){
                newWord=getSpellWord(quizWordIDs.getString(quizWordIDs.getColumnIndex(WordsUserInfo.COLUMN_ID)));
                //newWord.setWordLevel(WordsUserInfo.WORDLEVEL_NOTINTRODUCED);
                spellWordArrayList.add(newWord);
            }
        }
        return spellWordArrayList;
    }

    public String getOriginID(String origin) {
        String originID = null;
        String[] originProjection = {OriginsEntry._ID,
                OriginsEntry.COLUMN_ORIGIN};
        String[] whichOrigin = {origin};
        Cursor originCursor = context.getContentResolver().query(
                OriginsEntry.CONTENT_URI, originProjection, OriginsEntry.COLUMN_ORIGIN + "=?", whichOrigin, null);
        try {
            originCursor.moveToFirst();
            originID = originCursor.getString(originCursor.getColumnIndex(OriginsEntry._ID));
        } finally {
            originCursor.close();
        }
        return originID;
    }

    public String getOrigin(String originID) {
        String origin = null;
        String[] originProjection = {OriginsEntry._ID,
                OriginsEntry.COLUMN_ORIGIN};
        Cursor originCursor = context.getContentResolver().query(
                Uri.withAppendedPath(OriginsEntry.CONTENT_URI, originID), originProjection, null, null, null);

        try {
            originCursor.moveToFirst();
            origin = originCursor.getString(originCursor.getColumnIndex(OriginsEntry.COLUMN_ORIGIN));
        } finally {
            originCursor.close();
        }
        return origin;
    }

    //TODO: Following method needs to be implemented.
    public void saveNewWordtoDB(SpellWord newWord) {
        ContentValues values = new ContentValues();
        values.put(WordsEntry.COLUMN_WORD, newWord.getWord());
        values.put(WordsEntry.COLUMN_MEANING, newWord.getMeaning());
        values.put(WordsEntry.COLUMN_EXAMPLE, newWord.getExample());
        values.put(WordsEntry.COLUMN_ORIGIN, newWord.getOrigin());
        values.put(WordsEntry.COLUMN_AUDIOFILE, newWord.getAudioFile());
        //db.insert(WordsEntry.TABLE_NAME,null,values);
    }

    public void updateWordtoDB(SpellWord updateWord) {
        ContentValues values = new ContentValues();
        String wordID = updateWord.getWord_id();
        values.put(WordsEntry.COLUMN_WORD, updateWord.getWord());
        values.put(WordsEntry.COLUMN_MEANING, updateWord.getMeaning());
        values.put(WordsEntry.COLUMN_EXAMPLE, updateWord.getExample());
        values.put(WordsEntry.COLUMN_ORIGIN, getOriginID(updateWord.getOrigin()));
        values.put(WordsEntry.COLUMN_AUDIOFILE, updateWord.getAudioFile());
        context.getContentResolver().update(Uri.withAppendedPath(WordsEntry.CONTENT_URI, wordID), values, null, null);
    }

    public int getWordLevel(int wordID) {
        String[] projection = {WordsUserInfo.COLUMN_WORDLEVEL};
        String selection = WordsUserInfo.COLUMN_ID + "=?";
        String [] selectionArgs={Integer.toString(wordID)};
        Cursor cursor = context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection,
                selection, selectionArgs, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(WordsUserInfo.COLUMN_WORDLEVEL));
    }

    public void updateWordLevel(SpellWord updateWord) {
        ContentValues values=new ContentValues();
        String wordID= updateWord.getWord_id();
        values.put(WordsUserInfo.COLUMN_WORDLEVEL,updateWord.getWordLevel());
        context.getContentResolver().update(Uri.withAppendedPath(WordsUserInfo.CONTENT_URI,wordID),values,null,null);
    }

    public void updateWordData(SpellWord updateWord) {
        ContentValues values=new ContentValues();
        String wordID= updateWord.getWord_id();
        values.put(WordsUserInfo.COLUMN_WORDLEVEL,updateWord.getWordLevel());
        values.put(WordsUserInfo.COLUMN_NUMBERTRIED,updateWord.getNumberOfSpells());
        values.put(WordsUserInfo.COLUMN_NUMBERCORRECT,updateWord.getNumberOfCorrectSpells());
        values.put(WordsUserInfo.COLUMN_NUMBERLEVELCORRECT,updateWord.getNumberOfLevelCorrect());
        //TODO: Add the latest attempt date to database as well.
        context.getContentResolver().update(Uri.withAppendedPath(WordsUserInfo.CONTENT_URI,wordID),values,null,null);
    }


    public int getNumberOfNewWords(int totalLessonWords) {
        int numberOfNewWords;
        Cursor cursor=null;
        try{
            String[] projection = {WordsUserInfo.COLUMN_ID};
            String selection = "( " + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_NOTINTRODUCED + ") AND ("
                    + WordsUserInfo.COLUMN_WORDLEVEL + "!=" + WordsUserInfo.WORDLEVEL_WORDLEARNED + ")";
            cursor = context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection,
                    selection, null, null);
            numberOfNewWords=totalLessonWords - cursor.getCount();
            selection = "( " + WordsUserInfo.COLUMN_WORDLEVEL + "=" + WordsUserInfo.WORDLEVEL_NOTINTRODUCED + ")";
            cursor = context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection,
                    selection, null, null);
            if (cursor.getCount()<numberOfNewWords){numberOfNewWords=cursor.getCount();}
        }
        finally {
            cursor.close();
        }

        return numberOfNewWords;
    }

    public Cursor getWordIDs(String wordLevel){
        String[] projection = {WordsUserInfo.COLUMN_ID};
        String selection =WordsUserInfo.COLUMN_WORDLEVEL + "=" + wordLevel;
        return context.getContentResolver().query(WordsUserInfo.CONTENT_URI, projection, selection, null, null);
    }

    //Use the following method to change the first entry in the database to its original form.
    //This method can be deleted once the program is fully functional.
    public void fixEntry() {
        ContentValues values = new ContentValues();
        values.put(WordsEntry.COLUMN_WORD, "prestidigitation");
        values.put(WordsEntry.COLUMN_MEANING, "A performance of or skill in performing magic or conjuring tricks with the hands");
        values.put(WordsEntry.COLUMN_EXAMPLE, "");
        values.put(WordsEntry.COLUMN_ORIGIN, "1");
        values.put(WordsEntry.COLUMN_AUDIOFILE, "prestidigitation");
        context.getContentResolver().update(Uri.withAppendedPath(WordsEntry.CONTENT_URI, "1"), values, null, null);
    }
}
