package com.team33.qrcodepursuit.activities.Account;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.Account;

/**
 * base class for MyAccountFragment and OtherAccountFragment
 */
public abstract class AccountFragment extends Fragment {

    // ui elements
    protected Button settings_button;
    protected ImageView pfp;
    protected TextView username_text;
    protected Button editpf_button;
    protected TextView contactinfo_text;
    protected TextView bio_text;
    protected TextView hiscore_text;
    protected TextView totalscore_text;
    protected TextView scannedCount_text;
    protected TextView ownedCount_text;
    protected Button collection_button;
    protected NavController navctrl;
    // account
    protected Account acc;
    // abstract methods
    protected abstract boolean setAccount();
    protected abstract void settings_button_onclick(View v);
    protected abstract void editpf_button_onclick(View v);
    protected abstract void collection_button_onclick(View v);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
        { return inflater.inflate(R.layout.fragment_account, container, false); }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // link ui
        settings_button = view.findViewById(R.id.account_settings_button);
        pfp = view.findViewById(R.id.account_pfp_imgview);
        username_text = view.findViewById(R.id.account_username_text);
        editpf_button = view.findViewById(R.id.account_editprofile_button);
        contactinfo_text = view.findViewById(R.id.account_info_contactinfo_content);
        bio_text = view.findViewById(R.id.account_info_bio_content);
        hiscore_text = view.findViewById(R.id.account_gamestats_hiscore);
        totalscore_text = view.findViewById(R.id.account_gamestats_totalscore);
        scannedCount_text = view.findViewById(R.id.account_gamestats_scannedqrs);
        ownedCount_text = view.findViewById(R.id.account_gamestats_ownedqrs);
        collection_button = view.findViewById(R.id.account_collection_button);
        navctrl = Navigation.findNavController(view);
        // set account
        if (!this.setAccount()) return;
        // fill content
        username_text.setText(acc.getUsername());
        contactinfo_text.setText(acc.getContactinfo());
        bio_text.setText(acc.getBio());
        hiscore_text.setText(format("%d pts", acc.getHiScore()));
        hiscore_text.setText(format("%d pts", acc.getTotalScore()));
        hiscore_text.setText(format("%d", acc.getScannedQRsCount()));
        hiscore_text.setText(format("%d", acc.getOwnedQRsCount()));
        // set listeners
        settings_button.setOnClickListener(this::settings_button_onclick);
        editpf_button.setOnClickListener(this::editpf_button_onclick);
        collection_button.setOnClickListener(this::collection_button_onclick);
    }
}
