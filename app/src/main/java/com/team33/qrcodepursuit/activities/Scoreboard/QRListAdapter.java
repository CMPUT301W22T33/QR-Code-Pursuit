package com.team33.qrcodepursuit.activities.Scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.team33.qrcodepursuit.R;
import com.team33.qrcodepursuit.models.Comment;

import java.util.ArrayList;


public class QRListAdapter extends BaseAdapter {

    private ArrayList<String> localData;

    public QRListAdapter(ArrayList<String> data) {
        localData = data;
    }

    @Override
    public int getCount() {
        return localData.size();
    }

    @Override
    public Object getItem(int i) {
        return localData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_qr, viewGroup, false);
        View finalView = view;
        FirebaseStorage.getInstance().getReference("qrImages/"+localData.get(i)+".jpg").getBytes(1024*1024*10).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if (task.isSuccessful()) {
                    byte[] bytes = task.getResult();
                    Bitmap bitm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ((ImageView) finalView.findViewById(R.id.qr_image)).setImageBitmap(bitm);
                }
            }
        });

        return view;
    }
}
