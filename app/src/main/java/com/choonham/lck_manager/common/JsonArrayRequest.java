package com.choonham.lck_manager.common;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JsonArrayRequest <T> extends JsonRequest<JSONArray> {

    private JSONObject mRequestObject;
    private Response.Listener<JSONArray> mResponseListener;

    public JsonArrayRequest(int method, String url, JSONObject requestObject, Response.Listener<JSONArray> responseListener,  Response.ErrorListener errorListener){
        super(method, url, (requestObject == null) ? null : requestObject.toString(), responseListener, errorListener);
        mRequestObject = requestObject;
        mResponseListener = responseListener;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            try {
                return Response.success(new JSONArray(json),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (JSONException e) {
                return Response.error(new ParseError(e));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
