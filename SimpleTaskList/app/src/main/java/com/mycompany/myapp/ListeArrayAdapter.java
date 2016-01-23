package com.mycompany.myapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gunseb on 23/01/16.
 */
public class ListeArrayAdapter extends ArrayAdapter<ListeDTO> {
    private final Activity context;
    private final ArrayList<ListeDTO> listes;

    static class ViewHolder{
        public TextView description;
    }

    public ListeArrayAdapter(Activity context, ArrayList<ListeDTO> listes){
        super(context, R.layout.listerowlayout, listes);
        this.context = context;
        this.listes = listes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.listerowlayout,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.description = (TextView) rowView.findViewById(R.id.textViewListDesc);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        ListeDTO liste = listes.get(position);
        holder.description.setText(liste.getDescription());
        return rowView;
    }
}
