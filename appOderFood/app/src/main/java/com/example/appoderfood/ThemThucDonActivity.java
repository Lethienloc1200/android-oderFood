package com.example.appoderfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.appoderfood.DAO.LoaiMonAnDAO;
import com.example.appoderfood.DAO.MonAnDAO;
import com.example.appoderfood.DTO.LoaiMonAnDTO;
import com.example.appoderfood.DTO.MonAnDTO;

import java.io.IOException;
import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    public  static int REQUEST_CODE_THEMLOAITHUCDON= 113;
    public  static int REQUEST_CODE_MOHINH= 123;
    Spinner spiner_loaiThucDon;
    ImageButton imageThemLoaiThucDon;
    ImageView imageViewHinhAnhTD;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    Button btnThemMonAn,btnThoat_monAn;
    String sDuongDanHinh;
    EditText edtTenMonAnLoaiTD,edtGiaTienLoaiTD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO= new MonAnDAO(this);

        spiner_loaiThucDon =(Spinner)findViewById(R.id.spiner_loaiThucDon);
        imageThemLoaiThucDon =(ImageButton) findViewById(R.id.imageThemLoaiThucDon);
        imageViewHinhAnhTD = (ImageView)findViewById(R.id.imageViewHinhAnhTD);
        btnThemMonAn = (Button)findViewById(R.id.btnThemMonAn);
        btnThoat_monAn  = (Button)findViewById(R.id.btnThoat_monAn);
        edtTenMonAnLoaiTD = findViewById(R.id.edtTenMonAnLoaiTD);
        edtGiaTienLoaiTD= findViewById(R.id.edtGiaTienLoaiTD);

        HienThiSpinerLoaiMonAn();


        btnThemMonAn.setOnClickListener(this);
        btnThoat_monAn.setOnClickListener(this);
        imageThemLoaiThucDon.setOnClickListener(this);
        imageViewHinhAnhTD.setOnClickListener(this);
    }
    private void HienThiSpinerLoaiMonAn(){
        loaiMonAnDTOs= loaiMonAnDAO.layDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn= new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this,R.layout.custom_spiner_loaithucdon,loaiMonAnDTOs);
        spiner_loaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imageThemLoaiThucDon:
                Intent intentThemLoaiMonAn = new Intent(ThemThucDonActivity.this,ThemLoaiThucDonActivity.class);
                startActivityForResult(intentThemLoaiMonAn,REQUEST_CODE_THEMLOAITHUCDON);
                break;
            case R.id.imageViewHinhAnhTD:
                if(Build.VERSION.SDK_INT < 19)
                {
                    Intent iMoHinh= new Intent();
                    iMoHinh.setType("image/*");
                    iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(iMoHinh,"chọn hình thực đơn"),REQUEST_CODE_MOHINH);
                }
                else {
                    Intent iMoHinh= new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    iMoHinh.setType("image/*");
//                    iMoHinh.setAction(Intent.ACTION_GET_CONTENT);//nếu lỗi xóa dòng này
                    iMoHinh.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(iMoHinh,"chọn hình thực đơn"),REQUEST_CODE_MOHINH);

                }

                break;
            case R.id.btnThemMonAn:
//                lấy vị trí loại món
                    int vitri= spiner_loaiThucDon.getSelectedItemPosition();
                    int maloai= loaiMonAnDTOs.get(vitri).getMaLoai();

                    String tenmonan=edtTenMonAnLoaiTD.getText().toString();
                    String giatien = edtGiaTienLoaiTD.getText().toString();

                    if(tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")){
                        MonAnDTO monAnDTO= new MonAnDTO();
                        monAnDTO.setTenMonAn(tenmonan);
                        monAnDTO.setGiaTien(giatien);
                        monAnDTO.setMaLoai(maloai);
                        monAnDTO.setHinhAnh(sDuongDanHinh);

                        boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                        if(kiemtra){
                            Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this,getResources().getString(R.string.vuilongnhapdaydu),Toast.LENGTH_SHORT).show();
                    }
                    Log.d("vitri",vitri +"");
                break;
            case R.id.btnThoat_monAn:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
           if(resultCode== Activity.RESULT_OK){
               Intent dulieu= data;
               boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon",false);
               if(kiemtra)
               {
                  HienThiSpinerLoaiMonAn();
                   Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
               }
           }
        }else if(  REQUEST_CODE_MOHINH == requestCode) {
            if(resultCode == Activity.RESULT_OK && data != null){

//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//                    imageViewHinhAnhTD.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                 }
                sDuongDanHinh= data.getData().toString();// đưa nó ra biến cục bộ để gọi
                    imageViewHinhAnhTD.setImageURI(data.getData());


            }
        }
    }
}