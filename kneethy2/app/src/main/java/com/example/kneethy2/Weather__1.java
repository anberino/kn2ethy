package com.example.kneethy2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather__1 {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("icon")
    @Expose
    public String icon;

}
