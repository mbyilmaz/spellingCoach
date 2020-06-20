package com.example.android.spellingCoach.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.android.spellingCoach.Data.SpellingCoachContract.*;

/**
 * Created by byilm on 4/26/2017.
 */

public class WordProvider extends ContentProvider {
    /** Tag for the log messages */
    public static final String LOG_TAG = WordProvider.class.getSimpleName();
    private SpellingCoachDBHelper mDbHelper;
    private static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS=1;
    private static final int WORD_ID=2;
    private static final int ORIGINS=3;
    private static final int ORIGINS_ID=4;
    private static final int WORDSUSER=5;
    private static final int WORDSUSER_ID=6;

    static {
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_WORDS,WORDS);
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_WORDS+"/#",WORD_ID);
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_ORIGINS,ORIGINS);
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_ORIGINS+"/#",ORIGINS_ID);
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_WORDSUSER,WORDSUSER);
        sUriMatcher.addURI(SpellingCoachContract.CONTENT_AUTHORITY,SpellingCoachContract.PATH_WORDSUSER+"/#",WORDSUSER_ID);
    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new SpellingCoachDBHelper(this.getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor=null;
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        int match=sUriMatcher.match(uri);

        switch (match) {
            case WORDS:
                cursor = db.query(
                        WordsEntry.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            case WORD_ID:
                selection = WordsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(
                        WordsEntry.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            case ORIGINS:
                cursor = db.query(
                        OriginsEntry.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            case ORIGINS_ID:
                selection = OriginsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(
                        OriginsEntry.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            case WORDSUSER:
                cursor = db.query(
                        WordsUserInfo.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            case WORDSUSER_ID:
                selection = WordsUserInfo.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(
                        WordsUserInfo.TABLE_NAME,  //Table name
                        projection,                   //columns to be returned (all if null) should be a String array of column names
                        selection,         //columns for the where clause. should be a String array
                        selectionArgs,                //Values corresponding to each where clause
                        null,                   //don't group the rows
                        null,                   //don't filter the row groups
                        sortOrder                    //The sort order
                );
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int updated=0;
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        int match=sUriMatcher.match(uri);

        switch (match) {
            case WORD_ID:
                selection = WordsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                updated=db.update(WordsEntry.TABLE_NAME,contentValues,selection,selectionArgs);
                break;
            case WORDSUSER_ID:
                selection = WordsUserInfo.COLUMN_ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                updated=db.update(WordsUserInfo.TABLE_NAME,contentValues,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return updated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }

}

