package com.mycompany.freelancekampus;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    Penyedia penyedia = new Penyedia("", "", "");
    Pemesan pemesan = new Pemesan("", "");
    ArrayList<Jasa> daftarJasa = new ArrayList<>();
    ArrayList<Pesanan> daftarPesanan = new ArrayList<>();

   
    ListView<String> jasaPenyedia = new ListView<>();
    ListView<String> pesananPenyedia = new ListView<>();
    ListView<String> jasaPemesan = new ListView<>();
    ListView<String> pesananPemesan = new ListView<>();

    Scene scene;
    VBox halamanDepan, halamanPenyedia, halamanPemesan;

    @Override
    public void start(Stage stage) {
        halamanDepan = buatHalamanDepan();
        halamanPenyedia = buatHalamanPenyedia();
        halamanPemesan = buatHalamanPemesan();

        scene = new Scene(halamanDepan, 700, 680);
        stage.setTitle("FreelanceKampus - Zahra Chintya Bela (2517052012)");
        stage.setScene(scene);
        stage.show();
    }

    private VBox buatHalamanDepan() {
        Button btnPenyedia = new Button("Masuk sebagai Penyedia");
        Button btnPemesan = new Button("Masuk sebagai Pemesan");
        btnPenyedia.setPrefWidth(240);
        btnPemesan.setPrefWidth(240);

        btnPenyedia.setOnAction(e -> scene.setRoot(halamanPenyedia));
        btnPemesan.setOnAction(e -> scene.setRoot(halamanPemesan));

        VBox box = new VBox(15, judulBesar("FREELANCEKAMPUS"),
                new Label("Marketplace Jasa Mahasiswa"),
                new Label("Masuk sebagai:"), btnPenyedia, btnPemesan);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        return box;
    }

   
    private VBox buatHalamanPenyedia() {
        TextField nama = new TextField();
        TextField email = new TextField();
        TextField keahlian = new TextField();
        Button btnProfil = new Button("Simpan Profil");

        TextField judul = new TextField();
        TextField kategori = new TextField();
        TextField harga = new TextField();
        Button btnTambah = new Button("Tambah Jasa");

        Button btnProses = new Button("Proses Pesanan Terpilih");
        Button btnKembali = new Button("< Kembali");

        btnKembali.setOnAction(e -> scene.setRoot(halamanDepan));

        btnProfil.setOnAction(e -> {
            if (nama.getText().trim().isEmpty()) {
                alert(Alert.AlertType.WARNING, "Nama tidak boleh kosong.");
                return;
            }
            penyedia.setNama(nama.getText().trim());
            penyedia.setEmail(email.getText().trim());
            penyedia.setKeahlian(keahlian.getText().trim());
            alert(Alert.AlertType.INFORMATION, "Profil penyedia tersimpan.");
        });

        btnTambah.setOnAction(e -> {
            if (!penyedia.isProfilTerisi()) {
                alert(Alert.AlertType.WARNING, "Simpan profil dulu.");
                return;
            }
            if (judul.getText().trim().isEmpty() || kategori.getText().trim().isEmpty()
                    || harga.getText().trim().isEmpty()) {
                alert(Alert.AlertType.WARNING, "Judul, kategori, dan harga wajib diisi.");
                return;
            }
            try {
                int h = Integer.parseInt(harga.getText().trim());
                if (h <= 0) {
                    alert(Alert.AlertType.WARNING, "Harga harus lebih dari 0.");
                    return;
                }
                daftarJasa.add(new Jasa(judul.getText().trim(), kategori.getText().trim(), h, penyedia));
                judul.clear();
                kategori.clear();
                harga.clear();
                refresh();
            } catch (NumberFormatException ex) {
                alert(Alert.AlertType.ERROR, "Harga harus berupa angka, contoh: 50000.");
            }
        });

        btnProses.setOnAction(e -> {
            int i = pesananPenyedia.getSelectionModel().getSelectedIndex();
            if (i < 0) {
                alert(Alert.AlertType.WARNING, "Pilih pesanan di daftar dulu.");
            } else if (!daftarPesanan.get(i).prosesPenyedia()) {
                alert(Alert.AlertType.WARNING, "Pesanan tidak bisa diproses pada status ini.");
            }
            refresh(pesananPenyedia, i);
        });

        jasaPenyedia.setPrefHeight(120);
        pesananPenyedia.setPrefHeight(120);

        VBox box = new VBox(8,
                btnKembali,
                judulBesar("PENYEDIA JASA"),
                bagian("Profil"),
                form(new String[]{"Nama", "Email", "Keahlian"}, nama, email, keahlian),
                btnProfil,
                bagian("Tambah Jasa"),
                form(new String[]{"Judul", "Kategori", "Harga (Rp)"}, judul, kategori, harga),
                btnTambah,
                bagian("Jasa Saya"), jasaPenyedia,
                bagian("Pesanan Masuk"), pesananPenyedia, btnProses);
        box.setPadding(new Insets(15));
        return box;
    }

    private VBox buatHalamanPemesan() {
        TextField nama = new TextField();
        TextField email = new TextField();
        Button btnProfil = new Button("Simpan Profil");

        TextField brief = new TextField();
        Button btnPesan = new Button("Pesan Jasa Terpilih");

        Button btnSelesai = new Button("Selesaikan Pesanan Terpilih");
        Button btnKembali = new Button("< Kembali");

        btnKembali.setOnAction(e -> scene.setRoot(halamanDepan));

        btnProfil.setOnAction(e -> {
            if (nama.getText().trim().isEmpty()) {
                alert(Alert.AlertType.WARNING, "Nama tidak boleh kosong.");
                return;
            }
            pemesan.setNama(nama.getText().trim());
            pemesan.setEmail(email.getText().trim());
            alert(Alert.AlertType.INFORMATION, "Profil pemesan tersimpan.");
        });

        btnPesan.setOnAction(e -> {
            int i = jasaPemesan.getSelectionModel().getSelectedIndex();
            if (!pemesan.isProfilTerisi()) {
                alert(Alert.AlertType.WARNING, "Simpan profil dulu.");
            } else if (i < 0) {
                alert(Alert.AlertType.WARNING, "Pilih jasa di daftar dulu.");
            } else if (brief.getText().trim().isEmpty()) {
                alert(Alert.AlertType.WARNING, "Brief kebutuhan wajib diisi.");
            } else {
                daftarPesanan.add(new Pesanan(daftarJasa.get(i), pemesan, brief.getText().trim()));
                brief.clear();
                refresh();
            }
        });

        btnSelesai.setOnAction(e -> {
            int i = pesananPemesan.getSelectionModel().getSelectedIndex();
            if (i < 0) {
                alert(Alert.AlertType.WARNING, "Pilih pesanan di daftar dulu.");
            } else if (!daftarPesanan.get(i).selesaikan()) {
                alert(Alert.AlertType.WARNING, "Belum bisa diselesaikan. Status: "
                        + daftarPesanan.get(i).getStatus());
            }
            refresh(pesananPemesan, i);
        });

        jasaPemesan.setPrefHeight(130);
        pesananPemesan.setPrefHeight(130);

        VBox box = new VBox(8,
                btnKembali,
                judulBesar("PEMESAN"),
                bagian("Profil"),
                form(new String[]{"Nama", "Email"}, nama, email),
                btnProfil,
                bagian("Daftar Jasa"), jasaPemesan,
                new HBox(10, new Label("Brief"), brief, btnPesan),
                bagian("Pesanan Saya"), pesananPemesan, btnSelesai);
        box.setPadding(new Insets(15));
        return box;
    }

    private GridPane form(String[] label, TextField... isi) {
        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(6);
        for (int i = 0; i < isi.length; i++) {
            g.addRow(i, new Label(label[i]), isi[i]);
        }
        return g;
    }

    private Label bagian(String teks) {
        Label l = new Label(teks);
        l.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return l;
    }

    private Label judulBesar(String teks) {
        Label l = new Label(teks);
        l.setStyle("-fx-font-weight: bold; -fx-font-size: 22px;");
        return l;
    }

    private void alert(Alert.AlertType tipe, String pesan) {
        Alert a = new Alert(tipe);
        a.setHeaderText(null);
        a.setContentText(pesan);
        a.showAndWait();
    }

    // Isi ulang semua daftar dari data terbaru
    private void refresh() {
        ArrayList<String> teksJasa = new ArrayList<>();
        for (int i = 0; i < daftarJasa.size(); i++) {
            teksJasa.add(daftarJasa.get(i).getRingkas(i + 1));
        }
        ArrayList<String> teksPesanan = new ArrayList<>();
        for (int i = 0; i < daftarPesanan.size(); i++) {
            teksPesanan.add(daftarPesanan.get(i).getRingkas(i + 1));
        }
        jasaPenyedia.getItems().setAll(teksJasa);
        jasaPemesan.getItems().setAll(teksJasa);
        pesananPenyedia.getItems().setAll(teksPesanan);
        pesananPemesan.getItems().setAll(teksPesanan);
    }

   
    private void refresh(ListView<String> list, int pilih) {
        refresh();
        if (pilih >= 0) {
            list.getSelectionModel().select(pilih);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
