package com.leadwinner.apicall.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataDbHelper extends SQLiteOpenHelper
{
    public final static int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Anji.Db";
    public static final String SQL_CREATE_ENTIRES=" CREATE TABLE "+ UserDataclass.TABLE_NAME+" ( "+
                                                                    UserDataclass.ID+" TEXT PRIMARY KEY, "+
                                                                    UserDataclass.NAME+" TEXT, "+
                                                                    UserDataclass.JOB+" TEXT, "+
                                                                    UserDataclass.Status+" TEXT) ";
    public static final String SQL_DELETE_ENTIRES=" DROP TABLE IF EXISTS "+ UserDataclass.TABLE_NAME;

    public UserDataDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
                db.execSQL(SQL_CREATE_ENTIRES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
                db.execSQL(SQL_DELETE_ENTIRES);
    }
}
