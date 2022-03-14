package com.team33.qrcodepursuit;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import net.glxn.qrgen.android.MatrixToImageWriter;

import java.util.ArrayList;

/* class to store and handle QR data from zxing result
 */
public class GameQRCode implements Parcelable {

    private String qrText;
    private ArrayList<String> comments;
    private Bitmap image;
    private Location location;

    public GameQRCode(Result code) {
        qrText = code.getText();
        comments = new ArrayList<String>();
        location = null;
    }

    protected GameQRCode(Parcel in) {
        qrText = in.readString();
        comments = in.createStringArrayList();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<GameQRCode> CREATOR = new Creator<GameQRCode>() {
        @Override
        public GameQRCode createFromParcel(Parcel in) {
            return new GameQRCode(in);
        }

        @Override
        public GameQRCode[] newArray(int size) {
            return new GameQRCode[size];
        }
    };

    public void setImage(Bitmap source) {
        image = source;
    }

    public Bitmap getImage() {
        return image;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public Location getLocation() {
        return location;
    }

    /* @return a bitmap representing the QR code visually
     */
    public Bitmap getQRImage() {
        QRCodeWriter writer = new QRCodeWriter(); // this should be in other class later on
        BitMatrix bitm = null;
        try {
            bitm = writer.encode(qrText, BarcodeFormat.QR_CODE, 300, 300);
            // super weird - the generated QR code looks different but the result is the same
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return MatrixToImageWriter.toBitmap(bitm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(qrText);
        parcel.writeStringArray(comments.toArray(new String[0]));
        parcel.writeParcelable(image,0);
        parcel.writeParcelable(location, 0);
    }
}