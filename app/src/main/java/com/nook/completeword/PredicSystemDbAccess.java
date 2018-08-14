package com.nook.completeword;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PredicSystemDbAccess {
    SQLiteDatabase db;
    private PredicSystemDbHelper openHelper;
    Cursor c = null;

    public PredicSystemDbAccess(Context context){
        openHelper = new PredicSystemDbHelper(context);
    }
    public void open(){
        db = openHelper.getWritableDatabase();
    }
    public void close(){
        openHelper.close();
        c.close();
    }
    public ArrayList<String> getPredict(String s){
        ArrayList<String> listWord = new ArrayList<>();
        String sql = "SELECT DISTINCT MEMBERS FROM BEST_PREDICT_" + LangCheck.getDBByKeyTH(s)+ " WHERE SENSEGROUP LIKE '" + s + "' ORDER BY FREQ DESC LIMIT 6";
        c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            listWord.add(c.getString(0));
            c.moveToNext();
        }

        return  listWord;
    }



}
