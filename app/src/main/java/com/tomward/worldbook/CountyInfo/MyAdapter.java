package com.tomward.worldbook.CountyInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tomward.worldbook.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final Context context;
    private final List<Upload> uploads;
    private final String key;
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public MyAdapter(Context context, List<Upload> uploads, String key) {
        this.uploads = uploads;
        this.context = context;
        this.key = key;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.layout_images, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Upload upload = uploads.get(position);

        try {
            StorageReference islandRef = storageRef.child(key + "/" + upload.getUrl());
            final long FIVE_MEGABYTES = 1024 * 1024 * 5;

            islandRef.getBytes(FIVE_MEGABYTES).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(bitmap);
            }).addOnFailureListener(Throwable::printStackTrace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
