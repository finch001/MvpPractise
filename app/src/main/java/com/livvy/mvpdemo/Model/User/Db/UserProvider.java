package com.livvy.mvpdemo.Model.User.Db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Finch on 2016/8/22 0022.
 */
public class UserProvider extends ContentProvider {
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
    private static final String[] READ_NOTE_PROJECTION = new String[]{UserDao.User._ID,               // Projection position 0, the user's id
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

    private DatabaseHelper databaseHelper;

    public UserProvider() {
        super();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserDao.User.TABLE_NAME);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ContentValues value;

        if (values != null) {
            value = new ContentValues(values);
        } else {
            value = new ContentValues();
        }

        long now = Long.valueOf(System.currentTimeMillis());

        // If the values map doesn't contain the creation date, sets the value to the current time.
        if (value.containsKey(UserDao.User.COLUMN_NAME_SHOWNAME) == false) {
            value.put(UserDao.User.COLUMN_NAME_SHOWNAME, now);
        }

        // If the values map doesn't contain the modification date, sets the value to the current
        // time.
        if (value.containsKey(UserDao.User.COLUMN_NAME_PASSWORD) == false) {
            value.put(UserDao.User.COLUMN_NAME_PASSWORD, now);
        }

        // If the values map doesn't contain a title, sets the value to the default title.
        if (value.containsKey(UserDao.User.COLUMN_NAME_LOGINNAME) == false) {
            value.put(UserDao.User.COLUMN_NAME_LOGINNAME, "");
        }

        // If the values map doesn't contain note text, sets the value to an empty string.
        if (value.containsKey(UserDao.User.COLUMN_NAME_LASTLOGINTIME) == false) {
            value.put(UserDao.User.COLUMN_NAME_LASTLOGINTIME, "");
        }

        if (value.containsKey(UserDao.User.COLUMN_NAME_ISMANUALLOGOUT) == false) {
            value.put(UserDao.User.COLUMN_NAME_ISMANUALLOGOUT, 0);
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long rowId = db.insert(UserDao.User.TABLE_NAME, null, value);

        if (rowId > 0) {
            // Creates a URI with the note ID pattern and the new row ID appended to it.
            Uri userUri = ContentUris.withAppendedId(UserDao.User.CONTENT_ID_URI_BASE, rowId);

            // Notifies observers registered against this provider that the data changed.
            getContext().getContentResolver().notifyChange(userUri, null);
            return userUri;
        }
        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int count;
        count = db.delete(UserDao.User.TABLE_NAME, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Opens the database object in "write" mode.
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count;

        count = db.update(UserDao.User.TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + UserDao.User.TABLE_NAME +
                    " (" + UserDao.User._ID + " INTEGER PRIMARY KEY," + UserDao.User.COLUMN_NAME_LOGINNAME + " VARCHAR, " + UserDao.User.COLUMN_NAME_PASSWORD + " VARCHAR, " + UserDao.User.COLUMN_NAME_SHOWNAME + " VARCHAR, " + UserDao.User.COLUMN_NAME_LASTLOGINTIME + " INTEGER, " + UserDao.User.COLUMN_NAME_ISMANUALLOGOUT + " INTEGER" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Logs that the database is being upgraded
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

            // Kills the table and existing data
            db.execSQL("DROP TABLE IF EXISTS user");

            // Recreates the database with a new version
            onCreate(db);
        }


    }
}
