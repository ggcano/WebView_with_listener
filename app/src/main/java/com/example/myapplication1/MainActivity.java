package com.example.myapplication1;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;

import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupWebView(webView);
        setupWebViewWithDownloadManager(webView);
    }


    private void setupWebView(WebView webview) {
        webview = findViewById(R.id.webiview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.sspa.juntadeandalucia.es/servicioandaluzdesalud/documentos");


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf") || url.endsWith(".doc") || url.endsWith(".xls") || url.endsWith(".ppt")) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        webview.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }

    private void setupWebViewWithDownloadManager(WebView myWebView) {
        myWebView = (WebView) findViewById(R.id.webiview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf") || url.endsWith(".doc") || url.endsWith(".xls") || url.endsWith(".ppt")) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    String fileName = URLUtil.guessFileName(url, null, null);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        myWebView.loadUrl("https://www.sspa.juntadeandalucia.es/servicioandaluzdesalud/documentos");
    }

}


