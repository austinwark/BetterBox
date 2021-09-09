package com.sandboxcode.betterbox.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrewModel implements Parcelable {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("job")
    @Expose()
    private String job;

    @SerializedName("profile_path")
    @Expose()
    private String profile_path;

    @SerializedName("department")
    @Expose()
    private String department;

    public CrewModel(int id, String name, String job, String profile_path, String department) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.profile_path = profile_path;
        this.department = department;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(job);
        dest.writeString(profile_path);
        dest.writeString(department);
    }

    protected CrewModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        job = in.readString();
        profile_path = in.readString();
        department = in.readString();
    }

    public static final Creator<CrewModel> CREATOR = new Creator<CrewModel>() {
        @Override
        public CrewModel createFromParcel(Parcel in) {
            return new CrewModel(in);
        }

        @Override
        public CrewModel[] newArray(int size) {
            return new CrewModel[size];
        }
    };

    @Override
    public String toString() {
        return "CrewModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
