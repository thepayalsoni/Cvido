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

public class ThirdFragment extends Fragment {
    TextView title_toolbar;

    public ThirdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Toolbar toolbar = ((HomeActivity) getActivity()).toolbar;
        title_toolbar = (TextView) toolbar.findViewById(R.id.title_toolbar);
        title_toolbar.setText(getString(R.string.str_profile_fragment));
        return rootView;
    }
}
