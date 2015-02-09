package com.android.madd.exercise1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by madd on 2015-01-29.
 */
public class MyPerformanceArrayAdapter extends ArrayAdapter<Site> {
    private final Activity context;
    private final ArrayList<Site> sites;

    public MyPerformanceArrayAdapter(Activity context, ArrayList<Site> sites) {
        super(context, R.layout.rowlayout, sites);
        this.context = context;
        this.sites = sites;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            ViewHolder viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Site item = sites.get(super.getCount() - position - 1);
        holder.text.setText(item.getUrl());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final String date = formatter.print(item.getTimeStamp());
        holder.time.setText(date);
        if (item.isSuccesful()) {
            holder.image.setImageResource(R.drawable.green_dot);
        } else {
            holder.image.setImageResource(R.drawable.red_dot);
        }
        return rowView;
    }



    static class ViewHolder {
        @InjectView(R.id.textView)
        public TextView text;
        @InjectView(R.id.date_textView)
        public TextView time;
        @InjectView(R.id.imageView)
        public ImageView image;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
