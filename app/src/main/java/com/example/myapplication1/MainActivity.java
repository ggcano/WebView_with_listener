package com.example.myapplication1;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomWebView customWebView = (CustomWebView) findViewById(R.id.webview_custom);
        customWebView.setupCustomWebView("https://www.sspa.juntadeandalucia.es/servicioandaluzdesalud/documentos");
        customWebView.setupListenerFiles(this);
    }

}


