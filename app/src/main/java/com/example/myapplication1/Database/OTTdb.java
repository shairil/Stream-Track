package com.example.myapplication1.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import com.example.myapplication1.Modals.OTT;
import com.example.myapplication1.Parameters.OTTParams;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.*;

public class OTTdb extends SQLiteOpenHelper {

    public OTTdb(Context context){
        super(context, new OTTParams().uid, null, new OTTParams().Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + new OTTParams().TBName
                + "(Platform TEXT PRIMARY KEY, UserName TEXT, Email TEXT, Date TEXT, PlanDetails INTEGER, GroupId TEXT)";
        db.execSQL(create);
    }

    public boolean ifExists(String Platform)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String checkQuery = "SELECT " + new OTTParams().KEY_PLATFORM + " FROM " + new OTTParams().TBName
                + " WHERE " + new OTTParams().KEY_PLATFORM + "= '"+Platform + "'";
        cursor= db.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void addOTT(OTT ott){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(new OTTParams().KEY_PLATFORM, ott.getPlatform() == null ? "" : ott.getPlatform());
        contentValues.put(new OTTParams().KEY_UserName, ott.getUserName()== null ? "" : ott.getUserName());
        contentValues.put(new OTTParams().KEY_Email, ott.getEmail()== null ? "" : ott.getEmail());
        contentValues.put(new OTTParams().KEY_Plan, ott.getPlan() == null ? "":ott.getPlan());
        contentValues.put(new OTTParams().KEY_Date, ott.getStartDate() == null ? "" : ott.getStartDate());
        contentValues.put(new OTTParams().KEY_GroupId, ott.getGroupId() == null ? "" : ott.getGroupId());
        sqLiteDatabase.insert(new OTTParams().TBName, null, contentValues);
        sqLiteDatabase.close();
    }

    public void update(OTT ott){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(new OTTParams().KEY_PLATFORM, ott.getPlatform() == null ? "" : ott.getPlatform());
        contentValues.put(new OTTParams().KEY_UserName, ott.getUserName()== null ? "" : ott.getUserName());
        contentValues.put(new OTTParams().KEY_Email, ott.getEmail()== null ? "" : ott.getEmail());
        contentValues.put(new OTTParams().KEY_Plan, ott.getPlan() == null ? "" : ott.getPlan());
        contentValues.put(new OTTParams().KEY_Date, ott.getStartDate() == null ? "" : ott.getStartDate());
        contentValues.put(new OTTParams().KEY_GroupId, ott.getGroupId() == null ? "" : ott.getGroupId());
        if(ifExists(ott.getPlatform())){
            sqLiteDatabase.update(new OTTParams().TBName, contentValues,
                    new OTTParams().KEY_PLATFORM + "=?", new String[]{ott.getPlatform()});
            sqLiteDatabase.close();
        }
        else{
            addOTT(ott);
        }
    }

    public ArrayList<OTT> getOTTList(){
        ArrayList<OTT> ottList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String select = "SELECT * FROM " + new OTTParams().TBName;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                OTT ott = new OTT();

                ott.setPlatform(cursor.getString(0) == null ? "":cursor.getString(0));
                ott.setUserName(cursor.getString(1) == null ? "":cursor.getString(1));
                ott.setEmail(cursor.getString(2) == null ? "":cursor.getString(2));
                ott.setStartDate(cursor.getString(3) == null ? "":cursor.getString(3));
                ott.setPlan(cursor.getString(4) == null ? "":cursor.getString(4));
                ott.setGroupId(cursor.getString(5) == null ? "":cursor.getString(5));
                if(!ott.getUserName().equals(""))
                    ottList.add(ott);
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return ottList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OTTtb");
        onCreate(db);
    }

    public void drop(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Drop = "DROP TABLE IF EXISTS OTTtb";
        db.execSQL(Drop);
        onCreate(db);
    }

    public int deleteOTT(String platform){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(new OTTParams().TBName, new OTTParams().KEY_PLATFORM + "=?", new String[]{platform});
    }

}
