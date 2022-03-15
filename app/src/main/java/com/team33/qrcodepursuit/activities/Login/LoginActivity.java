package com.team33.qrcodepursuit.activities.Login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team33.qrcodepursuit.activities.MainActivity;
import com.team33.qrcodepursuit.R;

/**
 * default entry point to app upon launch
 * login to user account using FirebaseAuth
 * if already logged in -> switch to MainActivity
 */

public class LoginActivity extends AppCompatActivity
{
    // FirebaseAuth object
    private FirebaseAuth mAuth;
    // gui elements
    private TextInputEditText login_email_field;
    private TextInputEditText login_passw_field;
    private Button            login_button;
    private Button qr_login_button;
    private TextView          forgotpw_link;
    private TextView          makeacct_link;
    private TextView          prvpolicy_link;

    private void gotoMainActivity(FirebaseUser user) {
        Intent itt = new Intent(LoginActivity.this, MainActivity.class);
        itt.putExtra("user", user);
        startActivity(itt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        this.mAuth = FirebaseAuth.getInstance();
        // link gui elements
        login_email_field  = findViewById(R.id.login_email_TextInputEditText);
        login_passw_field  = findViewById(R.id.login_passw_TextInputEditText);
        login_button = findViewById(R.id.login_button);
        qr_login_button = findViewById(R.id.qr_login_button);
        forgotpw_link = findViewById(R.id.forgotpw_link);
        makeacct_link = findViewById(R.id.makeacct_link);
        prvpolicy_link = findViewById(R.id.prvpolicy_link);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        // if user already signed in, switch to MainActivity
        if(currentUser != null)
            gotoMainActivity(currentUser);
        // log in with email+pw
        login_button.setOnClickListener(view -> {
            Editable email = login_email_field.getText();
            Editable passw = login_passw_field.getText(); // todo: seems pretty insecure
            if (email == null || passw == null) return;
            mAuth.signInWithEmailAndPassword(email.toString(), passw.toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // switch to MainActivity
                            Log.d(TAG, "signInWithEmail:success");
                            gotoMainActivity(mAuth.getCurrentUser());
                        }
                        else {
                            email.clear();
                            passw.clear();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // login with QR
        qr_login_button.setOnClickListener(view -> {
            // todo: login with QR fragment
        });

        // recover password
        forgotpw_link.setOnClickListener(view -> {
            // todo: recover password fragment
        });

        // view privacy policy
        prvpolicy_link.setOnClickListener(view -> {
            // todo: link to privacy policy
        });

        // create new account
        makeacct_link.setOnClickListener(view -> {
            // todo: account create fragment
        });

    }
}
