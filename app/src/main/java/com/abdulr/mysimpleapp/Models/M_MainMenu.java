package com.abdulr.mysimpleapp.Models;

public class M_MainMenu {
    String nama;
    int img;

    public M_MainMenu (String nama, int img) {
        this.nama = nama;
        this.img = img;
    }

    public String getNama() {
        return nama;
    }

    public int getImg() {
        return img;
    }
}
