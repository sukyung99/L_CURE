package com.example.l_cure;

import java.io.Serializable;

public class Words implements Serializable {
    private int number;
    private String word;
    private String img_name;
    private String img_extension;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getImg_extension() {
        return img_extension;
    }

    public void setImg_extension(String img_extension) {
        this.img_extension = img_extension;
    }

}
