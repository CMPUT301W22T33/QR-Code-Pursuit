package com.team33.qrcodepursuit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_recieveqr, container, false);
        Bundle b = getArguments();
        qr = (GameQRCode) b.getParcelable(ScanFragment.QRKEY);

        getActivity().getSupportFragmentManager().setFragmentResultListener("takeImage", getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(String requestKey, Bundle bundle) {
                Bitmap bitm = bundle.getParcelable("newImage");
                qr.setImage(bitm);
            }
        });

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
                Fragment newFrag = new CameraFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, newFrag, "scannedFrag")
                        .addToBackStack("qrResult").commit();
            }
        });

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
                if (qr.getLocation() != null) {
                    addLocationButton.setEnabled(false);
                }
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

    /* tries to add location to the qr code
     */
    private void addLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                qr.setLocation(location);
                            }
                        }
                    });
        } else { // extra step should be here but meh
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
            addLocation();
        }
    }

    private void goHome() {
        Fragment frag = new ScanFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
    }

}
