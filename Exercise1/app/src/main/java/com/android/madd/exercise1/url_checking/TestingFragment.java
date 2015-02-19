package com.android.madd.exercise1.url_checking;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.madd.exercise1.model.MySitesDatabaseHelper;
import com.android.madd.exercise1.R;
import com.android.madd.exercise1.model.Site;
import com.android.madd.exercise1.model.SitesAdapter;
import com.android.madd.exercise1.model.UniqueSitesList;
import com.android.madd.exercise1.network.NetworkStatusChangeReceiver;
import com.android.madd.exercise1.network.NetworkStatusHandler;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import timber.log.Timber;

public class TestingFragment extends MainFragment implements NetworkStatusHandler {

    private final String MOBICA_URL = "http://mobica.com";
    @InjectView(R.id.listView) ListView listView;
    @InjectView(R.id.main_editText_url) TextView edtUrl;
    @InjectView(R.id.btn_test) Button btnTest;
    private UniqueSitesList sites = new UniqueSitesList();
    private MySitesDatabaseHelper dbHelper;
    private SitesAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUrl.setText(MOBICA_URL);
        dbHelper = new MySitesDatabaseHelper(context);
        sites = dbHelper.getNewestSites();
        adapter = new SitesAdapter(context, sites);
        listView.setAdapter(adapter);
        NetworkStatusChangeReceiver receiver = new NetworkStatusChangeReceiver(this);
        context.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        String url = edtUrl.getText().toString();
        checkUrl(url);
    }

    @OnItemClick(R.id.listView)
    public void checkSiteAtPosition(int position) {
        Timber.d("Clicked item at position: " + position);
        if (online) {
            Site site = adapter.getItem(position);
            checkUrl(site.getUrl());
        } else {
            showToast("Internet connection is required");
        }
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

    @Override
    public void handleNetworkStatus(boolean isOnline) {
        String status = isOnline ? "ONLINE" : "OFFLINE";
        Timber.i("Connection state changed to: " + status);
        this.online = isOnline;
        btnTest.setEnabled(isOnline);
    }
}
