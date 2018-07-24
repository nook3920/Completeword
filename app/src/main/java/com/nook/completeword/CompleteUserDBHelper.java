package com.nook.completeword;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CompleteUserDBHelper extends SQLiteOpenHelper  {

    private SQLiteDatabase db;
    Cursor c = null;

    public CompleteUserDBHelper(Context context){
        super(context, "complete_user.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(int i = 0; i<=9;i++){
            String create_table = "CREATE TABLE BEST_COMPLETE_TH0"+ i +"('ID' INTEGER NOT NULL,'SENSEGROUP' VARCHAR(1000) NOT NULL,'FREQ' INTEGER NOT NULL DEFAULT 0,PRIMARY KEY ('ID' ASC))";
            sqLiteDatabase.execSQL(create_table);
        }
        for(int i = 10; i <= 52; i++){
            String create_table = "CREATE TABLE BEST_COMPLETE_TH"+ i +"('ID' INTEGER NOT NULL,'SENSEGROUP' VARCHAR(1000) NOT NULL,'FREQ' INTEGER NOT NULL DEFAULT 0,PRIMARY KEY ('ID' ASC))";
            sqLiteDatabase.execSQL(create_table);
        }
        Log.d("SQL", "DB Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<String> getUserComplete(String s){
        db = this.getWritableDatabase();
        ArrayList<String> listWord = new ArrayList<>();
        String sql = "SELECT DISTINCT SENSEGROUP FROM BEST_COMPLETE_"+ LangCheck.getDBByKeyTH(s) + " WHERE SENSEGROUP LIKE '" + s + "%' ORDER BY FREQ DESC LIMIT 50";

        c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            listWord.add(c.getString(0));
            c.moveToNext();
        }
        db.close();
        return  listWord;
    }

    public void updateUserComplete(String s, String wComplete){
        db = this.getWritableDatabase();
        String sql = "";
        String checkNum = "SELECT COUNT(*) AS NUM FROM BEST_COMPLETE_" + LangCheck.getDBByKeyTH(s)
                + " WHERE SENSEGROUP = '" + wComplete + "'";
        c = db.rawQuery(checkNum,null);
        c.moveToFirst();
        Log.d("NUM", String.valueOf(c.getInt(0)));
        //Toast.makeText(c.getInt(0), Toast.LENGTH_SHORT);
        if(c.getInt(0) > 0){
            sql = "UPDATE BEST_COMPLETE_" + LangCheck.getDBByKeyTH(s)
                    + " SET FREQ = (FREQ+1) WHERE SENSEGROUP = '" + wComplete + "'";
        }else{
            sql = "INSERT INTO BEST_COMPLETE_" + LangCheck.getDBByKeyTH(s)
                    + " ('SENSEGROUP','FREQ') VALUES('" + wComplete + "', 1)";

        }
        Log.d("SQL2",sql);
        c = db.rawQuery(sql, null);

    }


}
