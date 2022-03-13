package com.team33.qrcodepursuit;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import net.glxn.qrgen.android.MatrixToImageWriter;

import java.util.ArrayList;

/* class to store and handle QR data from zxing result
 */
public class GameQRCode {

    private Result qr;
    private ArrayList<String> comments;
    private Bitmap image;

    public GameQRCode(Result code) {
        qr = code;
    }

    public void addImage(Bitmap source) {
        image = source;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public Bitmap getQRImage() {
        QRCodeWriter writer = new QRCodeWriter(); // this should be in other class later on
        BitMatrix bitm = null;
        try {
            bitm = writer.encode(qr.getText(), BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return MatrixToImageWriter.toBitmap(bitm);
    }
}
