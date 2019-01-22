package com.portgas.demo.websocketserver;

import android.util.Log;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

public class SimpleHttpServer extends AsyncHttpServer {

  private static final String TAG = "799";

  @Override
  protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
    Log.d(TAG, "onRequest1: " + request.getMethod());
    return super.onRequest(request, response);
  }

  @Override
  protected void onRequest(HttpServerRequestCallback callback, AsyncHttpServerRequest request,
      AsyncHttpServerResponse response) {
    Log.d(TAG, "onRequest2: " + request.getMethod());
    super.onRequest(callback, request, response);
  }

  @Override
  public void websocket(String regex, WebSocketRequestCallback callback) {
    super.websocket(regex, callback);
    Log.d(TAG, "websocket: " + regex);
  }

  @Override
  public void websocket(String regex, String protocol, WebSocketRequestCallback callback) {
    super.websocket(regex, protocol, callback);
    Log.d(TAG, "websocket: " + regex);
  }

  @Override
  public void get(String regex, HttpServerRequestCallback callback) {
    super.get(regex, callback);
    Log.d(TAG, "get: ");
  }

  @Override
  public void post(String regex, HttpServerRequestCallback callback) {
    super.post(regex, callback);
    Log.d(TAG, "post: ");
  }
}
