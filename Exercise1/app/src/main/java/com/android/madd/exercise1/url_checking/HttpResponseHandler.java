package com.android.madd.exercise1.url_checking;

import com.android.madd.exercise1.model.UrlHistoryItem;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.joda.time.DateTime;

import timber.log.Timber;

class HttpResponseHandler extends AsyncHttpResponseHandler {
    private static Respondent<UrlHistoryItem> respondent;
    private String uri;

    public HttpResponseHandler(Respondent<UrlHistoryItem> context) {
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
        passUrlStatus(statusCode);

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
        passUrlStatus(statusCode);
    }

    public void passUrlStatus(int statusCode) {
        Timber.i("Http response status: " + String.valueOf(statusCode));
        uri = getRequestURI().toString();
        respondent.addResponse(new UrlHistoryItem(uri, statusCode, DateTime.now()));
    }
}
