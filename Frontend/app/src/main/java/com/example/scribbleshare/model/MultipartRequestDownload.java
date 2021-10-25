package com.example.scribbleshare.model;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

public class MultipartRequestDownload extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;
    public Map<String, String> responseHeaders;

    public MultipartRequestDownload(int method, String mUrl, Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params){
        super(method, mUrl, errorListener);
        setShouldCache(false); //TODO this might change
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        responseHeaders = response.headers;
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

}
