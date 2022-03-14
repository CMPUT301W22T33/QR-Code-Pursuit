// i know camera is deprecated but i want to be consistent with the codescanner lib im using
// you actually cannot get here without camera permission so i don't bother with permissions (yet)

package com.team33.qrcodepursuit;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
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
                Camera.PictureCallback pic = new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        bitm[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        System.out.println(bitm[0]); // if i don't do this, bitm becomes null for whatever reason
                    }
                };
                camera.takePicture(null, null, pic);
                System.out.println(bitm[0]);
                try {
                    sleep(1000); // extremely cursed

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putParcelable("newImage", bitm[0]);
                getActivity().getSupportFragmentManager().setFragmentResult("takeImage", bundle);
                getActivity().getSupportFragmentManager().popBackStack();
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
        super.onPause();
    }

    private void startPreview() {
        camera = Camera.open();

        previewLayout.removeView(preview);
        preview = new CameraPreview(getActivity(), camera);

        previewLayout.addView(preview);
    }

}
