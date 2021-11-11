package com.leadwinner.apicall.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GetApiDataDbHelper extends SQLiteOpenHelper
{
    public final static int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Anji.Db";
    public static final String SQL_CREATE_QUERIES=" CREATE TABLE "+GetApiData.TABLE_NAME+" ( "+
                                                                    GetApiData.ID+" TEXT, "+
                                                                    GetApiData.NAME+ " TEXT, "+
                                                                    GetApiData.COLOR+" TEXT, "+
                                                                    GetApiData.PANTONE_VALUE+" TEXT,"+
                                                                    GetApiData.YEAR+" TEXT) ";
    public static final String SQL_DELETE_QUERIES=" DROP TABLE IF EXISTS "+GetApiData.TABLE_NAME;

    public GetApiDataDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_QUERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUERIES);
    }
}
