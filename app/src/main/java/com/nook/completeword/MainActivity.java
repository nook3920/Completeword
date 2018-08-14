package com.nook.completeword;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button speak;
    public android.support.v7.widget.GridLayout gridContain;
    private TextView outPut;
    private static ArrayList<String> result;

    static {
        result = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        speak = findViewById(R.id.speak);
        outPut = findViewById(R.id.outPut);
        gridContain = findViewById(R.id.gridContainBT);
        outPut.setText("");
        editText.setText("");
        editText.addTextChangedListener(myTextWatcher);
        CompleteSystemDbAccess a = new CompleteSystemDbAccess(getApplicationContext());
        a.open();
        ArrayList<String> s = a.getComplete("การ");
        String aa = "";
        for(String b : result)
            aa += b;
        outPut.setText(aa);

        a.close();

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
                    CompleteSystemDbAccess aa = new CompleteSystemDbAccess(getApplicationContext());
                    aa.open();
                    ArrayList<String> ss = aa.getComplete(editText.getText().toString());
                    gridContain.removeAllViews();
                    MakeButton(ss);
                    aa.close();
                }
                else if((result.size() >= 1 && editText.getText().length() == 0)){
                    PredicSystemDbAccess bb = new PredicSystemDbAccess(getApplicationContext());
                    bb.open();
                    String s = result.get(result.size()-1);
                    ArrayList<String> zz = bb.getPredict(s);
                    gridContain.removeAllViews();
                    MakeButton(zz);
                    bb.close();
                }else{
                    gridContain.removeAllViews();
                }
            }
        };
    }
    private void MakeButton(ArrayList<String> completeWorld){
        for(String s : completeWorld){
            final Button bt = (Button) getLayoutInflater().inflate(R.layout.bt_layout, null);
            bt.setText(s);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateText(bt.getText().toString());
                }
            });

            gridContain.addView(bt);
        }
    }

    private void updateText(String s){
        result.add(s);
        String a = "";
        for(String b : result)
            a += b;
        outPut.setText(a);
        gridContain.removeAllViews();
        editText.setText("");
    }
    public void delWord(View view){
        if(result.size() > 0){
            result.remove(result.size()-1);
            String a = "";
            for(String b : result)
                a += b;
            outPut.setText(a);
            gridContain.removeAllViews();
        }
        if((result.size() >= 1 && editText.getText().length() == 0)){
            PredicSystemDbAccess bb = new PredicSystemDbAccess(getApplicationContext());
            bb.open();
            String s = result.get(result.size()-1);
            ArrayList<String> zz = bb.getPredict(s);
            gridContain.removeAllViews();
            MakeButton(zz);
        }
    }
    public void speak(View view){
        if(result.size() > 0){
            String a = "";
            for(String b : result)
                a += b;
            MyTTS.getInstance(getApplicationContext()).speak(a);
        }
    }


}
