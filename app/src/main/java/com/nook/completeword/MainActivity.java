package com.nook.completeword;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button speak;
    private TextView outPut;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private android.support.constraint.ConstraintLayout tp;
    private static ArrayList<String> result;
    private static ArrayList<String> listWord;
    private static int page;
    static {
        result = new ArrayList<>();
        listWord = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        speak = findViewById(R.id.speak);
        outPut = findViewById(R.id.outPut);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        outPut.setText("");
        editText.setText("");
        page = 0;
        editText.addTextChangedListener(myTextWatcher);

        hideWord();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv1.getText().toString());
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv2.getText().toString());
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv3.getText().toString());
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv4.getText().toString());
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv5.getText().toString());
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(tv6.getText().toString());
            }
        });

        tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv1.getText().toString());
                return true;
            }
        });

        tv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv2.getText().toString());
                return true;
            }
        });

        tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv3.getText().toString());
                return true;
            }
        });

        tv4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv4.getText().toString());
                return true;
            }
        });

        tv5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv5.getText().toString());
                return true;
            }
        });

        tv6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyTTS.getInstance(getApplicationContext()).speak(tv6.getText().toString());
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        result.clear();
        updateword(result);
    }

    private TextWatcher myTextWatcher;
    {
        myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.length() > 0){
                    //ArrayList<String> ss = new ArrayList<>();
                    listWord = getComplete(editText.getText().toString());
                    page = 0;
                    updateword(listWord);
                }
                else if((result.size() >= 1 && editText.getText().length() == 0)){
                    //ArrayList<String> zz = new ArrayList<>();
                    listWord = getPredic(result.get(result.size()-1));
                    page = 0;
                    updateword(listWord);
                }else{
                    hideWord();
                    //do something
                }
            }
        };
    }
    private  ArrayList<String> getPredic(String s){
        ArrayList<String> ss = new ArrayList<>();
        PredicUserDBHelper aa = new PredicUserDBHelper(getApplicationContext());
        ss = aa.getUserPredic(s);
        PredicSystemDbAccess bb = new PredicSystemDbAccess(getApplicationContext());
        bb.open();
        ss = bb.getPredict(s, ss);
        bb.close();
        return ss;
    }

    private ArrayList<String> getComplete(String s){
        ArrayList<String> ss = new ArrayList<>();
        CompleteUserDBHelper aa = new CompleteUserDBHelper(getApplicationContext());
        ss = aa.getUserComplete(s);
        CompleteSystemDbAccess bb = new CompleteSystemDbAccess(getApplicationContext());
        bb.open();
        ss = bb.getComplete(s,ss);
        bb.close();
        return ss;
    }

    private void updateText(String s){
        result.add(s);
        String a = "";
        for(String b : result)
            a += b;
        outPut.setText(a);
        editText.setText("");
        listWord = getPredic(result.get(result.size()-1));
        page = 0;
        updateword(listWord);
    }

    public void delWord(View view){
        if(result.size() > 0){
            result.remove(result.size()-1);
            String a = "";
            for(String b : result)
                a += b;
            outPut.setText(a);
            hideWord();
            //updateword(getPredic(result.get(result.size()-1)));
        }
        if((result.size() >= 1 && editText.getText().length() == 0)){
            listWord = getPredic(result.get(result.size()-1));
            page = 0;
            updateword(listWord);
        }
    }
    public void speak(View view){
        if(result.size() > 0){
            String a = "";
            for(String b : result)
                a += b;
            MyTTS.getInstance(getApplicationContext()).speak(a);
        }
        Toast.makeText(getApplicationContext(),"speak",Toast.LENGTH_LONG);
    }

    public void updateword(ArrayList<String> s){
        if(s.size() > 0){
            if(s.size() >= 1+(page*6)){
                tv1.setVisibility(View.VISIBLE);
               // tv1.setText(s.get(0));
                tv1.setText(s.get(page*6).toString());
            }else {
                tv1.setVisibility(View.GONE);
            }

            if(s.size() >= 2+(page*6)){
                tv2.setVisibility(View.VISIBLE);
                tv2.setText(s.get(page*6+1).toString());
            }else {
                tv2.setVisibility(View.GONE);
            }

            if(s.size() >= 3+(page*6)){
                tv3.setVisibility(View.VISIBLE);
                tv3.setText(s.get(page*6+2).toString());
            }else {
                tv3.setVisibility(View.GONE);
            }

            if(s.size() >= 4+(page*6)){
                tv4.setVisibility(View.VISIBLE);
                tv4.setText(s.get(page*6+3).toString());
            }else{
                tv4.setVisibility(View.GONE);
            }

            if(s.size() >= 5+(page*6)){
                tv5.setVisibility(View.VISIBLE);
                tv5.setText(s.get(page*6+4).toString());
            }else{
                tv5.setVisibility(View.GONE);
            }

            if(s.size() >= 6+(page*6)){
                tv6.setVisibility(View.VISIBLE);
                tv6.setText(s.get(page*6+5).toString());
            }

        }else{
            hideWord();
        }
    }
    public void hideWord(){
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
    }

    public void nextPage(View view){
        if(listWord.size() > (page+1)*6){
            page += 1;
            updateword(listWord);
        }
    }
    public void prevPage(View view){
        if(page > 0){
            page -= 1;
            updateword(listWord);
        }
    }
    public void copyToClipBoard(View view){
        if(result.size() > 0){
            String a = "";
            for(String b : result)
                a += b;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("completeword", a);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(),"copy to clipboard",Toast.LENGTH_LONG);
            Log.d("CB", "copyToClipBoard: ");
        }

    }
    public void delAllWord(View view){
        result.clear();
        editText.setText("");
        outPut.setText("");
        updateword(result);

    }



}
