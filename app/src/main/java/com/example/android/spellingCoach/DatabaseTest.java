package com.example.android.spellingCoach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.spellingCoach.Data.SpellingCoachContract.*;
import com.example.android.spellingCoach.Data.SpellingCoachDBHelper;
import com.example.android.spellingCoach.Data.SpellingCoachDBManager;

import java.util.ArrayList;

public class DatabaseTest extends AppCompatActivity {
    SpellingCoachDBHelper mDbHelper;
    SpellingCoachDBManager mDBM;
    SQLiteDatabase db;
    ContentValues values;
    TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        mDbHelper = new SpellingCoachDBHelper(this);
        db = mDbHelper.getWritableDatabase();
        mDBM=new SpellingCoachDBManager(this);
        displayView = (TextView) findViewById(R.id.dbtestView);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + WordsEntry.TABLE_NAME, null);
        try {
            TextView displayView = (TextView) findViewById(R.id.dbtestView);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    public void createDB(View view) {
        Intent i = new Intent(DatabaseTest.this, WordDataManagement.class);
        startActivity(i);
    }

    public void deleteDB(View view) {

        //db.delete(WordsEntry.TABLE_NAME, null, null);
        //displayView.setText("Number of words that are introduced:" + Integer.toString(mDBM.getNumberOfNewWords()));

    }

    public void populateDB(View view) {
        ArrayList<SpellWord> words = new ArrayList<SpellWord>();
//        words.add(new SpellWord("meticulous", "Marked by extreme or excessive care in the consideration or treatment of details ", null, WordsEntry.ORIGIN_LATIN, "meticulous"));
//        words.add(new SpellWord("interrupt", "To stop or hinder by breaking in ", null, WordsEntry.ORIGIN_LATIN, "interrupt"));
//        words.add(new SpellWord("animosity", "A strong feeling of dislike or hatred", null, WordsEntry.ORIGIN_LATIN, "animosity"));
//        words.add(new SpellWord("subterfuge", "Deception by artifice or stratagem in order to conceal, escape, or evade", null, WordsEntry.ORIGIN_LATIN, "subterfuge"));
//        words.add(new SpellWord("magnanimous", "Showing or suggesting a lofty and courageous spirit", null, WordsEntry.ORIGIN_LATIN, "magnanimous"));
//        words.add(new SpellWord("mediocre", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_LATIN, "mediocre"));
//        words.add(new SpellWord("albatross", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ARABIC, "albatross"));
//        words.add(new SpellWord("monsoon", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ARABIC, "monsoon"));
//        words.add(new SpellWord("saffron", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ARABIC, "saffron"));
//        words.add(new SpellWord("gunnysack", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ASIANLANGUAGES, "gunnysack"));
//        words.add(new SpellWord("cummerbund", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ASIANLANGUAGES, "cummerbund"));
//        words.add(new SpellWord("dungaree", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ASIANLANGUAGES, "dungaree"));
//        words.add(new SpellWord("prestidigitation", "A performance of or skill in performing magic or conjuring tricks with the hands", null, WordsEntry.ORIGIN_FRENCH, "prestidigitation"));
//        words.add(new SpellWord("entourage", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_FRENCH, "entourage"));
//        words.add(new SpellWord("croquette", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_FRENCH, "croquette"));
//        words.add(new SpellWord("crochet", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_FRENCH, "crochet"));
//        words.add(new SpellWord("bureaucracy", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_FRENCH, "bureaucracy"));
//        words.add(new SpellWord("doctrinaire", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_FRENCH, "doctrinaire"));
//        words.add(new SpellWord("jeremiad", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_EPONYMS, "jeremiad"));
//        words.add(new SpellWord("bandersnatch", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_EPONYMS, "bandersnatch"));
//        words.add(new SpellWord("poinsettia", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_EPONYMS, "poinsettia"));
//        words.add(new SpellWord("schnauzer", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GERMAN, "schnauzer"));
//        words.add(new SpellWord("poltergeist", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GERMAN, "poltergeist"));
//        words.add(new SpellWord("dachshund", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GERMAN, "dachshund"));
//        words.add(new SpellWord("feldspar", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GERMAN, "feldspar"));
//        words.add(new SpellWord("dachshund", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GERMAN, "dachshund"));
//        words.add(new SpellWord("cossack", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SLAVICLANGUAGES, "cossack"));
//        words.add(new SpellWord("mammoth", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SLAVICLANGUAGES, "mammoth"));
//        words.add(new SpellWord("Bolshevik", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SLAVICLANGUAGES, "Bolshevik"));
//        words.add(new SpellWord("samovar", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SLAVICLANGUAGES, "samovar"));
//        words.add(new SpellWord("daffodil", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_DUTCH, "daffodil"));
//        words.add(new SpellWord("wintergreen", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_DUTCH, "wintergreen"));
//        words.add(new SpellWord("caboose", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_DUTCH, "caboose"));
//        words.add(new SpellWord("furlough", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_DUTCH, "furlough"));
//        words.add(new SpellWord("isinglass", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_DUTCH, "isinglass"));
//        words.add(new SpellWord("hustings", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "hustings"));
//        words.add(new SpellWord("earthenware", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "earthenware"));
//        words.add(new SpellWord("broadleaf", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "broadleaf"));
//        words.add(new SpellWord("roughhewn", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "roughhewn"));
//        words.add(new SpellWord("knavery", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "knavery"));
//        words.add(new SpellWord("nightingale", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "nightingale"));
//        words.add(new SpellWord("bequeath", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_OLDENGLISH, "bequeath"));
//        words.add(new SpellWord("iguana", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_NEWWORLDLANGUAGES, "iguana"));
//        words.add(new SpellWord("toboggan", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_NEWWORLDLANGUAGES, "toboggan"));
//        words.add(new SpellWord("totem", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_NEWWORLDLANGUAGES, "totem"));
//        words.add(new SpellWord("skunk", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_NEWWORLDLANGUAGES, "skunk"));
//        words.add(new SpellWord("shogun", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_JAPANESE, "shogun"));
//        words.add(new SpellWord("hibachi", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_JAPANESE, "hibachi"));
//        words.add(new SpellWord("satori", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_JAPANESE, "satori"));
//        words.add(new SpellWord("lethargy", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "lethargy"));
//        words.add(new SpellWord("megalopolis", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "megalopolis"));
//        words.add(new SpellWord("asterisk", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "asterisk"));
//        words.add(new SpellWord("homonym", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "homonym"));
//        words.add(new SpellWord("hydraulic", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "hydraulic"));
//        words.add(new SpellWord("synchronous", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "synchronous"));
//        words.add(new SpellWord("apostrophe", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_GREEK, "apostrophe"));
//        words.add(new SpellWord("staccato", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "staccato"));
//        words.add(new SpellWord("incognito", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "incognito"));
//        words.add(new SpellWord("extravaganza", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "extravaganza"));
//        words.add(new SpellWord("portfolio", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "portfolio"));
//        words.add(new SpellWord("ballerina", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "ballerina"));
//        words.add(new SpellWord("pesto", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_ITALIAN, "pesto"));
//        words.add(new SpellWord("chimichanga", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SPANISH, "chimichanga"));
//        words.add(new SpellWord("hacienda", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SPANISH, "hacienda"));
//        words.add(new SpellWord("peccadillo", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SPANISH, "peccadillo"));
//        words.add(new SpellWord("castanets", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SPANISH, "castanets"));
//        words.add(new SpellWord("desperado", "Of moderate or low quality, value, ability, or performance", null, WordsEntry.ORIGIN_SPANISH, "desperado"));
        words.add(new SpellWord("tonic", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "tonic"));
        words.add(new SpellWord("enlisted", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "enlisted"));
        words.add(new SpellWord("tomes", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "tome"));
        words.add(new SpellWord("levied", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "levy"));
        words.add(new SpellWord("bookmobile", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "bookmobile"));
        words.add(new SpellWord("nursery", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "nursery"));
        words.add(new SpellWord("shortage", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "shortage"));
        words.add(new SpellWord("precise", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "precise"));
        words.add(new SpellWord("roundabout", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "roundabout"));
        words.add(new SpellWord("handiwork", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "handiwork"));
        words.add(new SpellWord("balloonist", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "balloonist"));
        words.add(new SpellWord("improvise", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "improvise"));
        words.add(new SpellWord("altitude", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "altitude"));
        words.add(new SpellWord("sparsely", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "sparsely"));
        words.add(new SpellWord("musician", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "musician"));
        words.add(new SpellWord("practically", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "practically"));
        words.add(new SpellWord("testament", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "testament"));
        words.add(new SpellWord("simulate", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "simulate"));
        words.add(new SpellWord("disengage", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "disengage"));
        words.add(new SpellWord("mohawk", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "mohawk"));
        words.add(new SpellWord("exterior", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "exterior"));
        words.add(new SpellWord("scenery", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "scenery"));
        words.add(new SpellWord("opponent", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "opponent"));
        words.add(new SpellWord("allowance", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "allowance"));
        words.add(new SpellWord("committee", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "committee"));
        words.add(new SpellWord("bulletin", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "bulletin"));
        words.add(new SpellWord("hoarsely", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "hoarsely"));
        words.add(new SpellWord("automated", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "automated"));
        words.add(new SpellWord("subdivision", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "subdivision"));
        words.add(new SpellWord("heralded", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "heralded"));
        words.add(new SpellWord("appointed", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "appointed"));
        words.add(new SpellWord("regents", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "regents"));
        words.add(new SpellWord("infraction", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "infraction"));
        words.add(new SpellWord("pacific", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "pacific"));
        words.add(new SpellWord("cypher", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "cypher"));
        words.add(new SpellWord("administration", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "administration"));
        words.add(new SpellWord("geometry", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "geometry"));
        words.add(new SpellWord("consecutive", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "consecutive"));
        words.add(new SpellWord("scavenger", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "scavenger"));
        words.add(new SpellWord("defensiveness", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "defensiveness"));
        words.add(new SpellWord("vegetation", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "vegetation"));
        words.add(new SpellWord("sconces", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "sconces"));
        words.add(new SpellWord("cyclops", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "cyclops"));
        words.add(new SpellWord("aerospace", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "aerospace"));
        words.add(new SpellWord("correctional", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "correctional"));
        words.add(new SpellWord("segregation", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "segregation"));
        words.add(new SpellWord("instantaneous", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "instantaneous"));
        words.add(new SpellWord("nausea", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "nausea"));
        words.add(new SpellWord("chesapeake", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "chesapeake"));
        words.add(new SpellWord("atoll", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "atoll"));
        words.add(new SpellWord("miracle", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "miracle"));
        words.add(new SpellWord("callous", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "callous"));
        words.add(new SpellWord("grandiose", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "grandiose"));
        words.add(new SpellWord("dexterity", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "dexterity"));
        words.add(new SpellWord("barrette", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "barrette"));
        words.add(new SpellWord("cordial", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "cordial"));
        words.add(new SpellWord("alacrity", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "alacrity"));
        words.add(new SpellWord("marmalade", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "marmalade"));
        words.add(new SpellWord("orchids", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "orchid"));
        words.add(new SpellWord("despondency", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "despondency"));
        words.add(new SpellWord("composure", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "composure"));
        words.add(new SpellWord("jauntily", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "jauntily"));
        words.add(new SpellWord("hispaniola", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "hispaniola"));
        words.add(new SpellWord("quantum", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "quantum"));
        words.add(new SpellWord("calamine", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "calamine"));
        words.add(new SpellWord("exasperation", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "exasperation"));
        words.add(new SpellWord("sophomore", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "sophomore"));
        words.add(new SpellWord("reprobate", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "reprobate"));
        words.add(new SpellWord("annoyance", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "annoyance"));
        words.add(new SpellWord("paralysis", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "paralysis"));
        words.add(new SpellWord("chimpanzee", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "chimpanzee"));
        words.add(new SpellWord("peroxide", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "peroxide"));
        words.add(new SpellWord("amputation", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "amputation"));
        words.add(new SpellWord("conspiracy", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "conspiracy"));
        words.add(new SpellWord("sinewy", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "sinewy"));
        words.add(new SpellWord("flabbergasted", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "flabbergasted"));
        words.add(new SpellWord("boisterously", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "boisterously"));
        words.add(new SpellWord("linoleum", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "linoleum"));
        words.add(new SpellWord("tapirs", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "tapir"));
        words.add(new SpellWord("repugnance", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "repugnance"));
        words.add(new SpellWord("impeccable", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "impeccable"));
        words.add(new SpellWord("contagious", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "contagious"));
        words.add(new SpellWord("stevedores", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "stevedore"));
        words.add(new SpellWord("commendable", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "commendable"));
        words.add(new SpellWord("incessant", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "incessant"));
        words.add(new SpellWord("ricochet", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "ricochet"));
        words.add(new SpellWord("tribulations", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "tribulation"));
        words.add(new SpellWord("azalea", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "azalea"));
        words.add(new SpellWord("fluorescent", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "fluorescent"));
        words.add(new SpellWord("reservoir", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "reservoir"));
        words.add(new SpellWord("truculently", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "truculently"));
        words.add(new SpellWord("generalissimo", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "generalissimo"));
        words.add(new SpellWord("vociferous", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "vociferous"));
        words.add(new SpellWord("merengue", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "merengue"));
        words.add(new SpellWord("claustrophobic", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "claustrophobic"));
        words.add(new SpellWord("methuselah", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "methuselah"));
        words.add(new SpellWord("entrepreneurs", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "entrepreneurs"));
        words.add(new SpellWord("monsieur", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "monsieur"));
        words.add(new SpellWord("piecederesistance", "Definition", null, WordsEntry.ORIGIN_NOORIGIN, "piece_de_resistance"));

        long wordrowID = -2;
        SpellWord newWord;
        for (int i = 0; i < words.size(); i++) {
            values = new ContentValues();
            newWord = words.get(i);
            values.put(WordsEntry.COLUMN_WORD, newWord.getWord());
            values.put(WordsEntry.COLUMN_MEANING, newWord.getMeaning());
            values.put(WordsEntry.COLUMN_EXAMPLE, newWord.getExample());
            values.put(WordsEntry.COLUMN_ORIGIN, newWord.getOrigin());
            values.put(WordsEntry.COLUMN_AUDIOFILE, newWord.getAudioFile());
            wordrowID = db.insert(WordsEntry.TABLE_NAME, null, values);

            values = new ContentValues();
            values.put(WordsUserInfo.COLUMN_ID, Long.toString(wordrowID));
            values.put(WordsUserInfo.COLUMN_WORDLEVEL,WordsUserInfo.WORDLEVEL_NOTINTRODUCED);
            values.put(WordsUserInfo.COLUMN_LATESTATTEMPT,"0");
            values.put(WordsUserInfo.COLUMN_NUMBERTRIED,"0");
            values.put(WordsUserInfo.COLUMN_NUMBERCORRECT,"0");
            values.put(WordsUserInfo.COLUMN_NUMBERLEVELCORRECT,"0");
            db.insert(WordsUserInfo.TABLE_NAME,null,values);

        }
        long originrowID = -2;
        ArrayList<String> origins = new ArrayList<String>();
        origins.add("Latin");
        origins.add("Arabic");
        origins.add("Asian Languages");
        origins.add("French");
        origins.add("Eponyms");
        origins.add("German");
        origins.add("Slavic Languages");
        origins.add("Dutch");
        origins.add("Old English");
        origins.add("New World Languages");
        origins.add("Japanese");
        origins.add("Greek");
        origins.add("Italian");
        origins.add("Spanish");
        origins.add("No Origin");
        for (int i = 0; i < origins.size(); i++) {
            values = new ContentValues();
            values.put(OriginsEntry.COLUMN_ORIGIN, origins.get(i));
            originrowID = db.insert(OriginsEntry.TABLE_NAME, null, values);
        }




        displayView.setText("Word Row id: " + wordrowID + " Origin Row id: " + originrowID);
    }
}
