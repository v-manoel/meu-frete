package com.example.meufrete.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE = "MeuFrete";
    private static final int VERSAO = 1;
    protected String tableName;

    public DatabaseManager(Context context, String tableName){
        super(context, DATABASE, null, VERSAO);
        this.tableName = tableName;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String ddl="DROP TABLE IF EXISTS " + this.tableName;
        db.execSQL(ddl);
        onCreate(db);
    }

    public void dropAll(){
        String[] args= { "0" };
        getWritableDatabase().delete(this.tableName,  "id>?",args);
    }
}
