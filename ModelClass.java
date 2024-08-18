package com.example.quizapp;

public class ModelClass {
    String Question;
    String A;
    String B;
    String C;
    String D;
    String Answer;

    public ModelClass(String question, String a, String b, String c, String d, String answer) {
        Question = question;
        A = a;
        B = b;
        C = c;
        D = d;
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
