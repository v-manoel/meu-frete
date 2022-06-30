package com.example.meufrete;

import android.annotation.SuppressLint;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PlaceCardAdapter extends RecyclerView.Adapter<PlaceCardHolder> {
    private final List<FavPlaceValue> favPlaces;
    private ItemClickListener clickListener;

    public PlaceCardAdapter(List<FavPlaceValue> favPlaces, ItemClickListener clicklistener) {

        this.favPlaces = favPlaces;
        this.clickListener = clicklistener;
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
        holder.getDescription().setText(place.prettyAddr());
        holder.getDeleteButton().setOnClickListener(view->removeItem(position,view));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(place);
            }
        });
//        holder.getCard().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new RegisterFavPlaces();
//                FragmentManager fm = activity.getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.navHostFragment, myFragment);
//                FrameLayout fl = activity.findViewById(R.id.navHostFragment);
//                fl.removeAllViews();
//                fragmentTransaction.commit();
////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, myFragment).commit();
//            }
//        });
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
                FavPlaceDao favPlaceDao = new FavPlaceDao(v.getContext());
                favPlaceDao.delete(favPlaces.get(position));
                favPlaces.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, favPlaces.size());
                favPlaceDao.close();
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

    public interface ItemClickListener {
        public void onItemClick(FavPlaceValue favPlaceValue);
    }
}
