package com.example.demo;

public class Vocabulary {
    private String word;
    private String detail;

    public Vocabulary(String word, String detail) {
        this.word = word;
        this.detail = detail;
    }

    public String getWord() {
        return word;
    }
//Tại sao tôi thêm ImageView cho Menu thì lại không chỉnh được kích thước bé như arrow hay volume
    public String getDetail() {
        return detail;
    }
}
