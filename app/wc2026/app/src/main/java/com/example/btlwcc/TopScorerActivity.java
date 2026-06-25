package com.example.btlwcc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TopScorerActivity extends AppCompatActivity {

    ListView lvVuaPhaLuoi;
    ArrayList<CauThu> danhSach;
    TopScorerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scorer);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        lvVuaPhaLuoi = findViewById(R.id.lvVuaPhaLuoi);
        danhSach = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createDatabase(); // Đảm bảo database đã được copy
        android.database.Cursor cursor = dbHelper.layVuaPhaLuoi();

        int hang = 1;
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy chỉ số cột theo tên để tránh lỗi index
            int colTen = cursor.getColumnIndex("tenCauThu");
            if (colTen == -1) colTen = cursor.getColumnIndex("tenCauThu ");
            
            int colQuocGia = cursor.getColumnIndex("quocGia");
            if (colQuocGia == -1) colQuocGia = cursor.getColumnIndex("quocGia ");
            
            int colViTri = cursor.getColumnIndex("viTri");
            if (colViTri == -1) colViTri = cursor.getColumnIndex("viTri ");
            
            int colSoBan = cursor.getColumnIndex("soBanThang");
            if (colSoBan == -1) colSoBan = cursor.getColumnIndex("soBanThang ");

            do {
                String tenCauThu = (colTen != -1) ? cursor.getString(colTen) : "Chưa rõ";
                String quocGia = (colQuocGia != -1) ? cursor.getString(colQuocGia) : "";
                String viTri = (colViTri != -1) ? cursor.getString(colViTri) : "";
                int soBanThang = 0;
                
                if (colSoBan != -1) {
                    try {
                        soBanThang = cursor.getInt(colSoBan);
                    } catch (Exception e) {
                        String s = cursor.getString(colSoBan);
                        if (s != null) soBanThang = Integer.parseInt(s.trim());
                    }
                }

                if (tenCauThu != null) tenCauThu = tenCauThu.trim();
                if (quocGia != null) quocGia = quocGia.trim();

                // Đổi sang logo_wc2026 theo ý bạn
                int avatar = getAvatarResource(tenCauThu);
                int coQuocGia = getFlagResource(quocGia);

                danhSach.add(new CauThu(hang, tenCauThu, quocGia, viTri, soBanThang, avatar, coQuocGia));
                hang++;
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new TopScorerAdapter(this, danhSach);
        lvVuaPhaLuoi.setAdapter(adapter);
    }

    private int getFlagResource(String teamName) {
        if (teamName == null || teamName.isEmpty()) return R.mipmap.ic_launcher;

        if (teamName.equals("Argentina")) return R.drawable.flag_argentina;
        if (teamName.equals("France")) return R.drawable.flag_belgium;
        if (teamName.equals("Norway")) return R.drawable.flag_belgium;
        if (teamName.equals("Germany")) return R.drawable.flag_belgium;
        if (teamName.equals("Canada")) return R.drawable.flag_belgium;
        if (teamName.equals("Belgium")) return R.drawable.flag_belgium;
        if (teamName.equals("Spain")) return R.drawable.flag_spain;
        if (teamName.equals("Japan")) return R.drawable.flag_japan;

        return R.mipmap.ic_launcher;
    }
    // Hàm hỗ trợ tự động gán ảnh đại diện dựa theo tên cầu thủ
    private int getAvatarResource(String playerName) {
        if (playerName == null || playerName.isEmpty()) return R.drawable.ic_avatar; // Ảnh mặc định hình người xám

        if (playerName.equals("Lionel Messi")) return R.drawable.avatar_messi;
        if (playerName.equals("Kylian Mbappe")) return R.drawable.avatar_mbappe;
        if (playerName.equals("Erling Haaland")) return R.drawable.avatar_haaland;
        if (playerName.equals("Deniz Undav")) return R.drawable.avatar_undav;
        if (playerName.equals("Jonathan David")) return R.drawable.avatar_david;

        // Nếu không tìm thấy tên, trả về ảnh mặc định
        return R.drawable.ic_avatar;
    }
}