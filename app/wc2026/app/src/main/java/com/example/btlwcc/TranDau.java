package com.example.btlwcc;

public class TranDau {
    private String tenDoiNha, tenDoiKhach;
    private int logoDoiNha, logoDoiKhach;
    private String thoiGian, tiSo, thongTinKeo;
    private boolean isLive; // Đang đá hay chưa?

    public TranDau(String tenDoiNha, String tenDoiKhach, int logoDoiNha, int logoDoiKhach, String thoiGian, String tiSo, String thongTinKeo, boolean isLive) {
        this.tenDoiNha = tenDoiNha;
        this.tenDoiKhach = tenDoiKhach;
        this.logoDoiNha = logoDoiNha;
        this.logoDoiKhach = logoDoiKhach;
        this.thoiGian = thoiGian;
        this.tiSo = tiSo;
        this.thongTinKeo = thongTinKeo;
        this.isLive = isLive;
    }

    public String getTenDoiNha() { return tenDoiNha; }
    public String getTenDoiKhach() { return tenDoiKhach; }
    public int getLogoDoiNha() { return logoDoiNha; }
    public int getLogoDoiKhach() { return logoDoiKhach; }
    public String getThoiGian() { return thoiGian; }
    public String getTiSo() { return tiSo; }
    public String getThongTinKeo() { return thongTinKeo; }
    public boolean isLive() { return isLive; }
}