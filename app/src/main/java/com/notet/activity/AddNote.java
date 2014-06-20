package com.notet.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.com.note.broadcastreceiver.NoteBroadcastReceiver;
import com.note.databasehandler.DabaseHandler;
import com.note.model.Notes;

import java.util.Calendar;


public class AddNote extends Activity {

    // request and result code
    public static final int REQUEST_CODE_ADD_NOTE = 111;
    public static final int RESULT_COLOR = 113;
    public static final int RESULT_PHOTO = 115;
    //  color use to setbackground for this and Notes
    public static final int RESULT_COLOR_WHITE = 200;
    public static final int RESULT_COLOR_YELLOW = 201;
    public static final int RESULT_COLOR_GREEN = 202;
    public static final int RESULT_COLOR_BLUE = 203;
    // db
    DabaseHandler myHandler;
    Notes notes;
    // all controls
    EditText txtTitle, txtContent;
    ImageView btnCancel;
    TextView txtCurrentDate, txtAlarm;
    Spinner spDate, spTime;
    LinearLayout llAddNote, llAlarm;

    // dialog
    static final int TIME_DIALOG_ID = 155;
    static final int DATE_DIALOG_ID = 166;
    // time and date


    // arr and adapter for spiner
    ArrayAdapter<String> adapterDay = null;
    ArrayAdapter<String> adapterTime = null;
    String arrDate[] = null;
    String arrTime[] = null;

