package com.example.deneme.model.User;

import java.security.PublicKey;

public class Family {

    private String isim;
    private String sehir;
    private String index;

    public Family(String _isim, String _sehir,String _index) {
        this.isim = _isim;
        this.sehir = _sehir;
        this.index = _index;
    }

    public String getName() {
        return isim;
    }

    public String getCity() {
        return sehir;
    }

    public String getIndex() {
        return index;
    }
    public void setIndex(String Index) {
        this.index = Index;
    }


}
