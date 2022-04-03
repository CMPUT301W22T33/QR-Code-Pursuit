package com.team33.qrcodepursuit.activities.Scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.GameQRCode;

/**
 * fragment to view qr code info that already exists
 */
public class ViewQRFragment extends Fragment {

    private ImageView qrImage;
    private TextView scoreView;
    private TextView timesScannedView;
    private TextView creatorView;
    private RecyclerView commentView;
    private EditText commentEdit;
    private Button addCommentButton;

    private GameQRCode qr;

    public ViewQRFragment() { super(R.layout.fragment_viewqr); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_recieveqr, container, false);

        qrImage = view.findViewById(R.id.viewqr_imageview_qrimage);
        scoreView = view.findViewById(R.id.viewqr_textview_score);
        timesScannedView = view.findViewById(R.id.viewqr_textview_timesscanned);
        creatorView = view.findViewById(R.id.viewqr_textview_creator);
        commentView = view.findViewById(R.id.viewqr_recyclerview_comments);
        commentEdit = view.findViewById(R.id.viewqr_edittext_commenttext);
        addCommentButton = view.findViewById(R.id.viewqr_button_addcomment);

        // bundle should contain QR id in firebase
        Bundle b = getArguments();
        //qr = b.getParcelable();

        return view;
    }

}
