package com.example.android.spellingCoach.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by byilm on 4/23/2017.
 */

public final class SpellingCoachContract {

    private SpellingCoachContract(){}

        public static final String CONTENT_AUTHORITY = "com.example.android.spellingCoach";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_WORDS = "words";
        public static final String PATH_ORIGINS="origins";
        public static final String PATH_WORDSUSER="wordsuser";

    public static final class WordsEntry implements BaseColumns{
        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WORDS);

        public static final String TABLE_NAME="words";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_WORD="word";
        public static final String COLUMN_MEANING="meaning";
        public static final String COLUMN_EXAMPLE="example";
        public static final String COLUMN_ORIGIN="origin"; //Holds the integer value of word origin as defined by the constants below
        public static final String COLUMN_AUDIOFILE="audioFile";


        /**
         * Possible Values for the origin
         */
        public static final String ORIGIN_LATIN="1";
        public static final String ORIGIN_ARABIC="2";
        public static final String ORIGIN_ASIANLANGUAGES="3";
        public static final String ORIGIN_FRENCH="4";
        public static final String ORIGIN_EPONYMS="5";
        public static final String ORIGIN_GERMAN="6";
        public static final String ORIGIN_SLAVICLANGUAGES="7";
        public static final String ORIGIN_DUTCH="8";
        public static final String ORIGIN_OLDENGLISH="9";
        public static final String ORIGIN_NEWWORLDLANGUAGES="10";
        public static final String ORIGIN_JAPANESE="11";
        public static final String ORIGIN_GREEK="12";
        public static final String ORIGIN_ITALIAN="13";
        public static final String ORIGIN_SPANISH="14";
        public static final String ORIGIN_NOORIGIN="15";
    }

    public static final class OriginsEntry implements BaseColumns{
        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORIGINS);

        public static final String TABLE_NAME="origins";
        public static final String _ID=BaseColumns._ID; //Holds the integer value of word origin
        public static final String COLUMN_ORIGIN="origin"; //Holds the string definition of word origin
    }

    public static final class WordsUserInfo implements BaseColumns{
        public static final Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,PATH_WORDSUSER);

        public static final String TABLE_NAME="wordsuser";
        public static final String COLUMN_ID="wordID";
        public static final String COLUMN_WORDLEVEL="wordLevel";
        public static final String COLUMN_LATESTATTEMPT="latestAttempt";
        public static final String COLUMN_NUMBERTRIED="numberTried";
        public static final String COLUMN_NUMBERCORRECT="numberCorrect";
        public static final String COLUMN_NUMBERLEVELCORRECT="numberLevelCorrect";

        public static final String WORDLEVEL_NOTINTRODUCED="0";
        public static final String WORDLEVEL_NEWWORD="1";
        public static final String WORDLEVEL_LEVEL2="2";
        public static final String WORDLEVEL_LEVEL3="3";
        public static final String WORDLEVEL_LEVEL4="4";
        public static final String WORDLEVEL_LEVEL5="5";
        public static final String WORDLEVEL_WORDLEARNED="6";

    }
}
