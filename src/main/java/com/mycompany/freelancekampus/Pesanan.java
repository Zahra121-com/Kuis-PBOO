package com.mycompany.freelancekampus;

// Status: Diajukan -> Diterima -> Dikerjakan -> Selesai
public class Pesanan {
    private Jasa jasa;
    private Pemesan pemesan;
    private String brief;
    private String status;

    public Pesanan(Jasa jasa, Pemesan pemesan, String brief) {
        this.jasa = jasa;
        this.pemesan = pemesan;
        this.brief = brief;
        this.status = "Diajukan";
    }

    public Jasa getJasa() { 
        return jasa; 
    }
    public Pemesan getPemesan() {
        return pemesan;
    }
    public String getBrief() { 
        return brief; 
    }
    public String getStatus() { 
        return status;
    }

    public boolean prosesPenyedia() {
        if (status.equals("Diajukan")) {
            status = "Diterima";
            return true;
        } else if (status.equals("Diterima")) {
            status = "Dikerjakan";
            return true;
        }
        return false;
    }

    public boolean selesaikan() {
        if (status.equals("Dikerjakan")) {
            status = "Selesai";
            return true;
        }
        return false;
    }

    public String getRingkas(int nomor) {
        return nomor + ". " + jasa.getJudul() + " | pemesan: " + pemesan.getNama()
                + " | brief: " + brief + " | status: " + status;
    }

    public void tampilkan(int nomor) {
        System.out.println(getRingkas(nomor));
    }
}
