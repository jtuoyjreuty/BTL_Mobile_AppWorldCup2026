package com.example.btlwcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class TopScorerAdapter extends BaseAdapter {
    Context context;
    List<CauThu> danhSachCauThu;

    public TopScorerAdapter(Context context, List<CauThu> danhSachCauThu) {
        this.context = context;
        this.danhSachCauThu = danhSachCauThu;
    }

    @Override
    public int getCount() { return danhSachCauThu != null ? danhSachCauThu.size() : 0; }
    @Override
    public Object getItem(int i) { return danhSachCauThu.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cau_thu, viewGroup, false);
            holder = new ViewHolder();
            holder.tvHang = view.findViewById(R.id.tvHang);
            holder.ivAvatar = view.findViewById(R.id.ivAvatar);
            holder.tvTenCauThu = view.findViewById(R.id.tvTenCauThu);
            holder.ivCoQuocGia = view.findViewById(R.id.ivCoQuocGia);
            holder.tvViTri = view.findViewById(R.id.tvViTri);
            holder.tvSoBanThang = view.findViewById(R.id.tvSoBanThang);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CauThu cauThu = danhSachCauThu.get(i);

        if (cauThu != null && holder != null) {
            if (holder.tvHang != null) holder.tvHang.setText(String.valueOf(cauThu.hang));
            
            // Sử dụng ảnh đại diện (mặc định là logo_wc2026 đã gán ở Activity)
            if (holder.ivAvatar != null) {
                holder.ivAvatar.setImageResource(cauThu.avatar != 0 ? cauThu.avatar : R.drawable.logo_wc2026);
            }
            
            if (holder.tvTenCauThu != null) holder.tvTenCauThu.setText(cauThu.tenCauThu);
            
            if (holder.ivCoQuocGia != null) {
                holder.ivCoQuocGia.setImageResource(cauThu.coQuocGia != 0 ? cauThu.coQuocGia : R.mipmap.ic_launcher);
            }

            if (holder.tvViTri != null) holder.tvViTri.setText(cauThu.viTri);
            if (holder.tvSoBanThang != null) holder.tvSoBanThang.setText(String.valueOf(cauThu.soBanThang));
        }

        return view;
    }

    static class ViewHolder {
        TextView tvHang;
        ImageView ivAvatar;
        TextView tvTenCauThu;
        ImageView ivCoQuocGia;
        TextView tvViTri;
        TextView tvSoBanThang;
    }
}
