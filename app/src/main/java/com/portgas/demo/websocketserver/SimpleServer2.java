package com.portgas.demo.websocketserver;

import android.util.Log;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocket.StringCallback;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer2 {

  private static final String TAG = "799";
  private static List<WebSocket> _sockets = new ArrayList<>();

  private static void open(String url) {
    Log.d(TAG, "open: ");
    final AsyncHttpServer httpServer = new SimpleHttpServer();
    httpServer.setErrorCallback(new CompletedCallback() {
      @Override
      public void onCompleted(Exception ex) {
        Log.d(TAG, "onCompleted: ");
        Log.e("WebSocket", "An error occurred", ex);
      }
    });

    int port = 8887;
    httpServer.listen(AsyncServer.getDefault(), port);

    // url = "/live"
    httpServer.websocket(url, new AsyncHttpServer.WebSocketRequestCallback() {
      @Override
      public void onConnected(final WebSocket webSocket, AsyncHttpServerRequest request) {
        Log.d(TAG, "onConnected: ");
        _sockets.add(webSocket);

        //Use this to clean up any references to your websocket
        webSocket.setClosedCallback(new CompletedCallback() {
          @Override
          public void onCompleted(Exception ex) {
            Log.d(TAG, "onCompleted: ", ex);
            try {
              if (ex != null) {
                Log.e("WebSocket", "An error occurred", ex);
              }
            } finally {
              _sockets.remove(webSocket);
            }
          }
        });

        webSocket.setStringCallback(new StringCallback() {
          @Override
          public void onStringAvailable(String s) {
            Log.d(TAG, "onStringAvailable: " + s);
            if ("Hello Server".equals(s)) {
              webSocket.send("Welcome Client!");
            }
          }
        });

      }
    });
  }

  public static void openServer(final String url) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        open(url);
      }
    }).start();
  }
}