    // time
    Calendar c = Calendar.getInstance();
    static int Hour, Min, Day, Month, Year;
    int color;
    String CurrentDateTime = "";
    String strAlarm = "";
    static String strDay = "";
    static String strTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.bg_actionbar));

        ActionBar actionBar = getActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
        actionBar.setBackgroundDrawable(background);

        arrDate = getResources().getStringArray(R.array.arrdate);
        arrTime = getResources().getStringArray(R.array.arrtime);
        getControls();


    }

    public void getControls() {

        spDate = (Spinner) findViewById(R.id.spDate);
        spTime = (Spinner) findViewById(R.id.spTime);
        txtContent = (EditText) findViewById(R.id.txtAddContent);
        txtTitle = (EditText) findViewById(R.id.txtAddTitle);
        txtAlarm = (TextView) findViewById(R.id.txtAlarm);
        txtCurrentDate = (TextView) findViewById(R.id.txtCreatedDate);
        btnCancel = (ImageView) findViewById(R.id.btnCancel);
        llAddNote = (LinearLayout) findViewById(R.id.llAddNote);
        llAlarm = (LinearLayout) findViewById(R.id.llAlarm);
        llAlarm.setEnabled(false);
        //
        c = Calendar.getInstance();
        CurrentDateTime = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR)+" "
                + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
        txtCurrentDate.setText(CurrentDateTime);

        // show spinner
        txtAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llAlarm.setEnabled(true);
                llAlarm.setVisibility(View.VISIBLE);
                Log.d("Visible linnear layout", "visible");
                spinerDropDown();
                txtAlarm.setVisibility(View.GONE);
            }
        });
        // hide spinner
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llAlarm.setVisibility(LinearLayout.INVISIBLE);
                llAlarm.setEnabled(false);
                txtAlarm.setVisibility(View.VISIBLE);
                strTime = "";
                strDay = "";
                strAlarm = "";
            }
        });
    }

    // show data spinner
    public void spinerDropDown() {
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
            Log.d("AddNote", "Load data for spinner Error...");
        }
    }

    private class myOnItemClickListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case 0:
                    strDay = setDate(0);
                    break;
                case 1:
                    strDay = setDate(1);
                    break;
                case 2:
                    strDay = setDate(2);
                    break;
                case 3:
                    showDialog(DATE_DIALOG_ID);
                    strDay = Day + "/" + Month + "/" + Year;
                    arrDate[3] = strDay;
                    break;
                default:
                    strDay = "";
                    break;
            }
            adapterDay.notifyDataSetChanged();
            Log.d("adapterday ", strDay);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

    // set date when spinner seleted item
    public String setDate(int i) {
        c.add(Calendar.DATE, i);
        Day = c.get(Calendar.DAY_OF_MONTH);
        Month = c.get(Calendar.MONTH);
        Year = c.get(Calendar.YEAR);

        return Day + "/" + Month + "/" + Year;
    }

    private class myOnItemClickListener2 implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    strTime = spTime.getSelectedItem().toString();
                    break;
                default:
                    strTime = "";
                    break;
            }
            adapterTime.notifyDataSetChanged();
            Log.d("adapterday ", strTime);
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

    // call insertphoto activity
    public void InsertPhoto() {
        Intent i = new Intent(AddNote.this, InsertPhoto.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity InertPhoto");
    }

    // call choosecolor activity
    public void SetBacground() {
        Intent i = new Intent(AddNote.this, ChooseColor.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity ChooseColor");
    }

    // save a new notes
    public void SaveNote() {

        strAlarm = strDay.toString() + strTime.toString();
        Log.d("AddNote", "nndndnnn" + strDay.toString());
        Log.d("AddNote", "nndndnnn" + strTime.toString());

        if (txtTitle.getText().toString().equals("")) {
            if (txtContent.getText().toString().equals("")) {
                if (strAlarm.toString().equals("")) {
                    finish();
                    Log.d("all null","cancel insert");
                } else {
                    txtTitle.setText("Untitle");
                    addNote();
                    finish();
                    Log.d("strAlarm not null","insert Untitle");
                }
            } else {
                txtTitle.setText(txtContent.getText().toString());
                addNote();
                finish();
                Log.d("Title null","insert no title");
            }
        } else {
            addNote();
            finish();
            Log.d("title not null","insert note");
        }
    }

    public void addNote() {
        myHandler = new DabaseHandler(this);
        int id = myHandler.idMax() + 1;
        Log.d("add note ","id max "+id);
        notes = new Notes();
        notes.setTitle(txtTitle.getText() + "");
        notes.setContent(txtContent.getText() + "");
        Log.d("AddNote Created date", txtCurrentDate.getText() + "");
        notes.setCreatedDate(txtCurrentDate.getText() + "");
        notes.setBackground(color + "");
        notes.setAlarm(strAlarm);

        try {
            myHandler.addNote(notes);
            myHandler.close();
            Log.d("AddNote", "Add note success.." + strAlarm);

        } catch (Exception e) {
            Log.d("AddNote", "Add new a note errorr..." + e.toString());
        }
         // set alarm for this item.
        if (strAlarm != "") {
            Log.d("AddNote", "Add note strAlarm.." + strAlarm);
            startAlert(id, strAlarm);

        }
    }

    // fuction use to start broadcastreceiver (id )
    public void startAlert(int id, String strAlarm) {
        // get time from spiner
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Year);
        calendar.set(Calendar.MONTH, Month);
        calendar.set(Calendar.YEAR, Day);
        calendar.set(Calendar.HOUR, Hour);
        calendar.set(Calendar.MINUTE, Min);

        Intent intent = new Intent(this, NoteBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getBaseContext(), id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "ALarm set in " + strAlarm, Toast.LENGTH_LONG).show();

    }

    // control the results returned
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int[] arrColor = {getResources().getColor(R.color.color_white), getResources().getColor(R.color.color_yellow),
                getResources().getColor(R.color.color_green), getResources().getColor(R.color.color_blue)};
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_COLOR) {
            Bundle b = data.getBundleExtra("DATA");
            color = b.getInt("COLOR");
            Log.d("Add note", "Background " + color);
            switch (color) {
                case RESULT_COLOR_WHITE:
                    llAddNote.setBackgroundColor(arrColor[0]);
                    break;
                case RESULT_COLOR_YELLOW:
                    llAddNote.setBackgroundColor(arrColor[1]);
                    break;
                case RESULT_COLOR_GREEN:
                    llAddNote.setBackgroundColor(arrColor[2]);
                    break;
                case RESULT_COLOR_BLUE:
                    llAddNote.setBackgroundColor(arrColor[3]);
                    break;
                default:
                    llAddNote.setBackgroundColor(arrColor[4]);
                    break;
            }
        }

    }

    // datepicker and timepiker dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, Hour, Min, false);

            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, Year, Month, Day);
            default:
                break;
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
            Hour = hourOfDay;
            Min = minutes;
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Year = year;
            Month = month;
            Day = day;
        }
    };
}
