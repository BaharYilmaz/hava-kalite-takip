package com.example.deneme.model.City;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList {

    @SerializedName("cityDetail")
    @Expose
    private List<CityDetail> cityDetail = null;

    public List<CityDetail> getCityDetail() {
        return cityDetail;
    }

    public void setCityDetail(List<CityDetail> cityDetail) {
        this.cityDetail = cityDetail;
    }

}