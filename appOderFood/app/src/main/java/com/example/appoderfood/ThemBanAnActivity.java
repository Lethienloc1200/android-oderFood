package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appoderfood.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText edtTenBanAn;
    Button btnThemBanAn;
    BanAnDAO banAnDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban_an);

        edtTenBanAn = (EditText) findViewById(R.id.edtTenBanAn);
        btnThemBanAn=(Button) findViewById(R.id.btnThemBanAn);


        banAnDAO = new BanAnDAO(this);
        btnThemBanAn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String sTenBanAn = edtTenBanAn.getText().toString();
        if(sTenBanAn != null && !sTenBanAn.equals(""))
        {
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else {
            Toast.makeText(this,getResources().getString(R.string.nhaptenbanan),Toast.LENGTH_SHORT).show();
        }
    }
}