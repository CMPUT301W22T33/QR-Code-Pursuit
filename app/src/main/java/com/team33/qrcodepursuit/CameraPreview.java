package com.team33.qrcodepursuit;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/* to handle fitting a camera preview in some layout

 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context, Camera cam) {
        super(context);
        camera = cam;

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder hold) {
        try {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(hold);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder hold) {
        // just for implement
    }

    @Override
    public void surfaceChanged(SurfaceHolder hold, int format, int w, int h) {
        if (holder.getSurface() == null) {return;}

        // stop preview
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // stop nothing, it's fine
        }

        // start preview
        try {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
