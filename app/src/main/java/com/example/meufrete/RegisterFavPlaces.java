package com.example.meufrete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class RegisterFavPlaces extends Fragment {


    private FavPlaceValue favPlaceValue;
    private TextInputLayout nameInput;
    private TextInputLayout aliasInput;

    public RegisterFavPlaces() {}


    public static RegisterFavPlaces newInstance() {
        return new RegisterFavPlaces();
    }

    public static RegisterFavPlaces newInstance( FavPlaceValue favPlaceValue) {
        RegisterFavPlaces fragment = new RegisterFavPlaces();
        fragment.setFavPlaceValue(favPlaceValue);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_fav_places, container, false);

        if(getArguments() != null)
            this.favPlaceValue = (FavPlaceValue) getArguments().getSerializable("FavPlaceValue");
        nameInput = (TextInputLayout) rootView.findViewById(R.id.favPlace_name);
        aliasInput = (TextInputLayout) rootView.findViewById(R.id.favPlace_alias);
        Button savebBtn = (Button) rootView.findViewById(R.id.saveFavPlace);

        if(favPlaceValue != null){
            Objects.requireNonNull(nameInput.getEditText()).setText(favPlaceValue.prettyAddr());
            Objects.requireNonNull(aliasInput.getEditText()).setText(favPlaceValue.getAlias());
            savebBtn.setText(R.string.btn_edit);
        }else{
            savebBtn.setText(R.string.btn_save);
        }

        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        savebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favPlaceValue == null){
                    favPlaceValue = new FavPlaceValue();
                    favPlaceValue.setAlias(Objects.requireNonNull(aliasInput.getEditText()).getText().toString());
                    favPlaceValue.setAddress(view.getContext() , Objects.requireNonNull(nameInput.getEditText()).getText().toString());
                    favPlaceDao.save(favPlaceValue);
                }else{
                    favPlaceValue.setAlias(Objects.requireNonNull(aliasInput.getEditText()).getText().toString());
                    favPlaceValue.setAddress(view.getContext() , Objects.requireNonNull(nameInput.getEditText()).getText().toString());
                    favPlaceDao.update(favPlaceValue);
                }
                favPlaceDao.close();
                NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
                navController.navigate(R.id.action_registerFavPlaces_to_favPlaces);
            }
        });

        return rootView;
    }

    public FavPlaceValue getFavPlaceValue() {
        return favPlaceValue;
    }

    public void setFavPlaceValue(FavPlaceValue favPlaceValue) {
        this.favPlaceValue = favPlaceValue;
    }
}