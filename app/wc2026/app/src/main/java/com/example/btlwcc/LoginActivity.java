//package com.example.btlwcc;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class LoginActivity extends AppCompatActivity {
//
//    EditText edtTaiKhoan, edtMatKhau;
//    Button btnXacNhanLogin, btnQuayLai;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Ánh xạ
//        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
//        edtMatKhau = findViewById(R.id.edtMatKhau);
//        btnXacNhanLogin = findViewById(R.id.btnXacNhanLogin);
//        btnQuayLai = findViewById(R.id.btnQuayLai);
//
//        // Ẩn nút quay lại vì đây là màn hình đầu tiên
//        btnQuayLai.setVisibility(View.GONE);
//
//        // Xử lý nút Đăng nhập
//        btnXacNhanLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String user = edtTaiKhoan.getText().toString().trim();
//                String pass = edtMatKhau.getText().toString().trim();
//
//                if (user.isEmpty() || pass.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
//                } else if (user.equals("admin") && pass.equals("123")) {
//                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//
//                    // Chuyển sang MainActivity
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish(); // Đóng màn hình Login để không quay lại được khi nhấn Back
//                } else {
//                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}
package com.example.btlwcc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnXacNhanLogin, btnQuayLai;
    DatabaseHelper dbHelper; // Khai báo DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnXacNhanLogin = findViewById(R.id.btnXacNhanLogin);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        
        // QUAN TRỌNG: Phải gọi lệnh này đầu tiên để copy file DB từ assets vào máy
        try {
            dbHelper.createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Sau khi đã có DB mới gọi hàm tạo tài khoản
//        dbHelper.taoTaiKhoanAdminMacDinh();

        // Ẩn nút quay lại vì đây là màn hình đầu tiên
        btnQuayLai.setVisibility(View.GONE);

        // Xử lý nút Đăng nhập bằng SQLite
        btnXacNhanLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtTaiKhoan.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Truy vấn dữ liệu thực tế từ SQLite
                    boolean isHopLe = dbHelper.kiemTraDangNhap(user, pass);

                    if (isHopLe) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        // Chuyển sang MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Đóng màn hình Login
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}