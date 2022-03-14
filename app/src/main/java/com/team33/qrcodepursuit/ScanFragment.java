package com.team33.qrcodepursuit;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ScanFragment extends Fragment {
    public ScanFragment() {
        super(R.layout.fragment_scan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }
}
