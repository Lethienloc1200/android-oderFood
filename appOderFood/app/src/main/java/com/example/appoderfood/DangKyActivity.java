package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appoderfood.DAO.NhanVienDAO;
import com.example.appoderfood.DAO.QuyenDAO;
import com.example.appoderfood.DTO.NhanVienDTO;
import com.example.appoderfood.DTO.QuyenDTO;
import com.example.appoderfood.Database.CreateDatabase;
import com.example.appoderfood.FragmentApp.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edtTen,edtPassWord,edtNgaySinh;
    Button btnThoat,btnDangKy;
    RadioGroup radioGroupGioiTinh;
    TextView txtTieuDeDangKy;
    RadioButton radNam,radNu;
    String sGioiTinh;
    Spinner spinQuyen;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    int manv = 0;
    int landautien = 0;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        addControls();

    }

    private void addControls() {
        edtTen =(EditText)findViewById(R.id.edtTen);
        edtPassWord =(EditText)findViewById(R.id.edtPassWord);
        txtTieuDeDangKy = (TextView) findViewById(R.id.txtTieuDeDangKy);
        radNam = (RadioButton) findViewById(R.id.radNam);
        radNu = (RadioButton) findViewById(R.id.radNu);
        edtNgaySinh =(EditText)findViewById(R.id.edtNgaySinh);
        btnDangKy=(Button)findViewById(R.id.btnDangKy);
        btnThoat=(Button)findViewById(R.id.btnThoat);
        radioGroupGioiTinh =(RadioGroup)findViewById(R.id.radioGroupGioiTinh);
        spinQuyen = (Spinner) findViewById(R.id.spinQuyen);

        //????ng k?? s??? ki???n click
        btnDangKy.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
        edtNgaySinh.setOnFocusChangeListener(this);


        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<String>();

        for (int i=0; i<quyenDTOList.size();i++){
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        manv = getIntent().getIntExtra("manv",0);
        landautien = getIntent().getIntExtra("landautien",0);


        if(landautien == 0){
            quyenDAO.ThemQuyen("qu???n l??");
            quyenDAO.ThemQuyen("nh??n vi??n");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataAdapter);
            spinQuyen.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            spinQuyen.setVisibility(View.GONE);
        }


        if(manv != 0){
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manv);

            edtTen.setText(nhanVienDTO.getTENDN());
            edtPassWord.setText(nhanVienDTO.getMATKHAU());
            edtNgaySinh.setText(nhanVienDTO.getNGAYSINH());

            String gioitinh = nhanVienDTO.getGIOITINH();
            if(gioitinh.equals("Nam")){
                radNam.setChecked(true);
            }else{
                radNu.setChecked(true);
            }
        }
    }

    // ????ng k?? nh??n vi??n
    private void DongYThemNhanVien(){
        String sTenDangNhap = edtTen.getText().toString();
        String sMatKhau = edtPassWord.getText().toString();
        switch (radioGroupGioiTinh.getCheckedRadioButtonId()){
            case R.id.radNam:
                sGioiTinh = "Nam";
                break;

            case R.id.radNu:
                sGioiTinh = "N???";
                break;
        }
        String sNgaySinh = edtNgaySinh.getText().toString();

        if(sTenDangNhap == null || sTenDangNhap.equals("")){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
        }else if(sMatKhau == null || sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
        }else{
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            if(landautien != 0){
                //g??n m???c ?????nh quy???n nh??n vi??n l?? admin
                nhanVienDTO.setMAQUYEN(1);
            }else{
                //g??n quy???n b???ng quy???n m?? admin ch???n khi t???o nh??n vi??n
                int vitri = spinQuyen.getSelectedItemPosition();
                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maquyen);
            }

            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if(kiemtra != 0){
                Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }
    //s???a nh??n vien
    private void SuaNhanVien(){
        String sTenDangNhap = edtTen.getText().toString();
        String sMatKhau = edtPassWord.getText().toString();
        String sNgaySinh = edtNgaySinh.getText().toString();
        switch (radioGroupGioiTinh.getCheckedRadioButtonId()){
            case R.id.radNam:
                sGioiTinh = "Nam";
                break;

            case R.id.radNu:
                sGioiTinh = "N???";
                break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manv);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);

        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if(kiemtra){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.btnDangKy:
                if(manv != 0){
//                    N???u ???? c?? nh??n vieeeenn r???i th??
                    // Th???c hi???n code s???a nh??n vi??n
                    SuaNhanVien();
                }else{
                    // Th???c hi???n ????ng k?? nh??n vi??n
                    DongYThemNhanVien();
                }
                break;
            case R.id.btnThoat:
                finish();
                break;
        }
    }

//    c??i n??y l?? c???a dateTime picker
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id =v.getId();
        switch(id){
            case R.id.edtNgaySinh:
                if(hasFocus)
                {
                    DatePickerFragment datePickerFragment= new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(),"Ng??y sinh");
                }
                break;
        }
    }
}