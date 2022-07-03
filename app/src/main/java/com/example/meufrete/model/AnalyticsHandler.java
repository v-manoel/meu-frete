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
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void itemSelected(String id, String name){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, name);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void customEvent(String eventName ,String id, String value){
        Bundle bundle = new Bundle();
        bundle.putString("key", id);
        bundle.putString("value", value);
        analytics.logEvent(eventName, bundle);
    }
}
