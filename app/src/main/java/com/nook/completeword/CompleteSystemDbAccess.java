package com.nook.completeword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompleteSystemDbAccess {
    SQLiteDatabase db;
    private CompleteSystemDbHelper openHelper;
    Cursor c = null;

    public CompleteSystemDbAccess(Context context){
        openHelper = new CompleteSystemDbHelper(context);
    }

    public void open(){
        db = openHelper.getWritableDatabase();
    }

    public void close(){
        openHelper.close();
        c.close();
    }

    public ArrayList<String> getComplete(String s,ArrayList<String> ss){
        ArrayList<String> listWord = new ArrayList<>();
        String sql = "SELECT DISTINCT SENSEGROUP FROM BEST_COMPLETE_" + LangCheck.getDBByKeyTH(s)
					+ " WHERE SENSEGROUP LIKE '" + s + "%' AND SENSEGROUP NOT IN('" + TextUtils.join(", ", ss)
					+ "') ORDER BY FREQ DESC LIMIT 50";
        c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            listWord.add(c.getString(0));
            c.moveToNext();
        }

        return  listWord;
    }
}
