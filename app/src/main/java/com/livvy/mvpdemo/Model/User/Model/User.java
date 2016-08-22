package com.livvy.mvpdemo.Model.User.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Finch on 2016/8/22 0022.
 */
public class User implements Parcelable, Serializable
{
    private static final long serialVersionUID = -1536387571039029761L;

    public int id;
    public String LogiName;

    public String ShowName;

    public String password;

    public String lastLoginTime;

    public boolean isManualLogout;


    public User()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.LogiName);
        dest.writeString(this.ShowName);
        dest.writeString(this.password);
        dest.writeString(this.lastLoginTime);
        dest.writeByte(this.isManualLogout ? (byte)1 : (byte)0);
    }

    protected User(Parcel in)
    {
        this.id = in.readInt();
        this.LogiName = in.readString();
        this.ShowName = in.readString();
        this.password = in.readString();
        this.lastLoginTime = in.readString();
        this.isManualLogout = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel source)
        {
            return new User(source);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };
}
