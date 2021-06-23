package com.example.appoderfood.CustomAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appoderfood.DAO.LoaiMonAnDAO;
import com.example.appoderfood.DTO.LoaiMonAnDTO;
import com.example.appoderfood.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    ViewholderLoaiMonAn viewholderLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context= context;
        this.layout= layout;
        this.loaiMonAnDTOList= loaiMonAnDTOList;

    }
    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonAnDTOList.get(position).getMaLoai();
    }
    public class ViewholderLoaiMonAn{
        TextView txtTenLoai;

    }

//    Xổ xuống của dropdown list => copy y chang bên dưới
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        if(view==null){
            viewholderLoaiMonAn = new ViewholderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.custom_spiner_loaithucdon,parent,false);

            viewholderLoaiMonAn.txtTenLoai=(TextView)view.findViewById(R.id.txtTenloai);

            view.setTag(viewholderLoaiMonAn);

        }else {
            viewholderLoaiMonAn= (ViewholderLoaiMonAn)view.getTag();
        }
        LoaiMonAnDTO loaiMonAnDTO= loaiMonAnDTOList.get(position);
        viewholderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewholderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            viewholderLoaiMonAn = new ViewholderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.custom_spiner_loaithucdon,parent,false);
            viewholderLoaiMonAn.txtTenLoai=(TextView)view.findViewById(R.id.txtTenloai);

            view.setTag(viewholderLoaiMonAn);

        }else {
            viewholderLoaiMonAn= (ViewholderLoaiMonAn)view.getTag();
        }
        LoaiMonAnDTO loaiMonAnDTO= loaiMonAnDTOList.get(position);
        viewholderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewholderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
