package com.notet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
/*
* class use to handler choose color for a backgound item notes*/


public class ChooseColor extends Activity {

    Button btnWhite, btnYellow, btnGreen, btnBlue;
    LinearLayout llAddNote;
    String strColor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        //get controls
        llAddNote = (LinearLayout) findViewById(R.id.llAddNote);
        btnWhite = (Button) findViewById(R.id.btnWhite);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnBlue = (Button) findViewById(R.id.btnBlue);

        // set onclicklistener
        btnWhite.setOnClickListener(new myOnclickListener());
        btnYellow.setOnClickListener(new myOnclickListener());
        btnGreen.setOnClickListener(new myOnclickListener());
        btnBlue.setOnClickListener(new myOnclickListener());

    }
    // handler btn click
    private class myOnclickListener implements View.OnClickListener {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnWhite:
                    setBackground(AddNote.RESULT_COLOR_WHITE);
                    break;
                case R.id.btnYellow:
                    setBackground(AddNote.RESULT_COLOR_YELLOW);
                    break;
                case R.id.btnGreen:
                    setBackground(AddNote.RESULT_COLOR_GREEN);
                    break;
                case R.id.btnBlue:
                    setBackground(AddNote.RESULT_COLOR_BLUE);
                    break;
                default:
                    setBackground(AddNote.RESULT_COLOR_WHITE);
                    break;
            }
        }
    }
    // 
    public void setBackground(int color) {
        Intent i = getIntent();
        Bundle b = new Bundle();
        b.putInt("COLOR", color);
        i.putExtra("DATA", b);
        setResult(AddNote.RESULT_COLOR, i);
        finish();

    }

}
