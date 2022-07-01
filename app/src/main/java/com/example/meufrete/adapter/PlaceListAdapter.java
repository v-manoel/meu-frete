package com.example.meufrete.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meufrete.R;
import com.example.meufrete.model.FavPlaceValue;

import java.util.ArrayList;
import java.util.List;

public class PlaceListAdapter extends ArrayAdapter<FavPlaceValue> {
    private List<FavPlaceValue> favPlaceValues;
    public PlaceListAdapter(@NonNull Context context, @NonNull List<FavPlaceValue> favPlaceValues) {
        super(context, 0, favPlaceValues);
        this.favPlaceValues = new ArrayList<>(favPlaceValues);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.places_list_item, parent, false
            );
        }

        TextView textView = convertView.findViewById(R.id.place_alias);
        FavPlaceValue place = getItem(position);

        if(place != null){
            textView.setText(place.getAlias());
        }
        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FavPlaceValue> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(favPlaceValues);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(FavPlaceValue place : favPlaceValues){
                    if(place.getAlias().toLowerCase().contains(filterPattern)){
                        suggestions.add(place);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((FavPlaceValue) resultValue).placeAddr();
        }
    };
}
