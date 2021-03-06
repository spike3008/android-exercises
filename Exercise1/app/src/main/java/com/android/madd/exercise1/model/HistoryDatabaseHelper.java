package com.android.madd.exercise1.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.android.madd.exercise1.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

import timber.log.Timber;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MySites.db";
    private static final String SITES_TABLE_NAME = "sites";
    private static final String SITES_COL_ID = "id";
    private static final String SITES_COL_URL = "url";
    private static final String SITES_COL_STATUS = "status";
    private static final String SITES_COL_DATE = "date";
    private static final String SITES_COL_SUCCESSFUL = "successful";

    public HistoryDatabaseHelper(final Context context) {
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
                " (id integer primary key, url text, status integer, successful boolean, date long)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertSite(UrlHistoryItem urlHistoryItem) {
        Timber.i("Inserting site '%s' into '%s' table", urlHistoryItem.getUrl(), SITES_TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SITES_COL_URL, urlHistoryItem.getUrl());
        contentValues.put(SITES_COL_STATUS, urlHistoryItem.getStatus());
        contentValues.put(SITES_COL_SUCCESSFUL, urlHistoryItem.isSuccessful());
        contentValues.put(SITES_COL_DATE, urlHistoryItem.getTimeStamp().toString());
        db.insert(SITES_TABLE_NAME, null, contentValues);
    }

    public Cursor getSite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + SITES_TABLE_NAME +
                " where " + SITES_COL_ID + "=" + id + "";
        return db.rawQuery(query, null);
    }

    public int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, SITES_TABLE_NAME);
    }

    public boolean updateSite(UrlHistoryItem urlHistoryItem) {
        if (urlHistoryItem.getId() != 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SITES_COL_ID, urlHistoryItem.getId());
            contentValues.put(SITES_COL_URL, urlHistoryItem.getUrl());
            contentValues.put(SITES_COL_STATUS, urlHistoryItem.getStatus());
            contentValues.put(SITES_COL_DATE, urlHistoryItem.getTimeStamp().toString());
            db.update(SITES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(urlHistoryItem.getId())});
            return true;
        }
        return false;
    }

    public Integer deleteSite(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SITES_TABLE_NAME,
                SITES_COL_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteSite(UrlHistoryItem urlHistoryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SITES_TABLE_NAME,
                SITES_COL_ID + " = ? ",
                new String[]{Integer.toString(urlHistoryItem.getId())});
    }

    public ArrayList<UrlHistoryItem> getAllSites() {
        UniqueItemsList list = new UniqueItemsList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from " + SITES_TABLE_NAME, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            UrlHistoryItem urlHistoryItem = new UrlHistoryItem();
            urlHistoryItem.setId(result.getInt(result.getColumnIndex(SITES_COL_ID)));
            urlHistoryItem.setUrl(result.getString(result.getColumnIndex(SITES_COL_URL)));
            urlHistoryItem.setStatus(result.getInt(result.getColumnIndex(SITES_COL_STATUS)));
            urlHistoryItem.setTimeStamp(DateTime.parse(
                    result.getString(result.getColumnIndex(SITES_COL_DATE))));
            list.add(urlHistoryItem);
            result.moveToNext();
        }
        result.close();
        return list;
    }

    public UniqueItemsList getNewestSites() {
        UniqueItemsList list = new UniqueItemsList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(
            /* FROM */ SITES_TABLE_NAME,
            /* SELECT */ new String[]{"*", "MAX(" + SITES_COL_DATE + ") AS " + SITES_COL_DATE},
            /* WHERE */ null,
            /* WHERE args */ null,
            /* GROUP BY */ SITES_COL_URL,
            /* HAVING */ null,
            /* ORDER BY */ SITES_COL_DATE + " DESC"
        );
        result.moveToFirst();
        while (!result.isAfterLast()) {
            UrlHistoryItem urlHistoryItem = new UrlHistoryItem();
            urlHistoryItem.setId(result.getInt(result.getColumnIndex(SITES_COL_ID)));
            urlHistoryItem.setUrl(result.getString(result.getColumnIndex(SITES_COL_URL)));
            urlHistoryItem.setStatus(result.getInt(result.getColumnIndex(SITES_COL_STATUS)));
            urlHistoryItem.setTimeStamp(DateTime.parse(
                    result.getString(result.getColumnIndex(SITES_COL_DATE))));
            list.add(urlHistoryItem);
            result.moveToNext();
        }
        result.close();
        return list;
    }

}
