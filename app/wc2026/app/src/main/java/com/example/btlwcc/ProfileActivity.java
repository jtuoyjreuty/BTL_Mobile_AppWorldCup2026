package com.example.btlwcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnThemDuLieu, btnDoiMatKhau, btnDangXuat;
    EditText edtPassCu, edtPassMoi;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        btnThemDuLieu = findViewById(R.id.btnThemDuLieu);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        edtPassCu = findViewById(R.id.edtMatKhauCu);
        edtPassMoi = findViewById(R.id.edtMatKhauMoi);

        dbHelper = new DatabaseHelper(this);

        // Nút Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Nút Quản trị
        btnThemDuLieu.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddMatchActivity.class);
            startActivity(intent);
        });

        // Xử lý đổi mật khẩu
        btnDoiMatKhau.setOnClickListener(v -> {
            String passCu = edtPassCu.getText().toString().trim();
            String passMoi = edtPassMoi.getText().toString().trim();
            String taiKhoanHienTai = "admin"; // Tạm thời gắn cứng tài khoản admin

            if (passCu.isEmpty() || passMoi.isEmpty()) {
                Toast.makeText(ProfileActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Kiểm tra mật khẩu cũ
            if (dbHelper.kiemTraDangNhap(taiKhoanHienTai, passCu)) {
                // 2. Cập nhật mật khẩu mới
                if (dbHelper.doiMatKhau(taiKhoanHienTai, passMoi)) {
                    Toast.makeText(ProfileActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    edtPassCu.setText("");
                    edtPassMoi.setText("");
                } else {
                    Toast.makeText(ProfileActivity.this, "Lỗi: Không thể đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút Đăng xuất
        btnDangXuat.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
