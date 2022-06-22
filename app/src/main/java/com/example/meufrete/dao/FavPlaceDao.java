package com.example.meufrete.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.meufrete.model.FavPlaceValue;

import java.util.LinkedList;
import java.util.List;

public class FavPlaceDao extends DatabaseManager{
    public FavPlaceDao(Context context) {
        super(context, "userFavPlaces");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl="CREATE TABLE " + this.tableName + " (id INTEGER PRIMARY KEY, name TEXT, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL);";
        db.execSQL(ddl);
    }

    public void delete(FavPlaceValue userPlace) {
        String[] args= { userPlace.getId().toString() };
        getWritableDatabase().delete(this.tableName,  "id=?", args);
    }

    public void update(FavPlaceValue userPlace) {
        ContentValues values= new ContentValues();
        values.put("name", userPlace.getAlias());
        values.put("latitude", userPlace.getAddress().getLatitude());
        values.put("longitude", userPlace.getAddress().getLongitude());
        getWritableDatabase().update(this.tableName, values,"id=?", new String[]{userPlace.getId().toString()});}

    public void save(FavPlaceValue userPlace) {
        ContentValues values= new ContentValues();
        values.put("name", userPlace.getAlias());
        values.put("latitude", userPlace.getAddress().getLatitude());
        values.put("longitude", userPlace.getAddress().getLongitude());
        getWritableDatabase().insert(this.tableName, null, values);
    }

    public List all(Context context){
        List<FavPlaceValue>  places = new LinkedList<FavPlaceValue>();
        String query = "SELECT  * FROM " + this.tableName + " order by name";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);
        FavPlaceValue place = null;
        if(cursor.moveToFirst()) {
            do {
                place = new FavPlaceValue();
                place.setId(Long.parseLong(cursor.getString(0)));
                place.setAlias(cursor.getString(1));
                place.setAddress(context,cursor.getDouble(2),cursor.getDouble(3));
                places.add(place);
            } while(cursor.moveToNext());
        }
        return places;
    }
}
