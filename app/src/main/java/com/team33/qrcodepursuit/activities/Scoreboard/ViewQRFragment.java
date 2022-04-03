package com.team33.qrcodepursuit.activities.Scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.GameQRCode;

/**
 * fragment to view qr code info that already exists
 */
public class ViewQRFragment extends Fragment {

    private GameQRCode qr;

    public ViewQRFragment() { super(R.layout.fragment_viewqr); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_recieveqr, container, false);
        Bundle b = getArguments();
        //qr = b.getParcelable();

        return view;
    }

}
