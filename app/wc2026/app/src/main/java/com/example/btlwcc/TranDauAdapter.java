package com.example.btlwcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class TranDauAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TranDau> danhSachTranDau;

    public TranDauAdapter(Context context, int layout, List<TranDau> danhSachTranDau) {
        this.context = context;
        this.layout = layout;
        this.danhSachTranDau = danhSachTranDau;
    }

    @Override
    public int getCount() { return danhSachTranDau.size(); }
    @Override
    public Object getItem(int i) { return danhSachTranDau.get(i); }
    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
        }

        // Ánh xạ
        TextView tvThoiGian = view.findViewById(R.id.tvThoiGian);
        TextView tvLive = view.findViewById(R.id.tvLive);
        TextView tvDoiNha = view.findViewById(R.id.tvDoiNha);
        TextView tvDoiKhach = view.findViewById(R.id.tvDoiKhach);
        TextView tvTiSo = view.findViewById(R.id.tvTiSo);
        TextView tvKeo = view.findViewById(R.id.tvKeo);
        ImageView ivDoiNha = view.findViewById(R.id.ivDoiNha);
        ImageView ivDoiKhach = view.findViewById(R.id.ivDoiKhach);

        // Bơm dữ liệu
        TranDau td = danhSachTranDau.get(i);
        tvThoiGian.setText(td.getThoiGian());
        tvDoiNha.setText(td.getTenDoiNha());
        tvDoiKhach.setText(td.getTenDoiKhach());
        tvTiSo.setText(td.getTiSo());
        tvKeo.setText(td.getThongTinKeo());
        ivDoiNha.setImageResource(td.getLogoDoiNha());
        ivDoiKhach.setImageResource(td.getLogoDoiKhach());

        // Hiện nút LIVE màu đỏ nếu trận đang đá
        if (td.isLive()) {
            tvLive.setVisibility(View.VISIBLE);
        } else {
            tvLive.setVisibility(View.GONE);
        }

        return view;
    }
}