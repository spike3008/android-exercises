package com.android.madd.exercise1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by madd on 2015-02-10.
 */
public class SitesBindableAdapter extends BindableAdapter<Site> {
    private ArrayList<Site> sitesRows;
    private LayoutInflater inflater;

    public SitesBindableAdapter(Context context, ArrayList<Site> sitesRows) {
        super(context);
        this.sitesRows = sitesRows;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sitesRows.size();
    }

    @Override
    public Site getItem(int position) {
        return sitesRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.rowlayout, container, false);
    }

    @Override
    public void bindView(Site item, int position, View rowView) {
        SiteViewHolder holder;
        holder = new SiteViewHolder(rowView);
        holder.text.setText(item.getUrl());
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        final String date = formatter.print(item.getTimeStamp());
        holder.time.setText(date);
        holder.image.setImageResource(item.isSuccessful()?R.drawable.green_dot:R.drawable.red_dot);
    }

    static class SiteViewHolder {
        @InjectView(R.id.textView)
        TextView text;
        @InjectView(R.id.date_textView)
        TextView time;
        @InjectView(R.id.imageView)
        ImageView image;

        public SiteViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
