package com.nook.completeword;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PredicUserDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    Cursor c = null;

    public PredicUserDBHelper(Context context){
        super(context, "predic_user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(int i = 0; i<=9;i++){
            String create_table = "CREATE TABLE IF NOT EXISTS BEST_PREDICT_TH0"+ i +" ('ID' INTEGER NOT NULL,'SENSEGROUP' VARCHAR(1000) NOT NULL,'MEMBERS' VARCHAR(1000) NOT NULL,'FREQ' INTEGER NOT NULL DEFAULT 0,PRIMARY KEY ('ID' ASC))";
            sqLiteDatabase.execSQL(create_table);

        }
        for(int i = 10; i<=52;i++){
            String create_table = "CREATE TABLE IF NOT EXISTS BEST_PREDICT_TH"+ i +" ('ID' INTEGER NOT NULL,'SENSEGROUP' VARCHAR(1000) NOT NULL,'MEMBERS' VARCHAR(1000) NOT NULL,'FREQ' INTEGER NOT NULL DEFAULT 0,PRIMARY KEY ('ID' ASC))";
            sqLiteDatabase.execSQL(create_table);
        }
        Log.d("SQL", "DB Created");
    }
    public ArrayList<String> getUserPredic(String s){
        db = this.getWritableDatabase();
        ArrayList<String> listword = new ArrayList<>();
        String sql = "SELECT DISTINCT MEMBERS FROM BEST_PREDICT_" + LangCheck.getDBByKeyTH(s) + " WHERE SENSEGROUP LIKE '" + s + "' ORDER BY FREQ DESC LIMIT 50";
        c = db.rawQuery(sql, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            listword.add(c.getString(0));
            c.moveToFirst();
        }
        db.close();
        return listword;
    }

    public void updateUserPredic(String s, String wPredict){
        db = this.getWritableDatabase();
        String sql = "";
        String checkNum = "SELECT COUNT(*) AS NUM FROM BEST_PREDICT_" + LangCheck.getDBByKeyTH(s)
					+ " WHERE SENSEGROUP = '" + s + "' AND MEMBERS = '" + wPredict + "'";
        c = db.rawQuery(checkNum, null);
        c.moveToFirst();
        Log.d("NUM",String.valueOf(c.getInt(0)));
        if(c.getInt(0) > 0){
            sql = "UPDATE BEST_PREDICT_" + LangCheck.getDBByKeyTH(s) + " SET FREQ = (FREQ+1)  WHERE SENSEGROUP = '" + s + "' AND MEMBERS = '" + wPredict + "'";
        }else{
            sql = "INSERT INTO BEST_PREDICT_" + LangCheck.getDBByKeyTH(s) + " ('SENSEGROUP','MEMBERS','FREQ') VALUES('" + s + "', '" + wPredict + "', 1)";
        }
        Log.d("SQL", sql);
        c = db.rawQuery(sql,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
