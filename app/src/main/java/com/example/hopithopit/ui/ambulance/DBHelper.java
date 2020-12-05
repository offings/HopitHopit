package com.example.hopithopit.ui.ambulance;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    final  String TAG = "DBHelper";

    public Context context;

    public static final String TABLE_NAME = "ambulance";
    public static final String ambulance_own = "own";
    public static final String ambulance_type = "type";
    public static final String ambulance_num = "num";
    public static final String ambulance_tel = "tel";
    public static final String ambulance_city = "city";
    public static final String ambulance_district = "district";

    static  final String DB_NAME = "ambulance.db";
    static  final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Create table " + TABLE_NAME + "(");
        stringBuffer.append(ambulance_own + " TEXT, ");
        stringBuffer.append(ambulance_type + " TEXT, ");
        stringBuffer.append(ambulance_num + " TEXT, ");
        stringBuffer.append(ambulance_tel + " TEXT, ");
        stringBuffer.append(ambulance_city + " TEXT, ");
        stringBuffer.append(ambulance_district + " TEXT);");

        Log.d(TAG, stringBuffer.toString());
        db.execSQL(stringBuffer.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*public Cursor getAllData(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }*/
}
