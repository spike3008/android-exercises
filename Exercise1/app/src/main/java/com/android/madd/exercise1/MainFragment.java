package com.android.madd.exercise1;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by madd on 2015-02-11.
 */
public abstract class MainFragment extends Fragment implements Respondent<Site>, UrlChecker {

    ProgressDialog progressDialog;
    protected Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_testing, container, false);
        ButterKnife.inject(this, view);
        context = view.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    public void checkUrl(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new MobicaHttpResponseHandler(this));
    }

    @Override
    public void addResponse(Site site) {
        getProgressDialog().dismiss();
        if (site.isSuccessful()) {
            showToast("SUCCESS");
        } else {
            showToast("FAILED");
        }
    }

    void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Timber.i("Toast with message '%s' shown", text);
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            createProgressDialog();
        }
        return progressDialog;
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        final CharSequence message = getText(R.string.dialog_wait_message);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
    }
}
