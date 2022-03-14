/*
https://github.com/yuriy-budiyev/code-scanner
MIT License

Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

// also, perhaps make this multipurpose (use for login), and the qrreader dependency isn't needed anymore

package com.team33.qrcodepursuit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanFragment extends Fragment {

    public static String QRKEY = "com.team33.qrcodepursuit.NEWQR";

    private CodeScanner qrScanner;

    private ActivityResultLauncher<String> requestPermissionLauncher
      = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // granted, continue
        } else {
            // some msg i guess
        }
    });

    public ScanFragment() {
        super(R.layout.fragment_scan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // not sure where else to put this
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // all good
        } else { // extra step should be here but meh
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scannerview);
        qrScanner = new CodeScanner(activity, scannerView);

        qrScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                // generate qr object and kick to another frag
                Bundle args = new Bundle();
                GameQRCode newQR = new GameQRCode(result);
                args.putParcelable(QRKEY, newQR);
                RecieveQRFragment newFrag = new RecieveQRFragment();
                newFrag.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, newFrag, "scannedFrag")
                        .commit();
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScanner.startPreview();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        qrScanner.startPreview();
    }

    @Override
    public void onPause() {
        qrScanner.releaseResources();
        super.onPause();
    }

}
