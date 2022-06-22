package com.example.meufrete;

import android.annotation.SuppressLint;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meufrete.model.FavPlaceValue;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PlaceCardAdapter extends RecyclerView.Adapter<PlaceCardHolder> {
    private final List<FavPlaceValue> favPlaces;

    public PlaceCardAdapter(List<FavPlaceValue> favPlaces) {
        this.favPlaces = favPlaces;
    }

    @NonNull
    @Override
    public PlaceCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceCardHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favcards, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull PlaceCardHolder holder, int position) {
        FavPlaceValue place = favPlaces.get(position);
        holder.getTitle().setText(place.getAlias());
        holder.getDescription().setText(place.getAddress().getThoroughfare() + " - " + place.getAddress().getSubAdminArea());
        holder.getDeleteButton().setOnClickListener(view->removeItem(position,view));
    }

    @Override
    public int getItemCount() {
        return favPlaces != null ? favPlaces.size() : 0;
    }

    @SuppressLint("ResourceAsColor")
    private void removeItem(int position, View view) {
        Snackbar snackbar = Snackbar.make(view, "Confirmar Exclusão de Registro ?", Snackbar.LENGTH_LONG);
        snackbar.setAction("SIM", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favPlaces.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, favPlaces.size());
            }
        });
        snackbar.show();
    }

    private void insertItem(FavPlaceValue favPlace) {
        favPlaces.add(favPlace);
        notifyItemInserted(getItemCount());
    }

    public void updateList(FavPlaceValue favPlace) {
        insertItem(favPlace);
    }

    private void updateItem(int position, FavPlaceValue modifiedFavPlace) {
        FavPlaceValue favPlace = favPlaces.get(position);
        favPlace = modifiedFavPlace;
        notifyItemChanged(position);
    }
}
