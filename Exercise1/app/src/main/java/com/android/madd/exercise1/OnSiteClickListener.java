package com.android.madd.exercise1;

import android.view.View;
import android.widget.AdapterView;

import timber.log.Timber;

/**
 * Created by madd on 2015-02-06.
 */
public class OnSiteClickListener implements AdapterView.OnItemClickListener {

    private UrlChecker checker;

    public OnSiteClickListener(UrlChecker checker) {
        this.checker = checker;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Timber.d("Clicked item at position: " + position);
        Site site = (Site) parent.getItemAtPosition(position);
        checker.checkUrl(site.getUrl());
    }
}
