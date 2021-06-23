package com.example.appoderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appoderfood.DTO.MonAnDTO;
import com.example.appoderfood.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<MonAnDTO>monAnDTOList;
    ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;

    public AdapterHienThiDanhSachMonAn(Context context,int layout,List<MonAnDTO>monAnDTOList)
    {
        this.context= context;
        this.layout= layout;
        this.monAnDTOList= monAnDTOList;
    }

    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTOList.get(position).getMaMonAn();

    }
    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imHinhAnhMonAn;
        TextView txtTenMonAn,txtGiaTien;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view= convertView;
      if(view== null){
          LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
          view = inflater.inflate(layout,parent,false);

          viewHolderHienThiDanhSachMonAn.imHinhAnhMonAn =(ImageView)view.findViewById(R.id.imHienThiDSMonAn);
          viewHolderHienThiDanhSachMonAn.txtTenMonAn =(TextView) view.findViewById(R.id.txtTenDSmonan);
          viewHolderHienThiDanhSachMonAn.txtGiaTien =(TextView) view.findViewById(R.id.txtGiaTienDSmonan);

          view.setTag(viewHolderHienThiDanhSachMonAn);
      }else {
        viewHolderHienThiDanhSachMonAn= (ViewHolderHienThiDanhSachMonAn)view.getTag();

      }
      MonAnDTO  monAnDTO= monAnDTOList.get(position);
        String hinhanh = monAnDTO.getHinhAnh().toString();
        if(hinhanh == null || hinhanh.equals("")){
            viewHolderHienThiDanhSachMonAn.imHinhAnhMonAn.setImageResource(R.drawable.banantrue);
        }else {
            Uri uri = Uri.parse(hinhanh);
            viewHolderHienThiDanhSachMonAn.imHinhAnhMonAn.setImageURI(uri);
        }


      viewHolderHienThiDanhSachMonAn.txtTenMonAn.setText(monAnDTO.getTenMonAn());
      viewHolderHienThiDanhSachMonAn.txtGiaTien.setText("Giá : "+monAnDTO.getGiaTien());


        return view;
    }
}
