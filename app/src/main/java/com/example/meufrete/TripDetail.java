package com.example.meufrete;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.meufrete.adapter.PlaceListAdapter;
import com.example.meufrete.adapter.VehicleListAdapter;
import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.example.meufrete.model.VehicleValue;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class TripDetail extends Fragment {
    private AutoCompleteTextView autoCompleteDestination;
    private AutoCompleteTextView autoCompleteOrigin;
    private AutoCompleteTextView autoCompleteVehicles;
    private ArrayList<VehicleValue> vehicles = null;
    private CheckBox checkTripTwice;
    private CheckBox checkEmptyReturn;
    private CheckBox checkLoadNUnload;
    private SharedPreferences tripPrefs = null;
    private View rootView;
    public TripDetail() {}


    public static TripDetail newInstance() {
        TripDetail fragment = new TripDetail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        navController.navigate(R.id.action_tripDetail_to_splash);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);
        this.initViewComponents();
        this.checkPreferences();
        this.loadVehiclesList();
        this.loadPlacesList();

        Button calculateBtn = rootView.findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreferences();

            }
        });


        return rootView;
    }

    public void initViewComponents() {
        //Checkboxes n Preferences
        checkTripTwice = (CheckBox) rootView.findViewById(R.id.checkTripTwice);
        checkEmptyReturn = (CheckBox) rootView.findViewById(R.id.checkEmptyReturn);
        checkLoadNUnload = (CheckBox) rootView.findViewById(R.id.checkLoadNUnload);
        tripPrefs = this.requireActivity().getSharedPreferences("tripPrefs", MODE_PRIVATE);

        //AutoComplete Selects
        autoCompleteOrigin = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletePlaceOrigin);
        autoCompleteDestination = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompletePlaceDestination);
        autoCompleteVehicles = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteVehicles);

    }

    public void checkPreferences(){
        checkTripTwice.setChecked(tripPrefs.getBoolean("check_trip_twice",false));
        checkEmptyReturn.setChecked(tripPrefs.getBoolean("check_empty_return",false));
        checkLoadNUnload.setChecked(tripPrefs.getBoolean("check_load_n_unload",true));
    }

    public void setPreferences(){
        SharedPreferences.Editor prefsEditor = tripPrefs.edit();
        prefsEditor.putBoolean("check_trip_twice",this.checkTripTwice.isChecked());
        prefsEditor.putBoolean("check_empty_return",this.checkEmptyReturn.isChecked());
        prefsEditor.putBoolean("check_load_n_unload",this.checkLoadNUnload.isChecked());
        prefsEditor.apply();
    }

    public void loadVehiclesList(){
        getParentFragmentManager().setFragmentResultListener("vehicleApiResult", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                vehicles = (ArrayList<VehicleValue>) result.getSerializable("vehicles");
                VehicleListAdapter adapter = new VehicleListAdapter(rootView.getContext(), vehicles);
                autoCompleteVehicles.setAdapter(adapter);
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void loadPlacesList(){
        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        ArrayList<FavPlaceValue> favPlaceValues = (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));
        ArrayList<FavPlaceValue> favPlaceValues2 = (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));

        favPlaceDao.close();



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


        if(getArguments() != null) {
            FavPlaceValue destination = (FavPlaceValue) getArguments().getSerializable("destinationLocation");
            if (destination != null) {
                autoCompleteDestination.setText(destination.prettyAddr());
            }
            FavPlaceValue origin = (FavPlaceValue) getArguments().getSerializable("originLocation");
            if (origin != null) {
                autoCompleteOrigin.setText(origin.prettyAddr());
            }
        }
    }
}