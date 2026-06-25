package com.example.btlwcc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMatchActivity extends AppCompatActivity {

    EditText edtDoiNha, edtDoiKhach, edtThoiGian, edtTyLeKeo, edtBaiNhanDinh;
    Button btnLuuTranDau;
    TextView tvTitleHeader; // Khai báo thêm để đổi chữ tiêu đề
    DatabaseHelper dbHelper;
    boolean isEditMode = false;
    String doiNhaGoc = ""; // Lưu lại tên đội nhà cũ để làm điều kiện WHERE khi sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        // Ánh xạ
        edtDoiNha = findViewById(R.id.edtDoiNha);
        edtDoiKhach = findViewById(R.id.edtDoiKhach);
        edtThoiGian = findViewById(R.id.edtThoiGian);
        edtTyLeKeo = findViewById(R.id.edtTyLeKeo);
        edtBaiNhanDinh = findViewById(R.id.edtBaiNhanDinh);
        btnLuuTranDau = findViewById(R.id.btnLuuTranDau);
        dbHelper = new DatabaseHelper(this);

        // Bạn nhớ vào file activity_add_match.xml, đặt ID cho cái TextView tiêu đề trên Header là tvTitleHeader nhé
        tvTitleHeader = findViewById(R.id.tvTitleHeader);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // KIỂM TRA XEM LÀ CHẾ ĐỘ THÊM HAY SỬA
        isEditMode = getIntent().getBooleanExtra("IS_EDIT", false);

        if (isEditMode) {
            // Nếu là SỬA: Đổi tiêu đề và tự đổ dữ liệu cũ vào các ô nhập
            if(tvTitleHeader != null) tvTitleHeader.setText("CẬP NHẬT TRẬN ĐẤU");
            btnLuuTranDau.setText("CẬP NHẬT");
            btnLuuTranDau.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#0A2A5B")));

            doiNhaGoc = getIntent().getStringExtra("DOI_NHA_CU");
            edtDoiNha.setText(doiNhaGoc);
            edtDoiKhach.setText(getIntent().getStringExtra("DOI_KHACH_CU"));
            edtThoiGian.setText(getIntent().getStringExtra("THOI_GIAN_CU"));
            edtTyLeKeo.setText(getIntent().getStringExtra("KEO_CU"));

            // Hút nốt bài nhận định cũ từ DB lên để người dùng sửa
            String baiCu = "";
            android.database.Cursor c = dbHelper.openDatabase().rawQuery("SELECT baiNhanDinh FROM TranDau WHERE doiNha = ?", new String[]{doiNhaGoc});
            if (c != null && c.moveToFirst()) {
                baiCu = c.getString(0);
                c.close();
            }
            edtBaiNhanDinh.setText(baiCu);
        }

        // Sự kiện bấm nút Lưu (Dùng chung cho cả Thêm và Sửa)
        btnLuuTranDau.setOnClickListener(v -> {
            String nha = edtDoiNha.getText().toString().trim();
            String khach = edtDoiKhach.getText().toString().trim();
            String thoiGian = edtThoiGian.getText().toString().trim();
            String keo = edtTyLeKeo.getText().toString().trim();
            String nhanDinh = edtBaiNhanDinh.getText().toString().trim();

            if (nha.isEmpty() || khach.isEmpty() || thoiGian.isEmpty()) {
                Toast.makeText(AddMatchActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean check;
            if (isEditMode) {
                // Nếu đang ở chế độ sửa -> Gọi hàm UPDATE
                check = dbHelper.suaTranDauTheoTen(doiNhaGoc, nha, khach, thoiGian, keo, nhanDinh);
            } else {
                // Nếu đang ở chế độ thêm -> Gọi hàm INSERT
                check = dbHelper.themTranDau(nha, khach, thoiGian, keo, nhanDinh);
            }

            if (check) {
                Toast.makeText(AddMatchActivity.this, isEditMode ? "Cập nhật thành công!" : "Thêm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddMatchActivity.this, "Thao tác thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}