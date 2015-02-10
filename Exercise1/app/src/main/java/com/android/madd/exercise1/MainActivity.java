package com.android.madd.exercise1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import timber.log.Timber;

/**
 * Created by madd on 2015-01-26.
 */
public class MainActivity extends ActionBarActivity implements Respondent<Site>, UrlChecker {

    private final String MOBICA_URL = "http://mobica.com";
    @InjectView(R.id.main_editText_url)
    EditText edtUrl;
    @InjectView(R.id.listView)
    ListView listView;
    ProgressDialog progressDialog;
    private UniqueSitesList sites = new UniqueSitesList();
    private MySitesDatabaseHelper dbHelper;
    private SitesBindableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        edtUrl.setText(MOBICA_URL);
        dbHelper = new MySitesDatabaseHelper(this);
        sites = dbHelper.getNewestSites();
        adapter = new SitesBindableAdapter(this, sites);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        String url = edtUrl.getText().toString();
        checkUrl(url);
    }

    @OnItemClick(R.id.listView)
    public void checkSiteAtPosition(int position) {
        Timber.d("Clicked item at position: " + position);
        Site site = adapter.getItem(position);
        checkUrl(site.getUrl());
    }

    public void checkUrl(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new MobicaHttpResponseHandler(this));
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            createProgressDialog();
        }
        return progressDialog;
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        final CharSequence message = getText(R.string.dialog_wait_message);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
    }

    /**
     * Prepares and shows a Toast with duration set to short.
     *
     * @param text message to be shown on a Toast
     */
    void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Timber.i("Toast with message '%s' shown", text);
    }

    public void addResponse(Site site) {
        getProgressDialog().dismiss();
        if (site.isSuccessful()) {
            showToast("SUCCESS");
        } else {
            showToast("FAILED");
        }
        dbHelper.insertSite(site);
        sites.replaceOlderRequests(site);
        adapter.notifyDataSetChanged();
        Timber.i("Site '%s' added to list", site.getUrl());
    }
}