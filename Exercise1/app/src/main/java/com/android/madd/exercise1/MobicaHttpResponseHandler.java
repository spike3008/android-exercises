package com.android.madd.exercise1;

import com.android.madd.exercise1.model.Site;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.joda.time.DateTime;

import timber.log.Timber;

/**
 * Created by madd on 2015-01-26.
 */
class MobicaHttpResponseHandler extends AsyncHttpResponseHandler {
    private static Respondent<Site> respondent;
    private String uri;

    public MobicaHttpResponseHandler(Respondent context) {
        uri = "unknown";
        respondent = context;
        respondent.getProgressDialog().show();
    }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Timber.i("Http response status: " + String.valueOf(statusCode));
        if (respondent != null) {
            uri = this.getRequestURI().toString();
            respondent.addResponse(new Site(uri, statusCode, DateTime.now()));
        }
    }

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Timber.i("Http response status: " + String.valueOf(statusCode));
        if (respondent != null) {
            uri = this.getRequestURI().toString();
            respondent.addResponse(new Site(uri, statusCode, DateTime.now()));
        }
    }
}
