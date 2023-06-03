package com.saurabh.pussgrc;

public class Question {
    private String question;
    private int[] score;

    public Question(String question, int[] score) {
        this.question = question;
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public int[] getScore() {
        return score;
    }
    public int getScores() {
        for (int i = 0; i < score.length; i++) {
            if (score[i] == 1) {
                return i;
            }
        }
        return -1; // Return -1 if no score of 1 is found
    }
}
