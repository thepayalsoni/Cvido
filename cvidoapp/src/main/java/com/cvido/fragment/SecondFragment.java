package com.cvido.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvido.HomeActivity;
import com.cvido.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondFragment extends Fragment {
    TextView title_toolbar;

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        Toolbar toolbar = ((HomeActivity) getActivity()).toolbar;
        title_toolbar = (TextView) toolbar.findViewById(R.id.title_toolbar);
        title_toolbar.setText(getString(R.string.str_second_fragment));

        String text = "asdf adsf asdfasdf http://www.google.com adsfasdfas asdf adsf asdfasdf http://www.yahoo.com adsfasdfas asdf adsf asdfasdf http://www.gmail.com adsfasdfas";
        String pattern1 = "http://";
        String pattern2 = " ";


        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {
            System.out.println("dataaaaa===" + m.group(1));
        }


        return rootView;
    }
}
