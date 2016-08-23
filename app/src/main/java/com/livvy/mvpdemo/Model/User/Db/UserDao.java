package com.livvy.mvpdemo.Model.User.Db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.livvy.mvpdemo.Model.User.Model.User;

import java.util.LinkedList;

/**
 * Created by Finch on 2016/8/22 0022.
 */
public final class UserDao
{
    public static final String AUTHORITY = "com.livvy.provider.userdao";

    private ContentResolver resolver = null;

    public static final class UserColumn implements BaseColumns
    {
        private UserColumn()
        {

        }

        /**
         * The table name offered by this provider
         */
        public static final String TABLE_NAME = "user";

        /*
         * URI definitions
         */

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path parts for the URIs
         */

        /**
         * Path part for the Notes URI
         */
        private static final String PATH_USER = "/users";

        /**
         * Path part for the Note ID URI
         */
        private static final String PATH_USER_ID = "/users/";

        /**
         * 0-relative position of a note ID segment in the path part of a note ID URI
         */
        public static final int NOTE_ID_PATH_POSITION = 1;

        /*Match Code*/
        public static final int ITEM = 1;
        public static final int ITEM_ID = 2;
        public static final int ITEM_POS = 3;

        /*Default sort order*/
        public static final String DEFAULT_SORT_ORDER = "_id asc";

        /**
         * The content URI base for a single note. Callers must append a numeric note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_USER_ID);

        /*Content URI*/
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/item");

        public static final Uri CONTENT_POS_URI = Uri.parse(SCHEME + AUTHORITY + "/pos");
        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.livvy.user";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.livvy.user";


        public static final String COLUMN_NAME_LOGINNAME = "loginname";

        public static final String COLUMN_NAME_SHOWNAME = "showname";

        public static final String COLUMN_NAME_PASSWORD = "password";

        public static final String COLUMN_NAME_LASTLOGINTIME = "lastlogintime";

        public static final String COLUMN_NAME_ISMANUALLOGOUT = "ismanuallogout";

    }

    public UserDao(Context context)
    {
        this.resolver = context.getContentResolver();
    }

    public long insertUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(UserColumn.COLUMN_NAME_LOGINNAME, user.logiName);
        values.put(UserColumn.COLUMN_NAME_SHOWNAME, user.showName);
        values.put(UserColumn.COLUMN_NAME_PASSWORD, user.password);
        values.put(UserColumn.COLUMN_NAME_ISMANUALLOGOUT, user.isManualLogout);
        values.put(UserColumn.COLUMN_NAME_LASTLOGINTIME, user.lastLoginTime);

        Uri uri = resolver.insert(UserColumn.CONTENT_URI, values);
        String itemId = uri.getPathSegments().get(1);

        return Integer.valueOf(itemId).longValue();
    }

    public boolean updateUser(User user)
    {
        Uri uri = ContentUris.withAppendedId(UserColumn.CONTENT_URI, user.id);

        ContentValues values = new ContentValues();
        values.put(UserColumn.COLUMN_NAME_LOGINNAME, user.logiName);
        values.put(UserColumn.COLUMN_NAME_SHOWNAME, user.showName);
        values.put(UserColumn.COLUMN_NAME_PASSWORD, user.password);
        values.put(UserColumn.COLUMN_NAME_ISMANUALLOGOUT, user.isManualLogout);
        values.put(UserColumn.COLUMN_NAME_LASTLOGINTIME, user.lastLoginTime);

        int count = resolver.update(uri, values, null, null);

        return count > 0;
    }

    public boolean removeUser(int id)
    {
        Uri uri = ContentUris.withAppendedId(UserColumn.CONTENT_URI, id);

        int count = resolver.delete(uri, null, null);

        return count > 0;
    }

    public LinkedList<User> getAllUser()
    {
        LinkedList<User> articles = new LinkedList<User>();

        String[] projection = new String[]{
                UserColumn._ID, UserColumn.COLUMN_NAME_LOGINNAME, UserColumn.COLUMN_NAME_SHOWNAME, UserColumn.COLUMN_NAME_PASSWORD,
                UserColumn.COLUMN_NAME_ISMANUALLOGOUT, UserColumn.COLUMN_NAME_LASTLOGINTIME};

        Cursor cursor = resolver.query(UserColumn.CONTENT_URI, projection, null, null, UserColumn.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst())
        {
            do
            {
                int id = cursor.getInt(0);
                String logiName = cursor.getString(1);
                String showName = cursor.getString(2);
                String passWord = cursor.getString(3);
                String LastLoginTime = cursor.getString(5);
                int IsManualLogout = cursor.getInt(4);

                User article = new User(id, logiName, showName, passWord, LastLoginTime, IsManualLogout == 0 ? false : true);
                articles.add(article);
            }
            while (cursor.moveToNext());
        }

        return articles;
    }


//    public Article getArticleById(int id)
//    {
//        Uri uri = ContentUris.withAppendedId(Articles.CONTENT_URI, id);
//
//        String[] projection = new String[]{
//                Articles.ID, Articles.TITLE, Articles.ABSTRACT, Articles.URL};
//
//        Cursor cursor = resolver.query(uri, projection, null, null, Articles.DEFAULT_SORT_ORDER);
//
//        Log.i(LOG_TAG, "cursor.moveToFirst");
//
//        if (!cursor.moveToFirst())
//        {
//            return null;
//        }
//
//        String title = cursor.getString(1);
//        String abs = cursor.getString(2);
//        String url = cursor.getString(3);
//
//        return new Article(id, title, abs, url);
//    }
//
//    public Article getArticleByPos(int pos)
//    {
//        Uri uri = ContentUris.withAppendedId(Articles.CONTENT_POS_URI, pos);
//
//        String[] projection = new String[]{
//                Articles.ID, Articles.TITLE, Articles.ABSTRACT, Articles.URL};
//
//        Cursor cursor = resolver.query(uri, projection, null, null, Articles.DEFAULT_SORT_ORDER);
//        if (!cursor.moveToFirst())
//        {
//            return null;
//        }
//
//        int id = cursor.getInt(0);
//        String title = cursor.getString(1);
//        String abs = cursor.getString(2);
//        String url = cursor.getString(3);
//
//        return new Article(id, title, abs, url);
//    }
}
