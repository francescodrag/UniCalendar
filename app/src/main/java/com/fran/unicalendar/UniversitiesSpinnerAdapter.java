package com.fran.unicalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UniversitiesSpinnerAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater inflater;

    public UniversitiesSpinnerAdapter(Context context, int resourceId, List<String> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.universities_spinner_element, null);
        }

        List<String> c = (List<String>) getItem(position);

        Log.d("DEBUG", "contact c=" + c);

        TextView nameTextView;
        TextView telTextView;
        ImageView fotoImageView;

        nameTextView = v.findViewById(R.id.univeristy_element_nome);

        Log.d("DEBUG", "nameTextView=" + nameTextView);


        return v;
    }
}
