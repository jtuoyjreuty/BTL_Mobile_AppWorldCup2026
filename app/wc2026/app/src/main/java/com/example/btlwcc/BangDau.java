package com.example.btlwcc;
import java.util.List;

public class BangDau {
    String tenBang;
    List<DoiBong> danhSachDoi; // Danh sách chứa đúng 4 đội

    public BangDau(String tenBang, List<DoiBong> danhSachDoi) {
        this.tenBang = tenBang;
        this.danhSachDoi = danhSachDoi;
    }
}