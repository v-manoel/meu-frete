package com.example.meufrete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meufrete.model.FavPlaceValue;

public class TripDetail extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        TextView text = (TextView) rootView.findViewById(R.id.testid);
        if(getArguments() != null) {
            FavPlaceValue destination = (FavPlaceValue) getArguments().getSerializable("destinationLocation");
            if (destination != null) {
                text.setText(destination.prettyAddr());
            }

            TextView text2 = (TextView) rootView.findViewById(R.id.testid2);
            FavPlaceValue origin = (FavPlaceValue) getArguments().getSerializable("originLocation");
            if (origin != null) {
                text2.setText(origin.prettyAddr());
            }
        }
        return rootView;
    }
}