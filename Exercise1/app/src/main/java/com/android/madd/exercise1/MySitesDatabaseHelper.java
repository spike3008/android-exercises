package com.android.madd.exercise1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by madd on 2015-01-30.
 */
public class MySitesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MySites.db";
    public static final String SITES_TABLE_NAME = "sites";
    public static final String SITES_COL_ID = "id";
    public static final String SITES_COL_URL = "url";
    public static final String SITES_COL_STATUS = "status";
    public static final String SITES_COL_DATE = "date";

    public MySitesDatabaseHelper(final Context context){
        super(context, DATABASE_NAME, null, 1, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                Toast.makeText(context, context.getString(R.string.connection_to_database_error),
                        Toast.LENGTH_LONG).show();
                Timber.e("Failed to connect");
                Timber.e("Database name: " + DATABASE_NAME);
            }
        });
        Timber.i("Connected successfully");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + SITES_TABLE_NAME +
                " (id integer primary key, url text, status integer, successful boolean, date text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertSite(Site site) {
        Timber.i("Inserting site '%s' into '%s' table", site.getUrl(), SITES_TABLE_NAME );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SITES_COL_URL, site.getUrl());
        contentValues.put(SITES_COL_STATUS, site.getStatus());
        contentValues.put(SITES_COL_DATE, site.getTimeStamp().toString());
        db.insert(SITES_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getSite(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "select * from " + SITES_TABLE_NAME +
                " where " + SITES_COL_ID + "="+id+"";
        return db.rawQuery( query, null );
    }

    public int getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, SITES_TABLE_NAME);
    }

    public boolean updateSite (Site site)
    {
        if (site.getId()!=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SITES_COL_ID, site.getId());
            contentValues.put(SITES_COL_URL, site.getUrl());
            contentValues.put(SITES_COL_STATUS, site.getStatus());
            contentValues.put(SITES_COL_DATE, site.getTimeStamp().toString());
            db.update(SITES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(site.getId())});
            return true;
        }
        return false;
    }

    public Integer deleteSite(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SITES_TABLE_NAME,
                SITES_COL_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteSite(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SITES_TABLE_NAME,
                SITES_COL_ID + " = ? ",
                new String[] { Integer.toString(site.getId()) });
    }

    public ArrayList<Site> getAllSites()
    {
        ArrayList<Site> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SITES_TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            Site site = new Site();
            site.setId(res.getInt(res.getColumnIndex(SITES_COL_ID)));
            site.setUrl(res.getString(res.getColumnIndex(SITES_COL_URL)));
            site.setStatus(res.getInt(res.getColumnIndex(SITES_COL_STATUS)));
            site.setTimeStamp(DateTime.parse(res.getString(res.getColumnIndex(SITES_COL_DATE))));
            list.add(site);
            res.moveToNext();
        }
        return list;
    }

}
