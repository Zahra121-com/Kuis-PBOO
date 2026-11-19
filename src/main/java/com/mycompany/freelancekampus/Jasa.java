package com.mycompany.freelancekampus;

public class Jasa {
    private String judul;
    private String kategori;
    private double harga;
    private Penyedia penyedia;

    public Jasa(String judul, String kategori, double harga, Penyedia penyedia) {
        this.judul = judul;
        this.kategori = kategori;
        this.harga = harga;
        this.penyedia = penyedia;
    }

    public String getJudul() { 
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul; 
    }
    public String getKategori() {
        return kategori;
    }
    public void setKategori(String kategori) { 
        this.kategori = kategori; 
    }
    public double getHarga() { 
        return harga; 
    }
    public void setHarga(double harga) { 
        this.harga = harga; 
    }
    public Penyedia getPenyedia() {
        return penyedia;
    }

    public String getRingkas(int nomor) {
        return nomor + ". " + judul + " | " + kategori
                + " | Rp" + (long) harga + " | oleh " + penyedia.getNama();
    }

    public void tampilkan(int nomor) {
        System.out.println(getRingkas(nomor));
    }
}
