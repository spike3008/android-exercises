package com.android.madd.exercise1.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.madd.exercise1.R;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SitesBindableAdapter extends BindableAdapter<Site> {
    private ArrayList<Site> sitesRows;
    private DateTimeFormatter formatter;

    public SitesBindableAdapter(Context context, ArrayList<Site> sitesRows) {
        super(context);
        this.sitesRows = sitesRows;
        formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
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
        View view = inflater.inflate(R.layout.rowlayout, container, false);
        view.setTag(new SiteViewHolder(view, formatter));
        return view;
    }

    @Override
    public void bindView(Site item, int position, View rowView) {
        ((SiteViewHolder) rowView.getTag()).bind(item);
    }

    static class SiteViewHolder {
        @InjectView(R.id.textView) TextView text;
        @InjectView(R.id.date_textView) TextView time;
        @InjectView(R.id.imageView)  ImageView image;
        DateTimeFormatter formatter;

        public SiteViewHolder(View view, DateTimeFormatter formatter) {
            ButterKnife.inject(this, view);
            this.formatter = formatter;
        }

        public void bind(Site item) {
            this.text.setText(item.getUrl());
            this.time.setText(formatter.print(item.getTimeStamp()));
            this.image.setImageResource(item.isSuccessful() ? R.drawable.green_dot : R.drawable.red_dot);
        }
    }
}
