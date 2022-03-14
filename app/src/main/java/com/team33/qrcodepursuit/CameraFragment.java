// i know camera is deprecated but i want to be consistent with the codescanner lib im using
// you actually cannot get here without camera permission so i don't bother with permissions (yet)

package com.team33.qrcodepursuit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    private Camera camera;
    private CameraPreview preview;
    private FrameLayout previewLayout;
    private Button captureButton;

    public CameraFragment() {
        super(R.layout.fragment_camera);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        captureButton = view.findViewById(R.id.fragment_camera_button_capture);

        camera = Camera.open(); // catch exceptions later

        preview = new CameraPreview(getActivity(), camera);

        previewLayout = view.findViewById(R.id.fragment_camera_framelayout_preview);
        previewLayout.addView(preview);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                final Bitmap[] bitm = new Bitmap[1]; // ultra weird
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera cam) {
                        bitm[0] = BitmapFactory.decodeByteArray(data, 0, data.length);
                    }
                });
                bundle.putParcelable("newImage", bitm[0]);
                getActivity().getSupportFragmentManager().setFragmentResult("takeImage", bundle);
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startPreview();
    }

    @Override
    public void onPause() {
        camera.release();
        previewLayout.removeView(preview);
        super.onPause();
    }

    private void startPreview() {
        camera = Camera.open();

        preview = new CameraPreview(getActivity(), camera);

        previewLayout.addView(preview);
    }

}
