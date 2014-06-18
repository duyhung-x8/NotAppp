package com.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.note.model.Notes;
import com.notet.activity.AddNote;
import com.notet.activity.MainActivity;
import com.notet.activity.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DUYHUNG on 16-06-2014.
 */
public class NotesArrayAdapter extends ArrayAdapter<Notes> {
    Activity context;
    ArrayList<Notes> arrNotes = null;
    int LayoutId;

    TextView txtTitle, txtContent, txtCreatedDate;
    ImageView imgAlarm;
   // RelativeLayout rlitem;


    public NotesArrayAdapter(Activity context, int resource, ArrayList<Notes> arrNotes) {
        super(context, resource, arrNotes);
        this.context = context;
        this.arrNotes = arrNotes;
        this.LayoutId = resource;
        Log.d("NotesArray Adapter", "calling contrustor..");

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // rlitem= MainActivity.rlItem;
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);

        Log.d("NotesArray Adapter", "load controls...");
        if (arrNotes.size() > 0) {
            Log.d("NotesArray Adapter", "arrNotes size:" + arrNotes.size());
            txtTitle = (TextView) convertView.findViewById(R.id.txtTitleCustom);
            txtContent = (TextView) convertView.findViewById(R.id.txtContentCustom);
            txtCreatedDate = (TextView) convertView.findViewById(R.id.txtCreatedDateCustom);
           // imgAlarm = (ImageView) convertView.findViewById(R.id.imgAlarmCustom);


            Notes notes = arrNotes.get(position);
            Log.d("NotesArray Adapter", "load background..."+notes.getBackground());
            Log.d("NotesArray Adapter", "load alarm..."+notes.getAlarm());
            txtTitle.setText(notes.getTitle());
            txtContent.setText(notes.getContent());
            txtCreatedDate.setText(notes.getCreatedDate());
            int color=Integer.parseInt(notes.getBackground().trim());

            if (color== AddNote.RESULT_COLOR_BLUE){
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
            }
            else if (color== AddNote.RESULT_COLOR_YELLOW){
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_yellow));

            }else if(color== AddNote.RESULT_COLOR_GREEN){
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_green));
            }
            //.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_alarms2));
//            if (notes.getAlarm() != null) {
//                Log.d("Notes arr adapter","load img...");
//                imgAlarm.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_alarms2));
//            }
        }

        return convertView;

    }
    public  void setBackgound(int color ){

    }
}
