package com.example.meufrete.model;

import java.util.Map;

public class VehicleValue {
    private int id;
    private String brand;
    private int year;
    private Map<String,String> model;
    private String gasType;
    private int gasCapacity;
    private Map<String,Float> consumption;

    public VehicleValue() {
    }

    public VehicleValue(int id, String brand, int year, Map<String, String> model, String gasType, int gasCapacity, Map<String, Float> consumption) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.model = model;
        this.gasType = gasType;
        this.gasCapacity = gasCapacity;
        this.consumption = consumption;
    }

    public Float getMeanConsuption(){
        Float meanConsuption = 0.0f;
        for(Map.Entry<String,Float> cons : consumption.entrySet()){
            meanConsuption += cons.getValue();
        }
        return meanConsuption / consumption.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<String, String> getModel() {
        return model;
    }

    public void setModel(Map<String, String> model) {
        this.model = model;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public int getGasCapacity() {
        return gasCapacity;
    }

    public void setGasCapacity(int gasCapacity) {
        this.gasCapacity = gasCapacity;
    }

    public Map<String, Float> getConsumption() {
        return consumption;
    }

    public void setConsumption(Map<String, Float> consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        return "VehiclesValue{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                ", model=" + model +
                ", gasType='" + gasType + '\'' +
                ", gasCapacity=" + gasCapacity +
                ", consumption=" + consumption +
                '}';
    }
}
