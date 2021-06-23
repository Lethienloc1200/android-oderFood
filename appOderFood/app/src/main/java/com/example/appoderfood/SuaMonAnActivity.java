package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appoderfood.DAO.BanAnDAO;
import com.example.appoderfood.DAO.MonAnDAO;

public class SuaMonAnActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYSua;
    EditText edSuaTenMonAn,edSuaGiaMonAn;

    MonAnDAO monAnDAO;
    int mamon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);

        btnDongYSua = (Button) findViewById(R.id.btnDongYSuaBanAn);
        edSuaTenMonAn = (EditText) findViewById(R.id.edSuaTenMonAn);
        edSuaGiaMonAn = (EditText) findViewById(R.id.edSuaGiaMonAn);
        monAnDAO = new MonAnDAO(this);

        mamon = getIntent().getIntExtra("mamon",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tenmon = edSuaTenMonAn.getText().toString();
        int giatien = Integer.parseInt( edSuaGiaMonAn.getText().toString());
        if(tenmon != null && !tenmon.equals("") ){
            boolean kiemtra = monAnDAO.CapNhatLaiTenGiaMonAn(mamon,tenmon,giatien);
            Intent intent = new Intent();
            intent.putExtra("kiemtrasuamon",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(SuaMonAnActivity.this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
