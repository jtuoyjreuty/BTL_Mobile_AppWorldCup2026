package com.example.btlwcc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "worldcup.db"; // Tên file bạn vừa copy vào assets
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    // Hàm này sẽ kiểm tra xem điện thoại đã có database chưa, nếu chưa thì copy từ assets sang
    public void createDatabase() {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (Exception e) {
                throw new Error("Lỗi copy database từ thư mục assets!");
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    private void copyDatabase() throws Exception {
        // Mở file từ thư mục assets
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Đường dẫn xả file vào hệ thống
        String outFileName = context.getDatabasePath(DB_NAME).getPath();
        OutputStream myOutput = new FileOutputStream(outFileName);

        // Tiến hành copy từng byte
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // Hàm mở Database để đọc/ghi dữ liệu
    public SQLiteDatabase openDatabase() {
        return SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không dùng hàm này vì ta đã tạo database bằng phần mềm ngoài
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // 1. Hàm lấy toàn bộ danh sách trận đấu ra trang chủ
    public android.database.Cursor layDanhSachTranDau() {
        SQLiteDatabase db = this.openDatabase();
        // Lấy tất cả các cột từ bảng TranDau
        return db.rawQuery("SELECT * FROM TranDau", null);
    }

    // 2. Hàm lấy chi tiết bài nhận định của 1 trận đấu cụ thể (dựa vào ID)
    public android.database.Cursor layChiTietTranDau(int id) {
        SQLiteDatabase db = this.openDatabase();
        // Tìm đúng dòng có id tương ứng
        return db.rawQuery("SELECT * FROM TranDau WHERE id = ?", new String[]{String.valueOf(id)});
    }
    // Lấy danh sách bảng xếp hạng, sắp xếp theo Tên Bảng (A->Z), rồi đến Điểm số (Giảm dần), rồi đến Hiệu số (Giảm dần)
    public android.database.Cursor layBangXepHang() {
        SQLiteDatabase db = this.openDatabase();
        return db.rawQuery("SELECT * FROM BangXepHang ORDER BY tenBang ASC, pts DESC, gd DESC", null);
    }
    // Lấy danh sách vua phá lưới, sắp xếp số bàn thắng giảm dần
    public android.database.Cursor layVuaPhaLuoi() {
        SQLiteDatabase db = this.openDatabase();
        // Lưu ý: Tên cột trong DB của bạn có dấu cách ở cuối "soBanThang " 
        // và đang để kiểu TEXT nên cần CAST sang INTEGER để sắp xếp đúng (10 > 9)
        return db.rawQuery("SELECT * FROM VuaPhaLuoi ORDER BY CAST([soBanThang] AS INTEGER) DESC", null);
    }
    // --- CÁC HÀM QUẢN TRỊ TRẬN ĐẤU (ADMIN) ---

    // 1. Thêm trận đấu mới
    public boolean themTranDau(String doiNha, String doiKhach, String thoiGian, String tyLeKeo, String baiNhanDinh) {
        SQLiteDatabase db = this.openDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("doiNha", doiNha);
        values.put("doiKhach", doiKhach);
        values.put("thoiGian", thoiGian);
        values.put("tyLeKeo", tyLeKeo);
        values.put("baiNhanDinh", baiNhanDinh);

        // Lệnh insert trả về -1 nếu lỗi, trả về ID nếu thành công
        long result = db.insert("TranDau", null, values);
        return result != -1;
    }

    // Sửa trận đấu dựa vào Tên Đội Nhà cũ
    public boolean suaTranDauTheoTen(String doiNhaCu, String doiNhaMoi, String doiKhach, String thoiGian, String tyLeKeo, String baiNhanDinh) {
        SQLiteDatabase db = this.openDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("doiNha", doiNhaMoi);
        values.put("doiKhach", doiKhach);
        values.put("thoiGian", thoiGian);
        values.put("tyLeKeo", tyLeKeo);
        values.put("baiNhanDinh", baiNhanDinh);

        long result = db.update("TranDau", values, "doiNha=?", new String[]{doiNhaCu});
        return result > 0;
    }

    // Xóa trận đấu dựa vào Tên Đội Nhà
    public boolean xoaTranDauTheoTen(String doiNha) {
        SQLiteDatabase db = this.openDatabase();
        long result = db.delete("TranDau", "doiNha=?", new String[]{doiNha});
        return result > 0;
    }
    // --- CÁC HÀM QUẢN LÝ NGƯỜI DÙNG ---

    // 1. Hàm kiểm tra đăng nhập
    public boolean kiemTraDangNhap(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.openDatabase(); // Hoặc getReadableDatabase() tùy cách bạn cấu hình
        // Truy vấn xem có tài khoản và mật khẩu nào khớp trong bảng NguoiDung không
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE taiKhoan = ? AND matKhau = ?", new String[]{taiKhoan, matKhau});

        boolean isSuccess = cursor.getCount() > 0; // Nếu đếm được > 0 nghĩa là tài khoản hợp lệ
        cursor.close();
        return isSuccess;
    }
    // 3. Hàm đổi mật khẩu
    public boolean doiMatKhau(String taiKhoan, String matKhauMoi) {
        SQLiteDatabase db = this.openDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("matKhau", matKhauMoi); // Đưa mật khẩu mới vào hộp

        // Cập nhật lại dòng có tên tài khoản tương ứng
        long result = db.update("NguoiDung", values, "taiKhoan=?", new String[]{taiKhoan});
        return result > 0;
    }

//    // 2. Hàm tạo tài khoản Admin mặc định (Chỉ tạo nếu bảng NguoiDung đang trống)
//    public void taoTaiKhoanAdminMacDinh() {
//        SQLiteDatabase db = this.openDatabase(); // Hoặc getWritableDatabase()
//        android.database.Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung", null);
//
//        // Nếu chưa có tài khoản nào trong hệ thống thì mới tự động tạo
//        if (cursor.getCount() == 0) {
//            android.content.ContentValues values = new android.content.ContentValues();
//            values.put("taiKhoan", "admin");
//            values.put("matKhau", "123");
//            values.put("quyen", 1); // 1 là Admin
//            db.insert("NguoiDung", null, values);
//        }
//        cursor.close();
//    }
}
