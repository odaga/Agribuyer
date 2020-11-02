package com.ugtechie.agribuyer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ugtechie.agribuyer.R;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";

    private Toolbar mActionBarToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cart, container, false);

        //Initializing the toolbar
        mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("My Cart");

        return v;
    }
}
