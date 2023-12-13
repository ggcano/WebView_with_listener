package com.example.myapplication1;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebView extends WebView {
    private DownloadManager downloadManager;

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setupCustomWebView(String url) {
        this.getSettings().setJavaScriptEnabled(true);
        this.setWebViewClient(new WebViewClient());
        this.loadUrl(url);
    }

    public void setupListenerFiles(Context context) {
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf") || url.endsWith(".doc") || url.endsWith(".xls") || url.endsWith(".ppt")) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    String fileName = URLUtil.guessFileName(url, null, null);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }


}
