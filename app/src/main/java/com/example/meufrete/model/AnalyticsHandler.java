package com.example.meufrete.model;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsHandler {
    private FirebaseAnalytics analytics;
    private static AnalyticsHandler instance = null;

    //Some Custom Event Names
    public static final String DATABASE_UPDATE = "Database Updated";
    public static final String DATABASE_INSERT = "Database Inserted";
    public static final String DATABASE_DELETE = "Database Deleted";
    public static final String BTN_CLICKED = "Button Clicked";


    private AnalyticsHandler(Context context) {
        analytics = FirebaseAnalytics.getInstance(context);
    }

    public static AnalyticsHandler getInstance(Context context){
        if(instance == null){
            instance = new AnalyticsHandler(context);
        }
        return instance;
    }

    public void contentSelected(String id, String type){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id.replaceAll("\\s","_"));
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type.replaceAll("\\s","_"));
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void itemSelected(String id, String name){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, id.replaceAll("\\s","_"));
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, name.replaceAll("\\s","_"));
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void customEvent(String eventName ,String id, String value){
        Bundle bundle = new Bundle();
        bundle.putString(id.replaceAll("\\s","_"),value.replaceAll("\\s","_"));
        analytics.logEvent(eventName, bundle);
    }
}
