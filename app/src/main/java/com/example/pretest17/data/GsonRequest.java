package com.example.pretest17.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private final Gson gson;
    private final Class<T> clazz;
    private final Response.Listener<T> successListener;
    private final JsonElement body;

    private Map<String, String> headers;

    GsonRequest(int method, @NonNull String url, @NonNull Class<T> clazz,
                @Nullable Map<String, String> headers, @Nullable JsonElement body,
                @Nullable Response.Listener<T> successListener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        gson = new Gson();
        this.clazz = clazz;
        this.headers = headers;
        this.successListener = successListener;
        this.body = body;
    }

    @Override
    public byte[] getBody() {
        return body == null ? null : body.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        if (successListener != null) {
            successListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    StandardCharsets.UTF_8);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }
}
