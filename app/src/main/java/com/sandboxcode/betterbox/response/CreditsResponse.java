package com.sandboxcode.betterbox.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandboxcode.betterbox.models.CastModel;
import com.sandboxcode.betterbox.models.CrewModel;

import java.util.List;

public class CreditsResponse {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("cast")
    @Expose()
    private List<CastModel> castList;

    @SerializedName("crew")
    @Expose()
    private List<CrewModel> crewList;

    public List<CastModel> getCastList() {
        return castList;
    }

    public List<CrewModel> getCrewList() {
        return crewList;
    }

    @Override
    public String toString() {
        return "CreditsResponse{" +
                "id=" + id +
                ", castList=" + castList +
                ", crewList=" + crewList +
                '}';
    }
}
