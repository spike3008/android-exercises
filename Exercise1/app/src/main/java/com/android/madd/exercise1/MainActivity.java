package com.android.madd.exercise1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by madd on 2015-01-26.
 */
public class MainActivity extends ActionBarActivity implements Respondent<Site> {

    private final String MOBICA_URL = "http://mobica.com";
    @InjectView(R.id.main_editText_url)
    EditText edtUrl;
    @InjectView(R.id.listView)
    ListView list;
    ProgressDialog progressDialog;
    private ArrayList<Site> sites = new ArrayList<Site>();
    private MyPerformanceArrayAdapter adapter;
    private MySitesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        edtUrl.setText(MOBICA_URL);
        dbHelper = new MySitesDatabaseHelper(this);
        sites = dbHelper.getAllSites();
        adapter = new MyPerformanceArrayAdapter(this, sites);
        list.setAdapter(adapter);
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        String url = edtUrl.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new MobicaHttpResponseHandler(this));
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            final CharSequence message = getText(R.string.dialog_wait_message);
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
        }
        return progressDialog;
    }

    /**
     * Prepares and shows a Toast with duration set to short.
     *
     * @param text message to be shown on a Toast
     */
    void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Timber.i("Toast with message '%s' shown", text);
    }

    public void addResponse(Site site) {
        getProgressDialog().dismiss();
        if (site.isSuccesful()) {
            showToast("SUCCESS");
        } else {
            showToast("FAILED");
        }
        dbHelper.insertSite(site);
        sites.add(site);
        adapter.notifyDataSetChanged();
        Timber.i("Site '%s' added to list", site.getUrl());
    }
}