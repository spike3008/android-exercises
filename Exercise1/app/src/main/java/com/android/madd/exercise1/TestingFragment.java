package com.android.madd.exercise1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import timber.log.Timber;

public class TestingFragment extends MainFragment {

    private final String MOBICA_URL = "http://mobica.com";
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.main_editText_url)
    TextView edtUrl;
    private UniqueSitesList sites = new UniqueSitesList();
    private MySitesDatabaseHelper dbHelper;
    private SitesBindableAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.i("TestingFragment created.");
        edtUrl.setText(MOBICA_URL);
        dbHelper = new MySitesDatabaseHelper(context);
        sites = dbHelper.getNewestSites();
        adapter = new SitesBindableAdapter(context, sites);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        Timber.i("Button Clicked");
        String url = edtUrl.getText().toString();
        checkUrl(url);
    }

    @OnItemClick(R.id.listView)
    public void checkSiteAtPosition(int position) {
        Timber.d("Clicked item at position: " + position);
        Site site = adapter.getItem(position);
        checkUrl(site.getUrl());
    }

    @Override
    public void addResponse(Site site) {
        super.addResponse(site);
        store(site);
    }

    protected void store(Site site) {
        dbHelper.insertSite(site);
        sites.replaceOlderRequests(site);
        adapter.notifyDataSetChanged();
        Timber.i("Site '%s' added to list", site.getUrl());
    }


}
