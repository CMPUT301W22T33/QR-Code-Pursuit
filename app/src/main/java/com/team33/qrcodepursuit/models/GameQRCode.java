package com.team33.qrcodepursuit.models;

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

/**
 * class to store and handle QR data from zxing result
 */
public class GameQRCode implements Parcelable {

    private ArrayList<Integer> qrHash;
    private ArrayList<String> comments;
    private Bitmap image;
    private Location location;
    private int score;

    /**
     * using the "raw" qr information, create a visual bitmap
     * @return a bitmap representing the QR code visually
     */
    public static Bitmap getQRImage(String qrText) {
        QRCodeWriter writer = new QRCodeWriter(); // this should be in other class later on
        BitMatrix bitm = null;
        try {
            bitm = writer.encode(qrText, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return MatrixToImageWriter.toBitmap(bitm);
    }

    // for firestore
    public GameQRCode() {}

    /**
     * construct a GameQRCode using Result from qrscanner
     * additionally, calculate the score
     * @param code raw data from scanner result
     */
    public GameQRCode(Result code) {
        // does store original qr very briefly
        qrHash = new ArrayList<Integer>();
        for (byte value : code.getRawBytes()) {
            qrHash.add((int) value);
        }
        comments = new ArrayList<String>();
        location = null;
        image = null;
        score = 0;

        /* calculate score
         * extra good because some data is tossed, original qr not stored
         * n^2 time but qr codes are never going to get large enough
         */
        for (int i = 1; i < qrHash.size(); i++) {
            qrHash.set(i, (qrHash.get(i) * qrHash.get(i)));
        }
        int streak = 1;
        for (int i = 1; i < qrHash.size(); i++) {
            if (qrHash.get(i) > qrHash.get(i-1)) {
                score += streak;
                streak += 1;
            } else { streak = 1; }
        }
    }

    /**
     * create a GameQRCode from its parceled form
     * @param in the parcel to unpack
     */
    protected GameQRCode(Parcel in) {
        comments = in.createStringArrayList();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
        score = in.readInt();
        qrHash = (ArrayList<Integer>) in.readSerializable();
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

    // getters
    public ArrayList<Integer> getQrHash() { return qrHash; }
    public ArrayList<String> getComments() { return comments; }
    public Bitmap getImage() { return image; }
    public Location getLocation() { return location; }
    public int getScore() { return score; }

    public void setImage(Bitmap source) { image = source; }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * pack a GameQRCode into a parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(comments.toArray(new String[0]));
        parcel.writeParcelable(image,0);
        parcel.writeParcelable(location, 0);
        parcel.writeInt(score);
        parcel.writeSerializable(qrHash);

    }
}
