package com.example.a13162.activitytest;


import java.util.ArrayList;

public class Data{
    private static boolean active =false;
    private static ArrayList<String> tag=new ArrayList<String>();
    public static ArrayList getTag(){
        return tag;
    }
    public static void addTag(String string){
        tag.add(string);

    }
    public static int getIsactive() {
        if(Data.active==false){
            return 0;
        }else{
            return 1;
        }
    }

    public static void change() {
        if(Data.active==false){
            Data.active=true;
        }else{
            Data.active=false;
        }

    }
}

