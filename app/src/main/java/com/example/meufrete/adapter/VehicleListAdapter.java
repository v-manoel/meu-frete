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
import com.example.meufrete.model.VehicleValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VehicleListAdapter extends ArrayAdapter<VehicleValue> {
    private List<VehicleValue> vehicleValues;
    public VehicleListAdapter(@NonNull Context context, @NonNull List<VehicleValue> vehicleValues) {
        super(context, 0, vehicleValues);
        this.vehicleValues = new ArrayList<>(vehicleValues);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return vehicleFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.vehicles_list_item, parent, false
            );
        }

        TextView textView = convertView.findViewById(R.id.vehicle_description);
        VehicleValue place = getItem(position);

        if(place != null){
            textView.setText(place.getModel().get("description"));
        }
        return convertView;
    }

    private Filter vehicleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<VehicleValue> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(vehicleValues);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(VehicleValue vehicle : vehicleValues){
                    if(Objects.requireNonNull(vehicle.getModel().get("description")).toLowerCase().contains(filterPattern)){
                        suggestions.add(vehicle);
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
            return ((VehicleValue) resultValue).getModel().get("description");
        }
    };
}
