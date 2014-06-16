package com.notet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.note.databasehandler.DabaseHandler;
import com.note.model.Notes;


public class AddNote extends Activity {

    public static final int REQUEST_CODE_ADD_NOTE = 111;
    public static final int RESULT_COLOR = 113;
    public static final int RESULT_PHOTO = 115;

    public String strColor = null;
    public String strAlarm = null;
    public String strDay = null;
    public String strTime = null;
    DabaseHandler myHandler;
    Notes notes;

    EditText txtTitle, txtContent;
    ImageView btnCancel;
    TextView txtCurrentDate;
    Spinner spDate, spTime;
    LinearLayout llAddNote;


    ArrayAdapter<String> adapterDay = null;
    ArrayAdapter<String> adapterTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        final String arrDate[] = getResources().getStringArray(R.array.arrdate);
        final String arrTime[] = getResources().getStringArray(R.array.arrtime);
        getControls();
        try {
            adapterDay = new ArrayAdapter<String>(AddNote.this, android.R.layout.simple_spinner_item, arrDate);
            adapterTime = new ArrayAdapter<String>(AddNote.this, android.R.layout.simple_spinner_item, arrTime);

            adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spDate.setAdapter(adapterDay);
            spTime.setAdapter(adapterTime);

            spDate.setOnItemSelectedListener(new myOnItemClickListener());
            spTime.setOnItemSelectedListener(new myOnItemClickListener2());

        } catch (Exception e) {
            Log.d("AddNote", "Load arrString Error...");
        }


    }

    public void getControls() {
        spDate = (Spinner) findViewById(R.id.spDate);
        spTime = (Spinner) findViewById(R.id.spTime);
        txtContent = (EditText) findViewById(R.id.txtAddContent);
        txtTitle = (EditText) findViewById(R.id.txtAddTitle);
//        txtCurrentDate = (TextView) findViewById(R.id.txtCreatedDate);
        btnCancel = (ImageView) findViewById(R.id.btnCancel);
        llAddNote = (LinearLayout) findViewById(R.id.llAddNote);


    }

    private class myOnItemClickListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                strDay = spDate.getSelectedItem().toString();
            } catch (Exception e) {
                Log.d("AddNote activity SpDay", strAlarm);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

    private class myOnItemClickListener2 implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                strTime = spTime.getSelectedItem().toString();
            } catch (Exception e) {
                Log.d("AddNote activity SpTime", strAlarm);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_insertphoto:
                InsertPhoto();
                break;

            case R.id.action_setbackground:
                SetBacground();
                break;
            case R.id.action_savenote:
                SaveNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void InsertPhoto() {
        Intent i = new Intent(AddNote.this, InsertPhoto.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity InertPhoto");
    }

    public void SetBacground() {
        Intent i = new Intent(AddNote.this, ChooseColor.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity ChooseColor");
    }

    public void SaveNote() {
        myHandler = new DabaseHandler(this);
        strAlarm = strDay + " " + strTime;
        notes = new Notes();
        notes.setTitle(txtTitle.getText() + "");
        notes.setContent(txtContent.getText() + "");
       // notes.setCreatedDate(txtCurrentDate.getText() + "");
        notes.setCreatedDate("16/06/2014");
        notes.setBackground("YELLOW");
        notes.setAlarm(strAlarm);
        try {
            myHandler.addNote(notes);
            myHandler.close();
            finish();
            Log.d("AddNote", "Call back to MainActivity class");
        } catch (Exception e) {
            Log.d("AddNote", "Add new a note errorr..." + e.toString());
        }
//        Intent i = new Intent(AddNote.this, MainActivity.class);
//        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_COLOR) {
            strColor = data.getStringExtra("DATA");
//            llAddNote.setBackgroundColor();

        }

    }
}
