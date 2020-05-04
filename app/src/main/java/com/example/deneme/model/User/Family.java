package com.example.deneme.model.User;

public class Family {

    private String isim;
    private String sehir;

    public Family(String _isim, String _sehir) {
        this.isim = _isim;
        this.sehir = _sehir;
    }

    public String getName() {
        return isim;
    }

    public String getCity() {
        return sehir;
    }
}
