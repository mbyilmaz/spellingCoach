package com.example.android.spellingCoach;


import com.example.android.spellingCoach.Data.SpellingCoachContract.*;
/**
 * Created by byilm on 4/13/2017.
 * This class holds the spelling word and the corresponding information.
 */
//TODO: Add Grade to words table. This can be used to pick words with different difficulties.
public class SpellWord {
    private int word_id;
    private String word;
    private String meaning;
    private String example;
    private String origin;
    //TODO It may be better to store the resource id of the audioFile instead.
    private String audioFile;
    private int numberOfSpells;
    private int numberOfCorrectSpells;
    private int numberOfLevelCorrect;
    private String wordLevel=WordsUserInfo.WORDLEVEL_NOTINTRODUCED;



    public SpellWord(String word) {
        this.word=word;
        numberOfSpells=0;
        numberOfCorrectSpells=0;
    }
    public SpellWord(String word,String meaning, String example, String origin, String audioFile) {
        this.word=word;
        this.meaning=meaning;
        this.example=example;
        this.origin=origin;
        this.audioFile=audioFile;
        numberOfSpells=0;
        numberOfCorrectSpells=0;
    }
    public SpellWord(int word_id,String word,String meaning, String example, String origin, String audioFile) {
        this.word_id=word_id;
        this.word=word;
        this.meaning=meaning;
        this.example=example;
        this.origin=origin;
        this.audioFile=audioFile;
        numberOfSpells=0;
        numberOfCorrectSpells=0;
    }
    public SpellWord(int word_id,String word,String meaning, String example, String origin, String audioFile, String wordLevel) {
        this.word_id=word_id;
        this.word=word;
        this.meaning=meaning;
        this.example=example;
        this.origin=origin;
        this.audioFile=audioFile;
        this.wordLevel=wordLevel;
        numberOfSpells=0;
        numberOfCorrectSpells=0;
        numberOfLevelCorrect=0;
    }

    public String getWord_id(){return Integer.toString(word_id);}

    public String getWord(){
        return word;
    }
    public void setParameters(String meaning, String example, String origin, String audioFile){
        this.meaning=meaning;
        this.example=example;
        this.origin=origin;
        this.audioFile=audioFile;
    }
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    public String getMeaning() {
        return meaning;
    }

    public void setExample(String example) {
        this.example = example;
    }
    public String getExample() {
        return example;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getOrigin() {
        return origin;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }
    public String getAudioFile() {
        return audioFile;
    }

    public void setWordLevel(String wordLevel){ this.wordLevel=wordLevel;}
    public String getWordLevel(){return wordLevel;}

    public void incrementNumberOfSpells() {
        this.numberOfSpells++;
    }
    public void setNumberOfSpells(int numberOfSpells){this.numberOfSpells=numberOfSpells;}
    public int getNumberOfSpells() {
        return numberOfSpells;
    }

    public void incrementNumberOfCorrectSpells() {this.numberOfCorrectSpells++;}
    public void setNumberOfCorrectSpells(int numberOfCorrectSpells){this.numberOfCorrectSpells=numberOfCorrectSpells;}
    public int getNumberOfCorrectSpells() {
        return numberOfCorrectSpells;
    }

    public void incrementNumberOfLevelCorrect() {this.numberOfLevelCorrect++;}
    public void setNumberOfLevelCorrect(int numberOfLevelCorrect){this.numberOfLevelCorrect=numberOfLevelCorrect;}
    public int getNumberOfLevelCorrect() {
        return numberOfLevelCorrect;
    }

    //TODO: Currently upgrade and downgrade wordLevel are not checking when the last time the word is tried!
    public void upgradeWordLevel(){
        incrementNumberOfSpells();
        incrementNumberOfCorrectSpells();
        switch (wordLevel){
            case WordsUserInfo.WORDLEVEL_NEWWORD:
                wordLevel=WordsUserInfo.WORDLEVEL_LEVEL2;
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL2:
                wordLevel=WordsUserInfo.WORDLEVEL_LEVEL3;
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL3:
                incrementNumberOfLevelCorrect();
                if (numberOfLevelCorrect>=3) {
                    wordLevel=WordsUserInfo.WORDLEVEL_LEVEL4;
                    numberOfLevelCorrect=0;
                }
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL4:
                incrementNumberOfLevelCorrect();
                if (numberOfLevelCorrect>=2) {
                    wordLevel=WordsUserInfo.WORDLEVEL_LEVEL5;
                    numberOfLevelCorrect=0;
                }
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL5:
                incrementNumberOfLevelCorrect();
                if (numberOfLevelCorrect>=2) {
                    wordLevel=WordsUserInfo.WORDLEVEL_WORDLEARNED;
                    numberOfLevelCorrect=0;
                }
                break;
        }
    }
    public void downgradeWordLevel(){
        incrementNumberOfSpells();
        switch (wordLevel){
            case WordsUserInfo.WORDLEVEL_NEWWORD:
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL2:
                wordLevel=WordsUserInfo.WORDLEVEL_NEWWORD;
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL3:
                wordLevel=WordsUserInfo.WORDLEVEL_LEVEL2;
                numberOfLevelCorrect=0;
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL4:
                wordLevel=WordsUserInfo.WORDLEVEL_LEVEL3;
                numberOfLevelCorrect=0;
                break;
            case WordsUserInfo.WORDLEVEL_LEVEL5:
                wordLevel=WordsUserInfo.WORDLEVEL_LEVEL4;
                numberOfLevelCorrect=0;
                break;
        }
    }

}
