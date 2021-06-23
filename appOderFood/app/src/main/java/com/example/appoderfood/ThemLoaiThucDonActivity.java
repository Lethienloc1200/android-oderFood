package com.example.appoderfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appoderfood.DAO.LoaiMonAnDAO;


public class ThemLoaiThucDonActivity extends AppCompatActivity  implements View.OnClickListener {
    Button btnThemLoaiThucDon;
    EditText edtTenLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thuc_don);

        loaiMonAnDAO= new LoaiMonAnDAO(this);
        edtTenLoaiThucDon=(EditText) findViewById(R.id.edtTenLoaiThucDon);
        btnThemLoaiThucDon= (Button)findViewById(R.id.btnThemLoaiThucDon);


        btnThemLoaiThucDon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String sTenLoaiThucDon= edtTenLoaiThucDon.getText().toString();
        if(sTenLoaiThucDon != null || sTenLoaiThucDon.equals("")){
            boolean kiemtra = loaiMonAnDAO.ThemLoaiMonAn(sTenLoaiThucDon);
            Intent iDulieu= new Intent();
            iDulieu .putExtra("kiemtraloaithucdon",kiemtra);
            setResult(Activity.RESULT_OK,iDulieu);
            finish();
        }
        else {
            Toast.makeText(this,getResources().getString(R.string.vuilongnhaplai),Toast.LENGTH_SHORT).show();
        }
    }
}