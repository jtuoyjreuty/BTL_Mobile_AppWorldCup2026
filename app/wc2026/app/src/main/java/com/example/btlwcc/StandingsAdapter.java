package com.example.btlwcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class StandingsAdapter extends BaseAdapter {
    Context context;
    List<BangDau> danhSachBang;

    public StandingsAdapter(Context context, List<BangDau> danhSachBang) {
        this.context = context;
        this.danhSachBang = danhSachBang;
    }

    @Override
    public int getCount() { return danhSachBang.size(); }
    @Override
    public Object getItem(int position) { return danhSachBang.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_bang_dau, parent, false);
        }

        BangDau bangDau = danhSachBang.get(position);

        TextView tvTenBang = view.findViewById(R.id.tvTenBang);
        tvTenBang.setText(bangDau.tenBang);

        // Ánh xạ 4 dòng đội bóng
        View row1 = view.findViewById(R.id.rowTeam1);
        View row2 = view.findViewById(R.id.rowTeam2);
        View row3 = view.findViewById(R.id.rowTeam3);
        View row4 = view.findViewById(R.id.rowTeam4);

        // Bơm dữ liệu cho từng đội (Đội 1 và 2 màu xanh, Đội 3 và 4 màu xám)
        // Dùng if-check để tránh crash nếu bảng có ít hơn 4 đội
        if (bangDau.danhSachDoi.size() > 0) {
            row1.setVisibility(View.VISIBLE);
            bindTeamData(row1, bangDau.danhSachDoi.get(0), 1, R.drawable.bg_circle_green);
        } else {
            row1.setVisibility(View.GONE);
        }

        if (bangDau.danhSachDoi.size() > 1) {
            row2.setVisibility(View.VISIBLE);
            bindTeamData(row2, bangDau.danhSachDoi.get(1), 2, R.drawable.bg_circle_green);
        } else {
            row2.setVisibility(View.GONE);
        }

        if (bangDau.danhSachDoi.size() > 2) {
            row3.setVisibility(View.VISIBLE);
            bindTeamData(row3, bangDau.danhSachDoi.get(2), 3, R.drawable.bg_circle_gray);
        } else {
            row3.setVisibility(View.GONE);
        }

        if (bangDau.danhSachDoi.size() > 3) {
            row4.setVisibility(View.VISIBLE);
            bindTeamData(row4, bangDau.danhSachDoi.get(3), 4, R.drawable.bg_circle_gray);
        } else {
            row4.setVisibility(View.GONE);
        }

        return view;
    }

    // Hàm phụ trợ để nhét dữ liệu vào 1 dòng cực nhanh
    private void bindTeamData(View rowView, DoiBong doi, int hang, int bgCircle) {
        TextView tvHang = rowView.findViewById(R.id.tvHang);
        ImageView ivLogo = rowView.findViewById(R.id.ivLogo);
        TextView tvTen = rowView.findViewById(R.id.tvTen);
        TextView tvP = rowView.findViewById(R.id.tvP);
        TextView tvW = rowView.findViewById(R.id.tvW);
        TextView tvD = rowView.findViewById(R.id.tvD);
        TextView tvL = rowView.findViewById(R.id.tvL);
        TextView tvGD = rowView.findViewById(R.id.tvGD);
        TextView tvPTS = rowView.findViewById(R.id.tvPTS);

        tvHang.setText(String.valueOf(hang));
        tvHang.setBackgroundResource(bgCircle);
        ivLogo.setImageResource(doi.logo);
        tvTen.setText(doi.tenDoi);
        tvP.setText(String.valueOf(doi.p));
        tvW.setText(String.valueOf(doi.w));
        tvD.setText(String.valueOf(doi.d));
        tvL.setText(String.valueOf(doi.l));

        // Hiện dấu + cho hiệu số dương
        tvGD.setText(doi.gd > 0 ? "+" + doi.gd : String.valueOf(doi.gd));
        tvPTS.setText(String.valueOf(doi.pts));
    }
}