package com.example.meufrete;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.meufrete.adapter.PlaceListAdapter;
import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class NewTrip extends Fragment {
    private AutoCompleteTextView autoCompleteDestination;
    private AutoCompleteTextView autoCompleteOrigin;
    private View rootView;

    public NewTrip() { }


    public static NewTrip newInstance() {
        NewTrip fragment = new NewTrip();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_trip, container, false);

        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        ArrayList<FavPlaceValue> favPlaceValues = (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));
        ArrayList<FavPlaceValue> favPlaceValues2 = (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));

        favPlaceDao.close();

        autoCompleteOrigin = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletePlaceOrigin);
        autoCompleteDestination = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletePlaceDestination);


        //Get User Last Location
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(rootView.getContext());
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    FavPlaceValue currentPlace = new FavPlaceValue();
                    currentPlace.setAddress(rootView.getContext(),location.getLatitude(),location.getLongitude());
                    currentPlace.setAlias(getString(R.string.current_place));
                    favPlaceValues.add(0,currentPlace);
                    favPlaceValues2.add(0,currentPlace);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                PlaceListAdapter adapter = new PlaceListAdapter(rootView.getContext(), favPlaceValues);
                PlaceListAdapter adapter1 = new PlaceListAdapter(rootView.getContext(), favPlaceValues2);
                autoCompleteOrigin.setAdapter(adapter);
                autoCompleteDestination.setAdapter(adapter1);
            }
        });

        Button newtrip = rootView.findViewById(R.id.newTripBtn);
        newtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavPlaceValue origin = new FavPlaceValue();
                FavPlaceValue destination = new FavPlaceValue();
                origin.setAlias("Origem");
                origin.setAddress(view.getContext(),autoCompleteOrigin.getText().toString());
                destination.setAlias("Destino");
                destination.setAddress(view.getContext(),autoCompleteDestination.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("originLocation",origin);
                bundle.putSerializable("destinationLocation",destination);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
                navController.navigate(R.id.action_new_trip_to_tripDetail,bundle);
            }
        });


        return rootView;
    }
}