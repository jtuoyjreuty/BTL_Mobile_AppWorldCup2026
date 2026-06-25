package com.example.btlwcc;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Nhớ khai báo trong Manifest nhé!

        // Nút Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Các nút Switch (Tượng trưng)
        Switch swThongBao = findViewById(R.id.swThongBao);
        Switch swMuiGio = findViewById(R.id.swMuiGio);
        Switch swDarkMode = findViewById(R.id.swDarkMode);

        swThongBao.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Đã bật thông báo" : "Đã tắt thông báo";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        swMuiGio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String msg = isChecked ? "Chuyển về giờ Việt Nam" : "Chuyển về giờ Quốc tế";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(SettingsActivity.this, "Chức năng Dark Mode đang phát triển!", Toast.LENGTH_SHORT).show();
            swDarkMode.setChecked(false); // Ép gạt về lại vì chưa làm thật
        });

        // Các nút bấm (Tượng trưng)
        LinearLayout btnClearCache = findViewById(R.id.btnClearCache);
        LinearLayout btnCredits = findViewById(R.id.btnCredits);
        LinearLayout btnRate = findViewById(R.id.btnRate);

        btnClearCache.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Đã dọn dẹp 14.2 MB rác thành công!", Toast.LENGTH_SHORT).show()
        );

        btnCredits.setOnClickListener(v ->
                        Toast.makeText(SettingsActivity.this, "Ứng dụng được phát triển bởi [Nguyễn Văn Hiếu và Hoàng Anh Phúc]", Toast.LENGTH_LONG).show()
                // Hoặc dùng Intent mở màn hình CreditsActivity ở đây
        );

        btnRate.setOnClickListener(v ->
                Toast.makeText(SettingsActivity.this, "Cảm ơn bạn đã đánh giá 5 sao!", Toast.LENGTH_SHORT).show()
        );
    }
}