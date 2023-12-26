package com.java.mahongbo.signup;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_USERDATA="create table User ("
            + "id integer primary key autoincrement,"
            +"name text,"
            +"password text)";
    public static final String CREATE_COLLECTIONNEWS = "create table Collection_News ("
            + "id integer primary key autoincrement,"
            + "news_title text,"
            + "news_date text,"
            + "news_author text,"
            + "news_content text)";
    public static final String CREATE_READNEWS = "create table Read_News ("
            + "id integer primary key autoincrement,"
            + "news_title text,"
            + "news_date text,"
            + "news_author text,"
            + "news_content text)";
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
        this.context=context;
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERDATA);
        db.execSQL(CREATE_COLLECTIONNEWS);
        db.execSQL(CREATE_READNEWS);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }

}
