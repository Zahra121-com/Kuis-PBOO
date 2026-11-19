package com.mycompany.freelancekampus;

public class Pemesan extends Pengguna {
    public Pemesan(String nama, String email) {
        super(nama, email);
    }

    @Override
    public String getPeran() {
        return "Pemesan";
    }
}
