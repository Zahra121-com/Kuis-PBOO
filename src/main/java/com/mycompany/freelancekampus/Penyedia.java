package com.mycompany.freelancekampus;

// Subclass: pengguna yang menawarkan jasa
public class Penyedia extends Pengguna {
    private String keahlian;

    public Penyedia(String nama, String email, String keahlian) {
        super(nama, email);
        this.keahlian = keahlian;
    }

    public String getKeahlian() { return keahlian; }
    public void setKeahlian(String keahlian) { this.keahlian = keahlian; }

    @Override
    public String getPeran() {
        return "Penyedia Jasa";
    }

    @Override
    public void tampilkanProfil() {
        super.tampilkanProfil();
        System.out.println("Keahlian : " + keahlian);
    }
}
