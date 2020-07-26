package com.example.learn;

public class WordMeaning {
    String word, meaning;
    String date;
    public WordMeaning(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public WordMeaning(String date) {
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
