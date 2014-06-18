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

    // my color
    public static final int RESULT_COLOR_WHITE = 200;
    public static final int RESULT_COLOR_YELLOW = 201;
    public static final int RESULT_COLOR_GREEN = 202;
    public static final int RESULT_COLOR_BLUE = 203;

    //
    int color;
    String CurrentDateTime = null;
    String strAlarm = null;
    static String strDay = null;
    static String strTime = null;

    // db
    DabaseHandler myHandler;
    Notes notes;

    // controls
    EditText txtTitle, txtContent;
    ImageView btnCancel;
    TextView txtCurrentDate, txtAlarm;
    Spinner spDate, spTime;
    LinearLayout llAddNote, llAlarm;
    // dialog
    static final int TIME_DIALOG_ID = 155;
    static final int DATE_DIALOG_ID = 166;
    // time and date
    static int Hour, Min, Day, Month, Year;


    ArrayAdapter<String> adapterDay = null;
    ArrayAdapter<String> adapterTime = null;
    String arrDate[] = null;
    String arrTime[] = null;

    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        arrDate = getResources().getStringArray(R.array.arrdate);
        arrTime = getResources().getStringArray(R.array.arrtime);

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
        txtAlarm = (TextView) findViewById(R.id.txtAlarm);
        txtCurrentDate = (TextView) findViewById(R.id.txtCreatedDate);
        btnCancel = (ImageView) findViewById(R.id.btnCancel);
        llAddNote = (LinearLayout) findViewById(R.id.llAddNote);
        llAlarm = (LinearLayout) findViewById(R.id.llAlarm);

        //
        c = Calendar.getInstance();
        CurrentDateTime = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR)
                + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE);
        txtCurrentDate.setText(CurrentDateTime);

        txtAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llAlarm.setVisibility(View.VISIBLE);
                Log.d("Visible linnear layout", "visible");
                txtAlarm.setVisibility(View.GONE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llAlarm.setVisibility(LinearLayout.INVISIBLE);
                txtAlarm.setVisibility(View.VISIBLE);
                strTime = "";
                strDay = "";
                strAlarm = "";
            }
        });
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
            // spDate.setAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

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
//            if (i == 3) {
//                showDialog(TIME_DIALOG_ID);
//                strTime = Hour + ":" + Min;
//                arrTime[3] = strTime;
//
//            } else {
//                arrTime[3] = "Other...";
//            }
            adapterTime.notifyDataSetChanged();
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
        int id = myHandler.idMax() + 1;
        strAlarm = strDay + strTime;

        notes = new Notes();

        notes.setTitle(txtTitle.getText() + "");
        notes.setContent(txtContent.getText() + "");
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

        if (strTime.equals("") && strDay.equals("")) {
            Log.d("AddNote", "nndndnnn" + strDay);
            Log.d("AddNote", "nndndnnn" + strTime);
            finish();

        } else {
            startAlert(id, strAlarm);
            finish();
        }


    }

    public void startAlert(int id, String strAlarm) {
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
