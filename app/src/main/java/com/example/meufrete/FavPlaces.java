package com.example.meufrete;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavPlaces#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavPlaces extends Fragment {
    private View rootView;
    private PlaceCardAdapter placeCardAdapter;
    RecyclerView recyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavPlaces() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavPlaces.
     */
    // TODO: Rename and change types and number of parameters
    public static FavPlaces newInstance(String param1, String param2) {
        FavPlaces fragment = new FavPlaces();
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
        rootView = inflater.inflate(R.layout.fragment_fav_places, container, false);
//        TextView cardTitle = rootView.findViewById(R.id.favPlace_title);
//        TextView cardDesc = rootView.findViewById(R.id.favPlace_desc);
//        Geocoder geocoder;
//        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
//        ArrayList<FavPlaceValue> favPlaceValues= (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));
//        favPlaceDao.close();
//        FavPlaceValue yourAddress = favPlaceValues.get(0);
//        Log.i("Teste2",yourAddress.getAddress().getLatitude() + " " + yourAddress.getAddress().getLongitude());
////        FavPlaceValue yourAddress = new FavPlaceValue();
////        yourAddress.setAlias("Casa");
////        yourAddress.setAddress(this.getContext(),-12.833891f, -38.4747557f);
//        if(yourAddress.getAddress() != null) {
//            cardDesc.setText(yourAddress.getAddress().getThoroughfare() + " - " + yourAddress.getAddress().getSubAdminArea());
//            cardTitle.setText(yourAddress.getAlias());
//        }
        this.setupRecycler();
        return rootView;
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        Log.i("Teste","pass here");
        if(layoutManager == null)
            Log.i("Teste","pass there but is null");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favPlacesRView);
        recyclerView.setLayoutManager(layoutManager);
        Log.i("Teste","pass here too");

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        ArrayList<FavPlaceValue> favPlaceValues= (ArrayList<FavPlaceValue>) new ArrayList(favPlaceDao.all(this.getContext()));
        favPlaceDao.close();
        placeCardAdapter = new PlaceCardAdapter(favPlaceValues);
        recyclerView.setAdapter(placeCardAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
    }

}