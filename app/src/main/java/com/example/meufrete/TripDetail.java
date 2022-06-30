package com.example.meufrete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meufrete.model.FavPlaceValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static TripDetail newInstance(String param1, String param2) {
        TripDetail fragment = new TripDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        TextView text = (TextView) rootView.findViewById(R.id.testid);
        FavPlaceValue destination = (FavPlaceValue) getArguments().getSerializable("destinationLocation");
        if(destination != null){
            text.setText(destination.prettyAddr());
        }

        TextView text2 = (TextView) rootView.findViewById(R.id.testid2);
        FavPlaceValue origin = (FavPlaceValue) getArguments().getSerializable("originLocation");
        if(origin != null){
            text2.setText(origin.prettyAddr());
        }
        return rootView;
    }
}