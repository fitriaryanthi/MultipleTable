package com.example.dosen.Features.DosenCRUD.ShowDosenList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dosen.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView dosenNameTextView;
    TextView dosenNipTextView;
    TextView dosenPhoneTextView;
    TextView dosenEmailTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        dosenNipTextView = itemView.findViewById(R.id.dosenNipTextView);
        dosenNameTextView = itemView.findViewById(R.id.dosenNameTextView);
        dosenPhoneTextView = itemView.findViewById(R.id.dosenPhoneTextView);
        dosenEmailTextView = itemView.findViewById(R.id.dosenEmailTextView);

        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);

    }
}
