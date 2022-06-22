package com.example.meufrete;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceCardHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageButton deleteButton;

    public PlaceCardHolder(@NonNull View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.favPlace_title);
        this.description = (TextView) itemView.findViewById(R.id.favPlace_desc);
        this.deleteButton = (ImageButton) itemView.findViewById(R.id.favPlace_delete);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(ImageButton deleteButton) {
        this.deleteButton = deleteButton;
    }


}
