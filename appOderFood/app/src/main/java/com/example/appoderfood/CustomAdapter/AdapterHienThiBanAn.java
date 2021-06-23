package com.example.appoderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appoderfood.DAO.BanAnDAO;
import com.example.appoderfood.DAO.GoiMonDAO;
import com.example.appoderfood.DTO.BanAnDTO;
import com.example.appoderfood.DTO.GoiMonDTO;
import com.example.appoderfood.FragmentApp.HienThiThucDonFragment;
import com.example.appoderfood.R;
import com.example.appoderfood.ThanhToanActivity;
import com.example.appoderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {
    Context context;
    int layout;
    List<BanAnDTO>banAnDTOList;
    ViewHoderBanAn viewHoderBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;


    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO>banAnDTOList)
    {
        this.context= context;
        this.layout= layout;
        this.banAnDTOList= banAnDTOList;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }
    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }


//ViewHolder cho phép bạn truy cập các thành phần của danh sách không cần VIewfindByID
    public class ViewHoderBanAn{
        ImageView imgBanAn,imgGoiMon,imgThanhToan;
        TextView txtTenBanAn;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        if(view==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHoderBanAn = new ViewHoderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan,parent,false);
            viewHoderBanAn.imgBanAn=(ImageView)view.findViewById(R.id.imgBanAn);
            viewHoderBanAn.imgThanhToan=(ImageView)view.findViewById(R.id.imgThanhToan);
            viewHoderBanAn.imgGoiMon=(ImageView)view.findViewById(R.id.imgGoiMon);

            viewHoderBanAn.txtTenBanAn=(TextView)view.findViewById(R.id.txtTenBanAn);

            view.setTag(viewHoderBanAn);
        }else {
            viewHoderBanAn =(ViewHoderBanAn)view.getTag();
        }

        //hiển thị button khi scoll
        if(banAnDTOList.get(position).getDuocChon())
        {
            hienThiButton();
        }
        else {
            anButton();
        }


        BanAnDTO banAnDTO= banAnDTOList.get(position);

        String ktTinhtrang = banAnDAO.LayTinhTrangBanAntheoMa(banAnDTO.getMaBan());
        if(ktTinhtrang.equals("true")){
            viewHoderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }
        else {
            viewHoderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }



        viewHoderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHoderBanAn.imgBanAn.setTag(position);

        viewHoderBanAn.imgBanAn.setOnClickListener(this);
        viewHoderBanAn.imgGoiMon.setOnClickListener(this);
        viewHoderBanAn.imgThanhToan.setOnClickListener(this);
        return view;
    }
    private void hienThiButton(){
        viewHoderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHoderBanAn.imgThanhToan.setVisibility(View.VISIBLE);


        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_nutbanan);
        viewHoderBanAn.imgGoiMon.startAnimation(animation);
        viewHoderBanAn.imgThanhToan.startAnimation(animation);

    }
    private void anButton(){
        viewHoderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHoderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        viewHoderBanAn = (ViewHoderBanAn) ((View)v.getParent()).getTag();
        int vitri1 =(int ) viewHoderBanAn.imgBanAn.getTag();
        int maban = banAnDTOList.get(vitri1).getMaBan();
        switch (id){
            case R.id.imgBanAn:
                String tenBan = viewHoderBanAn.txtTenBanAn.getText().toString();
                //chọn vào vị trí  bàn ăn
                int vitri = (int) v.getTag();
                banAnDTOList.get(vitri).setDuocChon(true);
                hienThiButton();
                break;
            case R.id.imgGoiMon:

                Intent layItrangchu = ((TrangChuActivity)context).getIntent();
                int manhanvien = layItrangchu.getIntExtra("manhanvien",0);

                String tinhtrang= banAnDAO.LayTinhTrangBanAntheoMa(maban);
                if(tinhtrang.equals("false")){
                   // 'thực hiện code them bản gọi món và cập nhập lại tình trang banf'
                    Calendar calendar= Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngaygoi = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO= new GoiMonDTO();
                    goiMonDTO.setMaBan(maban);
                    goiMonDTO.setMaNhanVien(manhanvien);
                    goiMonDTO.setNgayGoi(ngaygoi);
                    goiMonDTO.setTinhTrang("false");

                   long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                   banAnDAO.CapNhatLaiTinhTrangBan(maban,"true");
                   if(kiemtra ==0){
                       Toast.makeText(context,context.getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                   }

                }
                FragmentTransaction tranThucDontransaction = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment= new HienThiThucDonFragment();

                Bundle bDulieuThucDon= new Bundle();
                bDulieuThucDon.putInt("maban",maban);

                hienThiThucDonFragment.setArguments(bDulieuThucDon);

                tranThucDontransaction.replace(R.id.content,hienThiThucDonFragment).addToBackStack("hienthibanan");
                tranThucDontransaction.commit();
                break;


            case R.id.imgThanhToan:
                Intent iThanhtoan = new Intent(context, ThanhToanActivity.class);
                iThanhtoan.putExtra("maban",maban);
                context.startActivity(iThanhtoan);
                break;

        }
    }
}
