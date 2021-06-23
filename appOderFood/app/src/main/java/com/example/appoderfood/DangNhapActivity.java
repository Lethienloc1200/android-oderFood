package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appoderfood.DAO.NhanVienDAO;
import com.example.appoderfood.DTO.NhanVienDTO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDangNhap,btnDangKyDN;
    EditText edtTenDN,edtPassWordDN;
    NhanVienDAO nhanVienDAO;
    TextView txtDangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        btnDangNhap =(Button)findViewById(R.id.btnDangNhap);
        btnDangKyDN =(Button)findViewById(R.id.btnDangKyDN);
        edtPassWordDN=(EditText)findViewById(R.id.edtPassWordDN);
        edtTenDN=(EditText)findViewById(R.id.edtTenDN);
        txtDangky = (TextView)findViewById(R.id.txtDangKyTaiKhoan);

        //khởi tạo Nhân viên
        //đăng kí sự kiện
        nhanVienDAO= new NhanVienDAO(this);
        btnDangKyDN.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtDangky.setOnClickListener(this);


       HienThiButtonDangKyVaDongY();


    }
    //nếu mà làn lần đầu chưa đăng ký tài khoản thì hiện button đăng ký còn có tài khoản rồi thì ẩn button đk đó đi
    private void HienThiButtonDangKyVaDongY(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
//        nếu kiểm tra ==true
        if(kiemtra)
        {
            btnDangKyDN.setVisibility(View.GONE);
            btnDangNhap.setVisibility(View.VISIBLE);
        }else {
            btnDangKyDN.setVisibility(View.VISIBLE);
            btnDangNhap.setVisibility(View.GONE);
        }

    }
    private void btnDangNhap(){
        String sTenDangNhap = edtTenDN.getText().toString();
        String sMatKhau = edtPassWordDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemtraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);//lấy cái quyền đăng nhập
        if(kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.commit();

//            truyền iten cái tên đăng nhập gán cho giao diện và mã nhân viên
            Intent iTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            iTrangChu.putExtra("tendn",edtTenDN.getText().toString());
            iTrangChu.putExtra("manhanvien",kiemtra);
            startActivity(iTrangChu);
//            hiệu ứng
//            overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);

        }

        else {
            Toast.makeText(DangNhapActivity.this,getResources().getString(R.string.dangnhapthatbai) ,Toast.LENGTH_LONG).show();
        }
    }

    private void btnDangKy(){
        Intent iDangKy = new Intent(DangNhapActivity.this,DangKyActivity.class);
        iDangKy.putExtra("landautien",1);
        startActivity(iDangKy);
    }

    @Override
    protected void onResume() {
        HienThiButtonDangKyVaDongY();
        super.onResume();
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnDangNhap:
                btnDangNhap();
            break;
            case R.id.btnDangKyDN:
                btnDangKy();
            break;
            case R.id.txtDangKyTaiKhoan:
                btnDangKy();

            break;

        }
    }
}