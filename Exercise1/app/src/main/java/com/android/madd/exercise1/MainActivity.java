package com.android.madd.exercise1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by madd on 2015-01-26.
 */
public class MainActivity extends ActionBarActivity {

    private final String MOBICA_URL = "http://mobica.com";
    @InjectView(R.id.main_editText_url)
    EditText edtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.tag(this.getClass().getName());
        edtUrl.setText(MOBICA_URL);
    }

    @OnClick(R.id.btn_test)
    public void onButtonClicked() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(edtUrl.getText().toString(), new MobicaHttpResponseHandler(this));
    }

    /**
     * Prepares and shows a Toast with duration set to short.
     *
     * @param text  message to be shown on Toast
     */
    void showToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Timber.i("Toast with message '%s' shown", text);
    }
}


