package com.example.android.spellingCoach;

/**
 * Created by byilm on 4/16/2017.
 */


public class Speller {
    private SpellWord spellWord=null;
    private String word;
    private String spelledWord;
    private int position=0;
    private final char[] CHARACTERS={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
        'P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final String filledCircle="\u2022";
    private char[] previousLetters={'0','0','0','0'};

    public Speller(SpellWord spellWord){
        this.spellWord=spellWord;
        this.word= spellWord.getWord().toUpperCase();
        spelledWord="";
    }

    public Speller(String word) {
        this.word = word.toUpperCase();
        spelledWord="";
    }

    private int getRandomPos(int numberOfPositions) {
        return (int )(Math.floor(Math.random() * numberOfPositions));
    }

    //Provides for letters for the activity to display. The user will pick one letter for the current position of the word.
    //One of the letters is the correct letter. The other three are randomly picked.
    public char[] getLetters(){
        char[] letters={'0','0','0','0'};
        int start=0;
        if (position<word.length()) {
            letters[0]=word.charAt(position);
            start=1;
        }
        for(int i=start;i<4;i++) {
            while (letters[i]=='0'){
                letters[i]=CHARACTERS[getRandomPos(26)];
                for(int j=0;j<i;j++){
                    if (letters[i]==letters[j]){
                        letters[i]='0';
                        break;
                    }
                }
            }
        }
        return rearrangeLetters(letters);
    }

    public char[] rearrangeLetters(char[] letters){

        //Shuffle Correct Letter Position Randomly
        int correctPos=getRandomPos(4);
        char dummy=letters[correctPos];
        letters[correctPos]=letters[0];
        letters[0]=dummy;

        //Check for previous letters arrangement and keep the same letters at the same place.
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (letters[j]==previousLetters[i]){
                    dummy=letters[j];
                    letters[j]=letters[i];
                    letters[i]=dummy;
                }
            }
        }
        previousLetters=letters;
        return letters;
    }

    public void enterLetter(String letter){
        spelledWord=spelledWord+letter;
        position++;
    }

    public String getSpelledWord(boolean wordHidden){
        String returnWord="";
        if (spellWord!=null){
            if(Integer.parseInt(spellWord.getWordLevel())>3 && wordHidden){
                for(int i=0;i<spelledWord.length();i++){
                    returnWord+=filledCircle;
                }
            }
            else{
                returnWord=spelledWord;
            }
        }
        else {returnWord=spelledWord;}
        return returnWord;
    }


    //Checks if the letter picked matches the correct letter of the word at the current position
    public boolean checkSpelling(){
        boolean result=(word.equals(spelledWord));
        spelledWord="";
        position=0;
        return result;
    }
}