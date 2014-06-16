package com.notet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ChooseColor extends Activity {

    Button btnWhite, btnYellow, btnGreen, btnBlue;
    String strColor=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);

        btnWhite = (Button) findViewById(R.id.btnWhite);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnBlue = (Button) findViewById(R.id.btnBlue);

        btnWhite.setOnClickListener(new myOnclickListener());
        btnYellow.setOnClickListener(new myOnclickListener());
        btnGreen.setOnClickListener(new myOnclickListener());
        btnBlue.setOnClickListener(new myOnclickListener());


    }
    private  class myOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id){
                case R.id.btnWhite:
                    setBackground("WHITE");
                    break;
                case R.id.btnYellow:
                    setBackground("YELLOW");
                    break;
                case R.id.btnGreen:
                    setBackground("GREEN");
                    break;
                case R.id.btnBlue:
                    setBackground("BLUE");
                    break;
                default:
                    setBackground("WHITE");
                    break;
            }
        }
    }
    public void setBackground(String color){
        Intent i = getIntent();
        i.putExtra("DATA",color);
        setResult(AddNote.RESULT_COLOR,i);
        finish();

    }

}
