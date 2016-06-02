package com.cvido.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cvido.HomeActivity;
import com.cvido.R;

public class FirstFragment extends Fragment {
    TextView title_toolbar;
    WebView menuView;

    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        Toolbar toolbar = ((HomeActivity) getActivity()).toolbar;
        title_toolbar = (TextView) toolbar.findViewById(R.id.title_toolbar);
        title_toolbar.setText(getString(R.string.str_first_fragment));

        menuView = (WebView) rootView.findViewById(R.id.menu_webview);
        loadWebViewWithContent();
        return rootView;
    }

    private void loadWebViewWithContent() {
        String htmlText = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "div {" +
                "    text-align: justify;" +
                "    text-justify: inter-word;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "" +
                "<h1>CSS text-justify Example</h1>" +
                "" +
                "<div>In my younger and more vulnerable years my father gave me some advice that I've been turning over in my mind ever  <a href=\"http://www.w3schools.com/html/\">http://www.w3schools.com/html</a> since. 'Whenever you feel like criticizing anyone,' he told me, 'just remember that all the people in this world haven't had the advantages that you've had.'</div>" +
                "" +
                "<p><b>Tip:</b> Resize the browser window to see how the value \"justify\" works.</p>" +
                "" +
                "<p><b>Note:</b> The text-justify property only works in IE.</p>" +
                "" +
                "</body>" +
                "</html>";
        menuView.setVerticalScrollBarEnabled(false);
        menuView.setHorizontalScrollBarEnabled(false);
        final WebSettings webSettings = menuView.getSettings();
        menuView.getSettings().setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        menuView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
            }

        });

        menuView.loadData(htmlText, "text/html; charset=utf-8", "utf-8");

    }


}
