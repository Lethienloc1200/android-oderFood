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

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYSua;
    EditText edSuaTenBan;
    BanAnDAO banAnDAO;
    int maban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban_an);

        btnDongYSua = (Button) findViewById(R.id.btnDongYSuaBanAn);
        edSuaTenBan = (EditText) findViewById(R.id.edSuaTenBanAn);

        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tenban = edSuaTenBan.getText().toString();
        if(tenban != null && !tenban.equals("")){
            boolean kiemtra = banAnDAO.CapNhatLaiTenBan(maban,tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtrasua",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
