package id.digitalks.pengaduanpdam.Model;

public class ModelData {
    String id_pengaduan, nama, no_sambung, alamat, pengaduan, penyelesaian_pengaduan, id_petugas, status, created_at, updated_at, tanggal_pengaduan, id, meter, bulan_meter, tahun_meter;

    public ModelData(){}

    public ModelData(String id_pengaduan, String nama, String no_sambung, String alamat, String pengaduan, String penyelesaian_pengaduan, String id_petugas, String status, String created_at, String updated_at, String tanggal_pengaduan, String id, String meter, String bulan_meter, String tahun_meter) {
        this.id_pengaduan = id_pengaduan;
        this.nama = nama;
        this.no_sambung = no_sambung;
        this.alamat = alamat;
        this.pengaduan = pengaduan;
        this.penyelesaian_pengaduan = penyelesaian_pengaduan;
        this.id_petugas = id_petugas;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tanggal_pengaduan = tanggal_pengaduan;
        this.id = id;
        this.meter = meter;
        this.bulan_meter = bulan_meter;
        this.tahun_meter = tahun_meter;
    }

    public String getBulan_meter() {
        return bulan_meter;
    }

    public void setBulan_meter(String bulan_meter) {
        this.bulan_meter = bulan_meter;
    }

    public String getTahun_meter() {
        return tahun_meter;
    }

    public void setTahun_meter(String tahun_meter) {
        this.tahun_meter = tahun_meter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getTanggal_pengaduan() {
        return tanggal_pengaduan;
    }

    public void setTanggal_pengaduan(String tanggal_pengaduan) {
        this.tanggal_pengaduan = tanggal_pengaduan;
    }

    public String getId_pengaduan() {
        return id_pengaduan;
    }

    public void setId_pengaduan(String id_pengaduan) {
        this.id_pengaduan = id_pengaduan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_sambung() {
        return no_sambung;
    }

    public void setNo_sambung(String no_sambung) {
        this.no_sambung = no_sambung;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPengaduan() {
        return pengaduan;
    }

    public void setPengaduan(String pengaduan) {
        this.pengaduan = pengaduan;
    }

    public String getPenyelesaian_pengaduan() {
        return penyelesaian_pengaduan;
    }

    public void setPenyelesaian_pengaduan(String penyelesaian_pengaduan) {
        this.penyelesaian_pengaduan = penyelesaian_pengaduan;
    }

    public String getId_petugas() {
        return id_petugas;
    }

    public void setId_petugas(String id_petugas) {
        this.id_petugas = id_petugas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
