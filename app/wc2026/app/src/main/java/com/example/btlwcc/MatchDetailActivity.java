package com.example.btlwcc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MatchDetailActivity extends AppCompatActivity {

    TextView tvTieuDe, tvTomTat, tvNoiDung, tvKeoChiTiet;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        // 1. Ánh xạ giao diện
        tvTieuDe = findViewById(R.id.tvTieuDe);
        tvTomTat = findViewById(R.id.tvTomTat);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        tvKeoChiTiet = findViewById(R.id.tvKeoChiTiet);
        btnBack = findViewById(R.id.btnBack);

        // 2. Nút Quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng màn hình bài báo, về lại trang chủ
            }
        });

        // 3. Lấy dữ liệu từ MainActivity gửi sang
        String doiNha = getIntent().getStringExtra("DOI_NHA");
        String doiKhach = getIntent().getStringExtra("DOI_KHACH");
        String thoiGian = getIntent().getStringExtra("THOI_GIAN");
        String thongTinKeo = getIntent().getStringExtra("KEO");

        // MỚI: Lấy bài nhận định dài từ Database đã được Intent gửi sang
        String noiDungBaiBao = getIntent().getStringExtra("BAI_NHAN_DINH");

        if (doiNha == null) doiNha = "Đội A";
        if (doiKhach == null) doiKhach = "Đội B";

        // 4. Lắp ráp
        String tieuDe = "Nhận định " + doiNha + " vs " + doiKhach + " - World Cup 2026: Màn so tài kịch tính";
        String tomTat = "Cuộc đối đầu diễn ra lúc " + thoiGian + ". Tỷ lệ thắng và hòa lên đến 90%...";

        // 5. Hiển thị lên màn hình
        tvTieuDe.setText(tieuDe);
        tvTomTat.setText(tomTat);
        tvNoiDung.setText(noiDungBaiBao); // In đoạn văn bản dài từ DB ra đây
        tvKeoChiTiet.setText(thongTinKeo);
    }
}