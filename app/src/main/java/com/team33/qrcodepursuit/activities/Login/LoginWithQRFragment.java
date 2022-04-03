package com.team33.qrcodepursuit.activities.Login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;
import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.GameQRCode;

public class LoginWithQRFragment extends Fragment {

    private CodeScanner qrScanner;
    private Button backButton;
    private FirebaseAuth auth;
    private NavController navctrl;

    public LoginWithQRFragment() {
        super(R.layout.fragment_loginwithqr);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginwithqr, container, false);
        Activity activity = getActivity();
        CodeScannerView scannerView = view.findViewById(R.id.scannerview);
        auth = FirebaseAuth.getInstance();
        qrScanner = new CodeScanner(activity, scannerView);

        qrScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                auth.signInWithCustomToken(result.getText())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    navctrl.navigate(R.id.action_loginFragment_to_mainActivity);
                                } else {
                                    Toast t = Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT);
                                    t.show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
