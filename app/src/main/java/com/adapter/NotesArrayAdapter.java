package com.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.note.model.Notes;
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

    public NotesArrayAdapter(Activity context, int resource, ArrayList<Notes> arrNotes) {
        super(context, resource, arrNotes);
        this.context = context;
        this.arrNotes = arrNotes;
        this.LayoutId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);

        Log.d("NotesArray Adapter","load controls...");
        if (arrNotes.size() > 0) {
            Log.d("NotesArray Adapter","arrNotes size:"+arrNotes.size());
            txtTitle = (TextView) convertView.findViewById(R.id.txtTitleCustom);
            txtContent = (TextView) convertView.findViewById(R.id.txtContentCustom);
            txtCreatedDate = (TextView) convertView.findViewById(R.id.txtCreatedDateCustom);
            imgAlarm = (ImageView) convertView.findViewById(R.id.imgAlarmCustom);


            Notes notes = arrNotes.get(position);

            txtTitle.setText(notes.getTitle());
            txtContent.setText(notes.getContent());
            txtCreatedDate.setText(notes.getCreatedDate());

            if (notes.getAlarm() != null) {
                imgAlarm.setImageResource(R.drawable.ic_action_alarms);
            }
        }

        return convertView;

    }
}
