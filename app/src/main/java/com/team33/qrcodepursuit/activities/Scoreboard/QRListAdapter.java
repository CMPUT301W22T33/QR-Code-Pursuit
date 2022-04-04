package com.team33.qrcodepursuit.activities.Scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class QRListAdapter extends RecyclerView.Adapter<QRListAdapter.ViewHolder> {

    private ArrayList<String> localData;
    public int clickedPos = -1;

    /**
     * weirdness for recyclerview
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = view.findViewById(R.id.qr_image);
        }

        public ImageView getImageView() { return image; }
    }

    /**
     * make adapter with data
     * @param data to populate views with (image)
     */
    public QRListAdapter(ArrayList<String> data) {
        localData = data;
    }

    // make new comment view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_qr, viewGroup, false);

        return new ViewHolder(view);
    }

    // set view content
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReferenceFromUrl(localData.get(position)).getBytes(1024*1024*10).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if (task.isSuccessful()) {
                    byte[] bytes = task.getResult();
                    Bitmap bitm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    viewHolder.getImageView().setImageBitmap(bitm);
                }
            }
        });

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPos = viewHolder.getAdapterPosition();
            }
        });

    }

    @Override
    public int getItemCount() {
        return localData.size();
    }

}
