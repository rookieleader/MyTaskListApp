package com.mycompany.myapp;

import android.app.Activity;
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
public class ItemArrayAdapter extends ArrayAdapter<ItemDTO> {
    private final Activity context;
    private final ArrayList<ItemDTO> items;

    static class ViewHolder{
        public CheckBox isDone;
        public TextView description;
    }

    public ItemArrayAdapter(Activity context, ArrayList<ItemDTO> items){
        super(context, R.layout.itemrowlayout, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.itemrowlayout,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.isDone = (CheckBox) rowView.findViewById(R.id.checkBoxIsDone);
            viewHolder.description = (TextView) rowView.findViewById(R.id.textViewItemDesc);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        ItemDTO item = items.get(position);
        holder.isDone.setChecked(item.isDone());
        holder.description.setText(item.getDescription());

        return rowView;
    }
}
