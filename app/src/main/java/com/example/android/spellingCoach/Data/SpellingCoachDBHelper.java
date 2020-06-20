package com.example.android.spellingCoach.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.spellingCoach.Data.SpellingCoachContract.*;
/**
 * Created by byilm on 4/23/2017.
 */

public class SpellingCoachDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME="spellingCoach.db";

    public SpellingCoachDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WORDS="CREATE TABLE "+ WordsEntry.TABLE_NAME+"("
                                                + WordsEntry._ID                +   " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + WordsEntry.COLUMN_WORD        +   " TEXT NOT NULL, "
                                                + WordsEntry.COLUMN_MEANING     +   " TEXT, "
                                                + WordsEntry.COLUMN_EXAMPLE     +   " TEXT, "
                                                + WordsEntry.COLUMN_ORIGIN      +   " INTEGER, "
                                                + WordsEntry.COLUMN_AUDIOFILE   +   " TEXT)";

        String SQL_CREATE_ORIGINS="CREATE TABLE "+ OriginsEntry.TABLE_NAME+"("
                                                + OriginsEntry._ID              +   " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + WordsEntry.COLUMN_ORIGIN      +   " TEXT)";

        String SQL_CREATE_WORDSUSERINFO="CREATE TABLE "+ WordsUserInfo.TABLE_NAME+"("
                                                + WordsUserInfo.COLUMN_ID                   +       " INTEGER, "
                                                + WordsUserInfo.COLUMN_WORDLEVEL            +       " INTEGER, "
                                                + WordsUserInfo.COLUMN_LATESTATTEMPT        +       " INTEGER, "
                                                + WordsUserInfo.COLUMN_NUMBERTRIED          +       " INTEGER, "
                                                + WordsUserInfo.COLUMN_NUMBERCORRECT        +       " INTEGER, "
                                                + WordsUserInfo.COLUMN_NUMBERLEVELCORRECT   +       " INTEGER)";

        db.execSQL(SQL_CREATE_WORDS);
        db.execSQL(SQL_CREATE_ORIGINS);
        db.execSQL(SQL_CREATE_WORDSUSERINFO);

    }

    @Override
    //TODO:Need to correctly implement onUpgrade method.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES="DROP TABLE "+WordsEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
