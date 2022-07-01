package com.example.meufrete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meufrete.adapter.PlaceCardAdapter;
import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavPlaces#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavPlaces extends Fragment implements PlaceCardAdapter.ItemClickListener {
    private View rootView;
    RecyclerView recyclerView;


    public FavPlaces() {}

    public static FavPlaces newInstance() {
        FavPlaces fragment = new FavPlaces();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fav_places, container, false);

        FloatingActionButton newPlaceBtn = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);
        newPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
                navController.navigate(R.id.action_favPlaces_to_registerFavPlaces);
            }
        });
        this.setupRecycler();
        return rootView;
    }


    private void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favPlacesRView);
        recyclerView.setLayoutManager(layoutManager);

        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        ArrayList<FavPlaceValue> favPlaceValues= (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));
        favPlaceDao.close();
        PlaceCardAdapter placeCardAdapter = new PlaceCardAdapter(favPlaceValues, this);
        recyclerView.setAdapter(placeCardAdapter);
    }

    @Override
    public void onItemClick(FavPlaceValue favPlaceValue) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("FavPlaceValue",favPlaceValue);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        navController.navigate(R.id.action_favPlaces_to_registerFavPlaces,bundle);
    }
}