/*
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

https://github.com/yuriy-budiyev/code-scanner

the above applies to the qr scanner library used
------------------------------------------------
the below applies to the qr generator api used

QRGen by kenglxn is licensed under the Apache License, Version 2.0
https://github.com/kenglxn/QRGen
 */

package com.team33.qrcodepursuit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private CodeScanner qrScanner;

    // be sure permissions are granted
    @Override
    protected void onStart() {
        super.onStart();

        int hasCameraPerm = checkSelfPermission(Manifest.permission.CAMERA);

        List<String> permissions = new ArrayList<String>();

        if (hasCameraPerm != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (!permissions.isEmpty()) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        CodeScannerView scannerView = findViewById(R.id.scannerview);
        qrScanner = new CodeScanner(this, scannerView, CodeScanner.CAMERA_BACK);

        qrScanner.setCamera(CodeScanner.CAMERA_BACK);
        qrScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        qrScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        qrScanner.setScanMode(ScanMode.CONTINUOUS);

        // when successfully scanned
        qrScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String rawData = new String(result.getRawBytes(), StandardCharsets.UTF_8);
                        Toast.makeText(CameraActivity.this, rawData, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        qrScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                thrown.printStackTrace();
            }
        });

        // not sure why this is useful
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        qrScanner.startPreview();
    }

    @Override
    protected void onPause() {
        qrScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
