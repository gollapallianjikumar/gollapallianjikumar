package com.leadwinner.apicall.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataDbHelperFragment extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Anji.Db";
    public static final String SQL_CREATE_QUERIES=" CREATE TABLE "+UserDataFragment.TABLE_NAME+" ( "+
                                                                    UserDataFragment.ID+" TEXT PRIMARY KEY, "+
                                                                    UserDataFragment.NAME+" TEXT, "+
                                                                    UserDataFragment.JOB+" TEXT, "+
                                                                    UserDataFragment.STATUS+" TEXT) ";
    public static final String SQL_DELETE_QUERIES=" DROP TABLE IF EXISTS "+UserDataFragment.TABLE_NAME;

    public UserDataDbHelperFragment(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
            db.execSQL(SQL_CREATE_QUERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_QUERIES);
    }
}
