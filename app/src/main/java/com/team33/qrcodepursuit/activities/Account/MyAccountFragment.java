package com.team33.qrcodepursuit.activities.Account;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.Account;

public class MyAccountFragment extends AccountFragment {

    public static final String TAG = "MyAccountFragment";

    @Override
    protected boolean setAccount() {
        // check login and set account accordingly
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return false; // not logged in
        this.acc = new Account(user.getUid(), false);
        this.acc.fromDB();
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void settings_button_onclick(View v) {
        // navigate to settings
    }

    @Override
    protected void editpf_button_onclick(View v) {
        // edit profile (username, contactinfo, bio)
    }

    @Override
    protected void collection_button_onclick(View v) {
        // navigate to my QRcollection
    }
}
