package com.styephenwilliambuli.testandroidp3l.Mdoels;

import java.io.Serializable;

public class Reservasi implements Serializable {
    private final int idReservasi;
    private final int idMeja;
    private final String namaCustomer;
    private final String sesi;
    private final String tanggal;

    public Reservasi(int idReservasi, int idMeja, String namaCustomer, String sesi, String tanggal) {
        this.idReservasi  = idReservasi;
        this.idMeja       = idMeja;
        this.namaCustomer = namaCustomer;
        this.sesi         = sesi;
        this.tanggal      = tanggal;
    }

    public int getIdReservasi() {
        return idReservasi;
    }

    public int getIdMeja() {
        return idMeja;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public String getSesi() {
        return sesi;
    }

    public String getTanggal() {
        return tanggal;
    }
}
