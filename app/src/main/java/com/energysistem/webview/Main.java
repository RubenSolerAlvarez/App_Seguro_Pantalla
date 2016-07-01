package com.energysistem.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

public class Main extends Activity {

    private WebView WebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.WebView = (WebView) this.findViewById(R.id.webView);

        // Enable JavaScript WebSettings webSettings
        WebSettings webSettings = WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Zoom Controls
        WebView.getSettings().setBuiltInZoomControls(true);
        WebView.getSettings().setDisplayZoomControls(false);

        // Default Zoom Out
        WebView.getSettings().setLoadWithOverviewMode(true);
        WebView.getSettings().setUseWideViewPort(true);

        // Provide a WebViewClient for your WebView
        WebView.setWebViewClient(new MyWebViewClient());
        WebView.loadUrl("https://store.energysistem.com/es/seguro");

    }

    @Override
    public void onBackPressed() {

        if (this.WebView.canGoBack())
            this.WebView.goBack();
        else
            super.onBackPressed();

    }

    private class MyWebViewClient extends WebViewClient {

        private long loadTime;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            this.loadTime = currentTimeMillis();

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout));

            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(R.string.toast);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM,0, 50);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

            /*Toast.makeText(getApplicationContext(),
                    "Cargando web...", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            this.loadTime = currentTimeMillis() - this.loadTime;

            String time = new SimpleDateFormat("mm:ss:SSS", Locale.getDefault())
                    .format(new Date(this.loadTime));

            /*Toast.makeText(getApplicationContext(),
                    "Web cargada en: " + time, Toast.LENGTH_SHORT)
                    .show();*/

        }
    }

}
