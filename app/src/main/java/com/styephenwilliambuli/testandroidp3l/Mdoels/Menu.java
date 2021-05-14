package com.styephenwilliambuli.testandroidp3l.Mdoels;

import java.io.Serializable;

public class Menu implements Serializable {
    private final int idMenu;
    //private final int idBahan;
    private final int jumlahMenuTersedia;
    private final String namaMenu;
    private final String tipeMenu;
    private final String deskripsiMenu;
    private final String fotoMenu;
    private final String satuanMenu;
    private final Double hargaMenu;

    public Menu(int idMenu, /*int idBahan,*/ int jumlahMenuTersedia, String namaMenu,
                String tipeMenu, String deskripsiMenu, String fotoMenu,
                String satuanMenu, Double hargaMenu)
    {
        this.idMenu = idMenu;
        //this.idBahan = idBahan;
        this.jumlahMenuTersedia = jumlahMenuTersedia;
        this.namaMenu = namaMenu;
        this.tipeMenu = tipeMenu;
        this.deskripsiMenu = deskripsiMenu;
        this.fotoMenu = fotoMenu;
        this.satuanMenu = satuanMenu;
        this.hargaMenu = hargaMenu;
    }

    public int getIdMenu() {
        return idMenu;
    }

    /*public int getIdBahan() {
        return idBahan;
    }*/

    public int getJumlahMenuTersedia() {
        return jumlahMenuTersedia;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public String getTipeMenu() {
        return tipeMenu;
    }

    public String getDeskripsiMenu() {
        return deskripsiMenu;
    }

    public String getFotoMenu() {
        return fotoMenu;
    }

    public String getSatuanMenu() {
        return satuanMenu;
    }

    public Double getHargaMenu() {
        return hargaMenu;
    }
}
