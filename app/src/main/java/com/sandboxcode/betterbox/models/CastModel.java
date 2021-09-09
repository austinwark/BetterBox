package com.sandboxcode.betterbox.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastModel implements Parcelable {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("character")
    @Expose()
    private String character;

    @SerializedName("profile_path")
    @Expose()
    private String profile_path;

    @SerializedName("order")
    @Expose()
    private int order;

    public CastModel(int id, String name, String character, String profile_path, int order) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.profile_path = profile_path;
        this.order = order;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(character);
        dest.writeString(profile_path);
        dest.writeInt(order);
    }

    protected CastModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        character = in.readString();
        profile_path = in.readString();
        order = in.readInt();
    }

    public static final Creator<CastModel> CREATOR = new Creator<CastModel>() {
        @Override
        public CastModel createFromParcel(Parcel in) {
            return new CastModel(in);
        }

        @Override
        public CastModel[] newArray(int size) {
            return new CastModel[size];
        }
    };

    @Override
    public String toString() {
        return "CastModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", order=" + order +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
