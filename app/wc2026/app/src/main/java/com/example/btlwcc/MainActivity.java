package com.example.btlwcc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton btnMenuHamburger, btnLoginHeader;
    TextView menuDangNhap, menuLichThiDau, menuBangXepHang, menuVuaPhaLuoi;
    ListView lvTranDau;
    ArrayList<TranDau> danhSach;
    TranDauAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        drawerLayout = findViewById(R.id.drawer_layout);
        btnMenuHamburger = findViewById(R.id.btnMenuHamburger);
        btnLoginHeader = findViewById(R.id.btn_login_header);
        menuDangNhap = findViewById(R.id.menuDangNhap);
        menuLichThiDau = findViewById(R.id.menuLichThiDau);
        menuBangXepHang = findViewById(R.id.menuBangXepHang);
        menuVuaPhaLuoi = findViewById(R.id.menuVuaPhaLuoi);
        TextView menuCaiDat = findViewById(R.id.menuCaiDat);
        lvTranDau = findViewById(R.id.lvTranDau);
        // Ánh xạ TextView ngày tháng
        TextView tvNgayThang = findViewById(R.id.tvNgayThang);

        // Lấy thời gian hiện tại của hệ thống máy điện thoại
        Calendar calendar = Calendar.getInstance();

        // Tạo khuôn định dạng ngày giờ (Ví dụ: Thứ Ba, 23 Tháng 06, 2026)
        // Dùng Locale("vi", "VN") để nó tự động dịch chữ "Tuesday" thành "Thứ Ba"
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'Tháng' MM, yyyy", new Locale("vi", "VN"));
        String ngayHienTai = sdf.format(calendar.getTime());

        // In kết quả lên màn hình
        tvNgayThang.setText("Hôm nay: " + ngayHienTai);

        danhSach = new ArrayList<>();

        // 1. Gọi cỗ máy DatabaseHelper để hút dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createDatabase(); // Lệnh này sẽ copy file db từ thư mục assets (nếu chưa có)

        // 2. Lấy danh sách ra và vòng lặp để đưa vào giao diện
        android.database.Cursor cursor = dbHelper.layDanhSachTranDau();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Rút dữ liệu từ các cột SQLite (Cột 0 là ID, 1 là Đội nhà, 2 Đội khách...)
                String doiNha = cursor.getString(1);
                String doiKhach = cursor.getString(2);
                String thoiGian = cursor.getString(3);
                String tyLeKeo = cursor.getString(4);

                // Gọi hàm lấy icon cờ (hàm này mình sẽ viết ở dưới)
                int logoNha = getFlagResource(doiNha);
                int logoKhach = getFlagResource(doiKhach);

                // Thêm vào danh sách hiển thị
                danhSach.add(new TranDau(doiNha, doiKhach, logoNha, logoKhach, thoiGian, "? - ?", tyLeKeo, false));

            } while (cursor.moveToNext());
            cursor.close();
        }

        // Cập nhật lên ListView
        adapter = new TranDauAdapter(this, R.layout.item_tran_dau, danhSach);
        lvTranDau.setAdapter(adapter);
        adapter = new TranDauAdapter(this, R.layout.item_tran_dau, danhSach);
        lvTranDau.setAdapter(adapter);

        // SỰ KIỆN: BẤM NÚT 3 GẠCH ĐỂ MỞ HAMBURGER MENU
        btnMenuHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // 2. Nút đăng nhập trong Hamburger Menu
        menuDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class); // Sửa thành ProfileActivity
                startActivity(intent);
            }
        });

        // Sự kiện click vào Lịch thi đấu trong Menu
        menuLichThiDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng Hamburger Menu lại (hiệu ứng trượt vào)
                drawerLayout.closeDrawer(GravityCompat.START);

                // Hiện một thông báo nhỏ cho người dùng biết họ đang ở đúng trang
                Toast.makeText(MainActivity.this, "Bạn đang ở trang Lịch thi đấu", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện mở Bảng xếp hạng
        menuBangXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Đóng menu trượt lại
                drawerLayout.closeDrawer(GravityCompat.START);

                // 2. Chuyển sang màn hình Bảng xếp hạng
                Intent intent = new Intent(MainActivity.this, StandingsActivity.class);
                startActivity(intent);
            }
        });

        menuVuaPhaLuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this, TopScorerActivity.class);
                startActivity(intent);
            }
        });
        // Sự kiện mở Cài đặt
        menuCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Đóng menu trượt lại cho mượt
                drawerLayout.closeDrawer(GravityCompat.START);

                // 2. Mở màn hình Cài đặt
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 1. Nút đăng nhập trên Header xanh đậm
        btnLoginHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class); // Sửa thành ProfileActivity
                startActivity(intent);
            }
        });

        // Sự kiện click xem kèo khi ấn vào từng trận đấu trên ListView
        lvTranDau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TranDau tranDuocChon = danhSach.get(position);
                Intent intent = new Intent(MainActivity.this, MatchDetailActivity.class);

                intent.putExtra("DOI_NHA", tranDuocChon.getTenDoiNha());
                intent.putExtra("DOI_KHACH", tranDuocChon.getTenDoiKhach());
                intent.putExtra("THOI_GIAN", tranDuocChon.getThoiGian());
                intent.putExtra("KEO", tranDuocChon.getThongTinKeo());

                // MỚI: Mở Database, tìm bài nhận định của đúng trận này và nhét vào Intent
                String baiNhanDinh = "Nội dung bài báo đang được cập nhật...";
                android.database.Cursor c = dbHelper.openDatabase().rawQuery("SELECT baiNhanDinh FROM TranDau WHERE doiNha = ?", new String[]{tranDuocChon.getTenDoiNha()});
                if (c != null && c.moveToFirst()) {
                    baiNhanDinh = c.getString(0);
                    c.close();
                }
                intent.putExtra("BAI_NHAN_DINH", baiNhanDinh);

                startActivity(intent);
            }
        });
        // Sự kiện NHẤN GIỮ để Sửa hoặc Xóa trận đấu
        lvTranDau.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TranDau tranDuocChon = danhSach.get(position);

                // Hiển thị một bảng lựa chọn (AlertDialog)
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                builder.setTitle("TÙY CHỌN QUẢN TRỊ");
                builder.setItems(new CharSequence[]{"✏️ Sửa trận đấu", "❌ Xóa trận đấu"}, new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        if (which == 0) {
                            // HÀNH ĐỘNG 1: SỬA TRẬN ĐẤU (Chuyển sang AddMatchActivity kèm dữ liệu cũ)
                            Intent intent = new Intent(MainActivity.this, AddMatchActivity.class);
                            intent.putExtra("IS_EDIT", true);
                            intent.putExtra("DOI_NHA_CU", tranDuocChon.getTenDoiNha());
                            intent.putExtra("DOI_KHACH_CU", tranDuocChon.getTenDoiKhach());
                            intent.putExtra("THOI_GIAN_CU", tranDuocChon.getThoiGian());
                            intent.putExtra("KEO_CU", tranDuocChon.getThongTinKeo());
                            startActivity(intent);
                        } else {
                            // HÀNH ĐỘNG 2: XÓA TRẬN ĐẤU
                            androidx.appcompat.app.AlertDialog.Builder confirmBuilder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                            confirmBuilder.setTitle("Xác nhận xóa");
                            confirmBuilder.setMessage("Bạn có chắc chắn muốn xóa trận " + tranDuocChon.getTenDoiNha() + " vs " + tranDuocChon.getTenDoiKhach() + "?");
                            confirmBuilder.setPositiveButton("Có", (d, w) -> {
                                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                                if (db.xoaTranDauTheoTen(tranDuocChon.getTenDoiNha())) {
                                    Toast.makeText(MainActivity.this, "Đã xóa trận đấu!", Toast.LENGTH_SHORT).show();
                                    onResume(); // Gọi lại hàm làm mới giao diện ngay lập tức
                                }
                            });
                            confirmBuilder.setNegativeButton("Không", null);
                            confirmBuilder.show();
                        }
                    }
                });
                builder.show();
                return true; // Trả về true để hệ thống biết đã xử lý xong, không kích hoạt click thường
            }
        });
    }
    // Hàm hỗ trợ tự động gán ảnh cờ dựa theo tên đội bóng lưu trong Database
    private int getFlagResource(String teamName) {
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
        if (teamName.equals("Argentina")) return R.drawable.flag_argentina;
        if (teamName.equals("Austria")) return R.drawable.flag_austria;

        return R.mipmap.ic_launcher; // Nếu không tìm thấy tên, dùng icon mặc định
    }
    // Hàm này tự động chạy mỗi khi quay trở lại Trang chủ
    @Override
    protected void onResume() {
        super.onResume();

        // 1. Kiểm tra xem danh sách và adapter đã được khởi tạo chưa
        if (danhSach != null && adapter != null) {

            // 2. Xóa sạch danh sách cũ đang hiển thị
            danhSach.clear();

            // 3. Gọi cỗ máy Hút lại dữ liệu mới toanh từ Database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            android.database.Cursor cursor = dbHelper.layDanhSachTranDau();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String doiNha = cursor.getString(1);
                    String doiKhach = cursor.getString(2);
                    String thoiGian = cursor.getString(3);
                    String tyLeKeo = cursor.getString(4);

                    int logoNha = getFlagResource(doiNha);
                    int logoKhach = getFlagResource(doiKhach);

                    // Thêm dữ liệu mới vào lại danh sách
                    danhSach.add(new TranDau(doiNha, doiKhach, logoNha, logoKhach, thoiGian, "? - ?", tyLeKeo, false));
                } while (cursor.moveToNext());
                cursor.close();
            }

            // 4. Báo cho Adapter biết: "Ê, tao vừa cập nhật dữ liệu, vẽ lại giao diện đi!"
            adapter.notifyDataSetChanged();
        }
    }
}