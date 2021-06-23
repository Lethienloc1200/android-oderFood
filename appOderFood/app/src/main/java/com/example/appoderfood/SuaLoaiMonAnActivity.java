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
import com.example.appoderfood.DAO.LoaiMonAnDAO;

public class SuaLoaiMonAnActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYSua;
    EditText edSuaTenLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    int maloai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_loai_mon_an);

        btnDongYSua = (Button) findViewById(R.id.btnDongYSuaBanAn);
        edSuaTenLoaiMonAn = (EditText) findViewById(R.id.edSuaTenLoaiMonAn);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        maloai = getIntent().getIntExtra("maloai",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tenloai = edSuaTenLoaiMonAn.getText().toString();
        if(tenloai != null && !tenloai.equals("")){
            boolean kiemtra = loaiMonAnDAO.CapNhatLaiTenMonAn(maloai,tenloai);
            Intent intent = new Intent();
            intent.putExtra("kiemtrasualoaimon",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(SuaLoaiMonAnActivity.this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
