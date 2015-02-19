package com.android.madd.exercise1.url_checking;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.madd.exercise1.model.HistoryDatabaseHelper;
import com.android.madd.exercise1.R;
import com.android.madd.exercise1.model.UniqueItemsList;
import com.android.madd.exercise1.model.UrlHistoryItem;
import com.android.madd.exercise1.model.SitesAdapter;
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
    private UniqueItemsList items = new UniqueItemsList();
    private HistoryDatabaseHelper dbHelper;
    private SitesAdapter adapter;
    private NetworkStatusChangeReceiver broadcastReceiver;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUrl.setText(MOBICA_URL);
        dbHelper = new HistoryDatabaseHelper(context);
        items = dbHelper.getNewestSites();
        adapter = new SitesAdapter(context, items);
        listView.setAdapter(adapter);
        broadcastReceiver = new NetworkStatusChangeReceiver(this);
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        String url = edtUrl.getText().toString();
        checkUrl(url);
    }

    @OnItemClick(R.id.listView)
    public void checkItemAtPosition(int position) {
        Timber.d("Clicked item at position: " + position);
        if (online) {
            UrlHistoryItem urlHistoryItem = adapter.getItem(position);
            checkUrl(urlHistoryItem.getUrl());
        } else {
            showToast("Internet connection is required");
        }
    }

    @Override
    public void addResponse(UrlHistoryItem urlHistoryItem) {
        super.addResponse(urlHistoryItem);
        store(urlHistoryItem);
    }

    protected void store(UrlHistoryItem urlHistoryItem) {
        dbHelper.insertSite(urlHistoryItem);
        items.replaceOlderRequests(urlHistoryItem);
        adapter.notifyDataSetChanged();
        Timber.i("Site '%s' added to list", urlHistoryItem.getUrl());
    }

    @Override
    public void handleNetworkStatus(boolean isOnline) {
        String status = isOnline ? "ONLINE" : "OFFLINE";
        Timber.i("Connection state changed to: " + status);
        this.online = isOnline;
        btnTest.setEnabled(isOnline);
    }

    @Override
    public void onResume() {
        super.onResume();
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        context.unregisterReceiver(broadcastReceiver);
        super.onPause();
    }
}
