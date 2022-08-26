package com.milesforce.mwbewb.TabFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milesforce.mwbewb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoSrFragment extends Fragment {


    public NoSrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_sr, container, false);
    }

}
