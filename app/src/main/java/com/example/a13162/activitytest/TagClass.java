package com.example.a13162.activitytest;

public class TagClass{
    private String tagtitle;
    private String tagtext;

    public TagClass(String title,String text){
        this.tagtitle=title;
        this.tagtext=text;
    }
    public String getTitle(){
        return tagtitle;
    }
    public String getText(){
        return tagtext;
    }
}
