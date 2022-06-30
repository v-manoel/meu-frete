package com.example.meufrete;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link new_trip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class new_trip extends Fragment {
    private AutoCompleteTextView autoCompleteDestination;
    private AutoCompleteTextView autoCompleteOrigin;

    private View rootView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public new_trip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment new_trip.
     */
    // TODO: Rename and change types and number of parameters
    public static new_trip newInstance(String param1, String param2) {
        new_trip fragment = new new_trip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
                    currentPlace.setAlias("Local Atual");
                    favPlaceValues.add(0,currentPlace);
                    favPlaceValues2.add(0,currentPlace);
                    Log.i("myLocation", location.getLatitude() + " " + location.getLongitude());
                }else{
                    Log.i("myLocation","location is null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("myLocation", "Falha no listener");
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
                NavController navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
                navController.navigate(R.id.action_new_trip_to_tripDetail,bundle);
            }
        });


        return rootView;
    }
}