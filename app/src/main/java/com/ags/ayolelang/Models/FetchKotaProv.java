package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class FetchKotaProv {
    private ArrayList<Kota> kotas;
    private ArrayList<Provinsi> provinsis;

    public FetchKotaProv(ArrayList<Kota> kotas, ArrayList<Provinsi> provinsis) {
        this.kotas = kotas;
        this.provinsis = provinsis;
    }

    public ArrayList<Kota> getKotas() {
        return kotas;
    }

    public void setKotas(ArrayList<Kota> kotas) {
        this.kotas = kotas;
    }

    public ArrayList<Provinsi> getProvinsis() {
        return provinsis;
    }

    public void setProvinsis(ArrayList<Provinsi> provinsis) {
        this.provinsis = provinsis;
    }

    @Override
    public String toString() {
        return "FetchKotaProv{" +
                "kotas=" + kotas.size() +
                ", provinsis=" + provinsis.size() +
                '}';
    }
}
