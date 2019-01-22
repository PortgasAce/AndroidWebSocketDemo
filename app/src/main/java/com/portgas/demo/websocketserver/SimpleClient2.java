package com.portgas.demo.websocketserver;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClient.WebSocketConnectCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocket.StringCallback;

public class SimpleClient2 {

  private static final String URL = "ws://localhost:8887/live";
  private static final String TAG = "799";
  private static Handler sHandler;
  private static WebSocket sClient;
  private static boolean isOpen = false;

  private static Handler getHandler() {
    if (sHandler == null) {
      synchronized (SimpleClient2.class) {
        if (sHandler == null) {
          HandlerThread handlerThread = new HandlerThread("client",
              Process.THREAD_PRIORITY_BACKGROUND);
          handlerThread.start();
          sHandler = new Handler(handlerThread.getLooper());
        }
      }
    }
    return sHandler;
  }

  public static void open(final String url) {
    getHandler().post(new Runnable() {
      @Override
      public void run() {
        openIn(url);
      }
    });
  }

  private static void openIn(String url) {
    AsyncHttpClient.getDefaultInstance().websocket(url, null, new WebSocketConnectCallback() {
      @Override
      public void onCompleted(Exception ex, WebSocket webSocket) {
        if (ex != null) {
          ex.printStackTrace();
          return;
        }
        Log.d(TAG, "onCompleted: ");
        isOpen = true;
        sClient = webSocket;
        webSocket.send("a string");
        webSocket.send("Hello Server");
//        webSocket.send("test".getBytes());
        webSocket.setStringCallback(new StringCallback() {
          public void onStringAvailable(String s) {
            System.out.println("I got a string: " + s);
            Log.d(TAG, "onStringAvailable1: " + s);
          }
        });
        webSocket.setDataCallback(new DataCallback() {
          public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
            System.out.println("I got some bytes!");
            Log.d(TAG, "onDataAvailable: ");
            // note that this data has been read
            byteBufferList.recycle();
          }
        });
      }
    });
  }

  public static void sendMsg(final String msg) {
    getHandler().post(new Runnable() {
      @Override
      public void run() {
        if (isOpen) {
          sClient.send(msg);
        }
      }
    });
  }
}
