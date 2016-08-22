package com.livvy.mvpdemo.Model.User.Db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Finch on 2016/8/22 0022.
 */
public class UserProvider extends ContentProvider
{
    private static final String TAG = UserProvider.class.getSimpleName();

    /**
     * The database that the provider uses as its underlying data store
     */
    private static final String DATABASE_NAME = "user.db";

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * A projection map used to select columns from the database
     */
    private static HashMap<String, String> projectionMap;

    /**
     * Standard projection for the interesting columns of a normal note.
     */
    private static final String[] READ_NOTE_PROJECTION = new String[]{
            UserDao.User._ID,               // Projection position 0, the user's id
            UserDao.User.COLUMN_NAME_LOGINNAME,  // Projection position 1, the login name's content
            UserDao.User.COLUMN_NAME_ISMANUALLOGOUT, // Projection position 2, the isManualLogout
            UserDao.User.COLUMN_NAME_LASTLOGINTIME, // Projection position 3, the lastLogintime
            UserDao.User.COLUMN_NAME_PASSWORD,// Projection position 4, the password
            UserDao.User.COLUMN_NAME_SHOWNAME};// Projection position 5, the showname

    private static final int USER_ID = 0;
    private static final int USER_LOGINNAME = 1;
    private static final int USER_ISMANUALLOGOUT = 2;
    private static final int USER_LASTLOGINTIME = 3;
    private static final int USER_PASSWORD = 4;
    private static final int USER_SHOWNAME = 5;


    public UserProvider()
    {
        super();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public boolean onCreate()
    {
        return false;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
