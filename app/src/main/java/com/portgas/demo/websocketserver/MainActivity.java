package com.portgas.demo.websocketserver;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.open).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText et = findViewById(R.id.server_url);
        SimpleServer2.openServer(et.getText().toString());
      }
    });

    findViewById(R.id.openClient).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText et = findViewById(R.id.server_path);
        SimpleClient2.open(et.getText().toString());
      }
    });

    findViewById(R.id.sendTest).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        SimpleClient2.sendMsg("这是一条测试消息");
      }
    });

    ((TextView) findViewById(R.id.ip)).setText("Ip:" + IpGetUtil.getIPAddress(this));
    EditText et = findViewById(R.id.server_path);
    et.setText("ws://192.168.2.2:8887/live");

    EditText et1 = findViewById(R.id.server_url);
    et1.setText("/live");
  }

}
