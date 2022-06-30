package com.example.meufrete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meufrete.dao.FavPlaceDao;
import com.example.meufrete.model.FavPlaceValue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFavPlaces#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFavPlaces extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FavPlaceValue favPlaceValue;
    private Button savebBtn;
    private View rootView;
    private TextInputLayout nameInput;
    private TextInputLayout aliasInput;

    public FavPlaceValue getFavPlaceValue() {
        return favPlaceValue;
    }

    public void setFavPlaceValue(FavPlaceValue favPlaceValue) {
        this.favPlaceValue = favPlaceValue;
    }


    // TODO: Rename and change types of parameters

    public RegisterFavPlaces() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFavPlaces.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFavPlaces newInstance() {
        RegisterFavPlaces fragment = new RegisterFavPlaces();
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_register_fav_places, container, false);

        this.favPlaceValue = (FavPlaceValue) getArguments().getSerializable("FavPlaceValue");
        nameInput = (TextInputLayout) rootView.findViewById(R.id.favPlace_name);
        aliasInput = (TextInputLayout) rootView.findViewById(R.id.favPlace_alias);
        savebBtn = (Button) rootView.findViewById(R.id.saveFavPlace);
        if(favPlaceValue != null){
            nameInput.getEditText().setText(favPlaceValue.prettyAddr());
            aliasInput.getEditText().setText(favPlaceValue.getAlias());
            savebBtn.setText("Editar");
        }

        FavPlaceDao favPlaceDao = new FavPlaceDao(this.getContext());
        savebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fr = new FavPlaces();
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.navHostFragment, fr);
//                fragmentTransaction.commit();
                if(favPlaceValue == null){
                    favPlaceValue = new FavPlaceValue();
                    favPlaceValue.setAlias(aliasInput.getEditText().getText().toString());
                    favPlaceValue.setAddress(view.getContext() ,nameInput.getEditText().getText().toString());
                    favPlaceDao.save(favPlaceValue);
                }else{
                    favPlaceValue.setAlias(aliasInput.getEditText().getText().toString());
                    favPlaceValue.setAddress(view.getContext() ,nameInput.getEditText().getText().toString());
                    favPlaceDao.update(favPlaceValue);
                }
                favPlaceDao.close();
                NavController navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
                navController.navigate(R.id.action_registerFavPlaces_to_favPlaces);
            }
        });

        return rootView;


    }
}