package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appoderfood.DAO.GoiMonDAO;
import com.example.appoderfood.DTO.ChiTietGoiMonDTO;

public class ThemSoLuongActivity extends AppCompatActivity implements View.OnClickListener {
    int maban,mamonan;
    Button btnThemSoLuongMonAn;
    EditText edtSoLuongmonAn;
    GoiMonDAO goiMonDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_so_luong);

        btnThemSoLuongMonAn = (Button)findViewById(R.id.btnThemSoLuongMonAn);
        edtSoLuongmonAn = (EditText)findViewById(R.id.edtSoLuongmonAn);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent =getIntent();
        maban = intent.getIntExtra("maban",0);
        mamonan = intent.getIntExtra("mamonan",0);

        btnThemSoLuongMonAn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            int magoimon =(int)goiMonDAO.LayMaGoiMonTheoBanAn(maban,"false");
            boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon,mamonan);
            if(kiemtra){
                //tiến hành cập nhật món ăn tồn tại
                int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon,mamonan);
                int soluongmoi = Integer.parseInt(edtSoLuongmonAn.getText().toString());

                int tongsoluong = soluongcu + soluongmoi;

                ChiTietGoiMonDTO chiTietGoiMonDTO= new ChiTietGoiMonDTO();
                chiTietGoiMonDTO.setMaGoiMon(magoimon);
                chiTietGoiMonDTO.setMaMonAn(mamonan);
                chiTietGoiMonDTO.setSoLuong(tongsoluong);

                   boolean kiemtracapnhat = goiMonDAO.CapNhapSoLuong(chiTietGoiMonDTO);
                    if(kiemtracapnhat){
                        Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }
            }else {
                //thêm món ăn

                int soluonggoi = Integer.parseInt(edtSoLuongmonAn.getText().toString());
                ChiTietGoiMonDTO chiTietGoiMonDTO= new ChiTietGoiMonDTO();
                chiTietGoiMonDTO.setMaGoiMon(magoimon);
                chiTietGoiMonDTO.setMaMonAn(mamonan);
                chiTietGoiMonDTO.setSoLuong(soluonggoi);

                boolean kiemtracapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
                if(kiemtracapnhat){
                    Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
            finish();

    }
}