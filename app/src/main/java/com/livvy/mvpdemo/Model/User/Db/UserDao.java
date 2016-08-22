package com.livvy.mvpdemo.Model.User.Db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Finch on 2016/8/22 0022.
 */
public final class UserDao
{
    public static final String AUTHORITY = "com.livvy.provider.userdao";

    public static final class User implements BaseColumns
    {
        private User()
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

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_USER);

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.livvy.user";

        /**
         * The content URI base for a single note. Callers must
         * append a numeric note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE
                = Uri.parse(SCHEME + AUTHORITY + PATH_USER_ID);

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.livvy.user";


        public static final String COLUMN_NAME_LOGINNAME = "loginname";

        public static final String COLUMN_NAME_LASTLOGINTIME = "lastlogintime";

        public static final String COLUMN_NAME_PASSWORD = "password";

        public static final String COLUMN_NAME_SHOWNAME = "showname";

        public static final String COLUMN_NAME_ISMANUALLOGOUT = "ismanuallogout";

    }
}
