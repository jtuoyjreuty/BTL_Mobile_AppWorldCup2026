package com.example.btlwcc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class StandingsActivity extends AppCompatActivity {

    ListView lvBangXepHang;
    StandingsAdapter adapter;
    List<BangDau> danhSachTatCaCacBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvBangXepHang = findViewById(R.id.lvBangXepHang);
        danhSachTatCaCacBang = new ArrayList<>();

        // 1. Gọi cỗ máy Database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        android.database.Cursor cursor = dbHelper.layBangXepHang();

        // 2. Thuật toán phân nhóm đội bóng theo Bảng đấu
        String tenBangHienTai = "";
        List<DoiBong> danhSachDoiTrongBang = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String tenBang = cursor.getString(1) != null ? cursor.getString(1) : "Unknown Group";
                    String tenDoi = cursor.getString(2) != null ? cursor.getString(2) : "Unknown Team";
                    int p = cursor.getInt(3);
                    int w = cursor.getInt(4);
                    int d = cursor.getInt(5);
                    int l = cursor.getInt(6);
                    int gd = cursor.getInt(7);
                    int pts = cursor.getInt(8);

                    int logo = getFlagResource(tenDoi);

                    // Nếu đọc đến một bảng mới (Ví dụ đang A chuyển sang B)
                    if (!tenBang.equals(tenBangHienTai)) {
                        // Lưu bảng cũ vào danh sách tổng (nếu không phải vòng lặp đầu tiên)
                        if (!tenBangHienTai.isEmpty()) {
                            danhSachTatCaCacBang.add(new BangDau(tenBangHienTai, danhSachDoiTrongBang));
                        }
                        // Khởi tạo bảng mới
                        tenBangHienTai = tenBang;
                        danhSachDoiTrongBang = new ArrayList<>();
                    }

                    // Thêm đội bóng vào bảng hiện tại
                    danhSachDoiTrongBang.add(new DoiBong(tenDoi, logo, p, w, d, l, gd, pts));

                } while (cursor.moveToNext());

                // Lưu bảng cuối cùng vào danh sách tổng
                if (!tenBangHienTai.isEmpty()) {
                    danhSachTatCaCacBang.add(new BangDau(tenBangHienTai, danhSachDoiTrongBang));
                }
            }
            cursor.close();
        }

        // 3. Hiển thị lên ListView
        adapter = new StandingsAdapter(this, danhSachTatCaCacBang);
        lvBangXepHang.setAdapter(adapter);
    }

    // Hàm lấy cờ (Cập nhật để tránh lỗi NullPointerException và khớp với các file ảnh hiện có)
    private int getFlagResource(String teamName) {
        if (teamName == null) return R.mipmap.ic_launcher;
        
        if (teamName.equals("Tunisia")) return R.drawable.flag_tunisia;
        if (teamName.equals("Japan")) return R.drawable.flag_japan;
        if (teamName.equals("Spain")) return R.drawable.flag_spain;
        if (teamName.equals("Saudi Arabia")) return R.drawable.flag_saudi_arabia;
        if (teamName.equals("Belgium")) return R.drawable.flag_belgium;
        if (teamName.equals("Iran")) return R.drawable.flag_iran;
        if (teamName.equals("Uruguay")) return R.drawable.flag_uruguay;
        if (teamName.equals("Cape Verde")) return R.drawable.flag_cape_verde;
        if (teamName.equals("New Zealand")) return R.drawable.flag_new_zealand;
        if (teamName.equals("Egypt")) return R.drawable.flag_egypt;

        return R.mipmap.ic_launcher;
    }
}