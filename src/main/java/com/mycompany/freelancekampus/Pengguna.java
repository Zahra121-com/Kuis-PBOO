package com.mycompany.freelancekampus;


public class Pengguna {
    private String nama;
    private String email;

    public Pengguna(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }

    public String getNama() { 
        return nama; 
    }
    public void setNama(String nama) {
        this.nama = nama; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isProfilTerisi() {
        return !nama.isEmpty();
    }

    public String getPeran() {
        return "Pengguna";
    }

    public void tampilkanProfil() {
        System.out.println("Nama  : " + nama);
        System.out.println("Peran : " + getPeran());
        System.out.println("Email : " + email);
    }
}
