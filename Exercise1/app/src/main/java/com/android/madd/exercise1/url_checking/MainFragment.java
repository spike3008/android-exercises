package com.android.madd.exercise1.url_checking;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.madd.exercise1.R;
import com.android.madd.exercise1.model.UrlHistoryItem;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

public abstract class MainFragment extends Fragment implements Respondent<UrlHistoryItem>, UrlChecker {

    protected Context context;
    protected boolean online = false;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_testing, container, false);
        ButterKnife.inject(this, view);
        context = view.getContext();
        return view;
    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    public void checkUrl(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new HttpResponseHandler(this));
    }

    @Override
    public void addResponse(UrlHistoryItem urlHistoryItem) {
        getProgressDialog().dismiss();
        if (urlHistoryItem.isSuccessful()) {
            showToast("SUCCESS");
        } else {
            showToast("FAILED");
        }
    }

    void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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
