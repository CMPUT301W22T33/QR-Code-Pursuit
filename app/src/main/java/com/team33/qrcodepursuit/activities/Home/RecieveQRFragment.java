package com.team33.qrcodepursuit.activities.Home;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.GameQRCode;

/**
 * handle adding identifying photo and location to GameQRCode
 */
public class RecieveQRFragment extends Fragment {

    private Button addPhotoButton;
    private Button addLocationButton;
    private Button submitButton;
    private Button cancelButton;
    private ImageView qrImage;
    private TextView qrScore;
    private GameQRCode qr;

    private FusedLocationProviderClient fusedLocationClient;

    private ActivityResultLauncher<String> requestPermissionLauncher
            = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // granted, continue
        } else {
            // lol no location
        }
    });

    public RecieveQRFragment() {
        super(R.layout.fragment_scan);
    }

    /**
     * on creation, set up all elements defined in layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_recieveqr, container, false);
        Bundle b = getArguments();
        qr = (GameQRCode) b.getParcelable(ScanFragment.QRKEY);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        addPhotoButton = view.findViewById(R.id.recieveqr_button_addphoto);
        addLocationButton = view.findViewById(R.id.recieveqr_button_addlocation);
        submitButton = view.findViewById(R.id.recieveqr_button_submit);
        cancelButton = view.findViewById(R.id.recieveqr_button_cancel);
        qrImage = view.findViewById(R.id.recieveqr_imageview_qrimage);

        qrImage.setImageBitmap(qr.getQRImage());

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(imageIntent, 1);
                } catch (ActivityNotFoundException e) {
                    // cant find cam app
                }
            }
        });

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add qr code to account here
                goHome();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        return view;
    }

    /**
     * retrieve compressed image (bitmap) from camera app
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            qr.setImage(imageBitmap);
            if (qr.getImage() != null) {
                addPhotoButton.setText("Retake photo");
            }
        }
    }

    private void addLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                qr.setLocation(location);
                                addLocationButton.setEnabled(false);
                            }
                        }
                    });
        } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            addLocationButton.setEnabled(false);
        }
        else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
            addLocation();
        }
    }

    private void goHome() {
        Fragment frag = new ScanFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
    }

}
