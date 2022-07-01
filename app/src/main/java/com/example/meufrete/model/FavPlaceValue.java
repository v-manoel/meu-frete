package com.example.meufrete.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FavPlaceValue implements java.io.Serializable {
    private Long id = null;
    private String alias;
    private Address address;


    public FavPlaceValue(){

    }

    public FavPlaceValue(String alias, Address address){
        this.alias = alias;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Context context, double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            if (addresses.size() > 0) {
                this.address = addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.address = null;
        }
    }

    public void setAddress(Context context, String placeName){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(placeName,1);
            if (addresses.size() > 0) {
                this.address = addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.address = null;
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String addrToString(){
        return this.address.getAddressLine(0)+ ", " +this.address.getLocality()+ " - " +this.address.getAdminArea();
    }

    public String prettyAddr(){
        return this.address.getThoroughfare() + " - " + this.address.getSubAdminArea();
    }

    public String placeAddr(){
        return this.address.getAddressLine(0);
    }

    @Override
    public String toString() {
        return alias;
    }
}
