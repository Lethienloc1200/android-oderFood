package com.example.appoderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appoderfood.DTO.NhanVienDTO;
import com.example.appoderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    SQLiteDatabase database;
    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database =createDatabase.open();
    }
    public long ThemNhanVien(NhanVienDTO nhanVienDTO)
    {
        //Đối tượng ContentValues được sử dụng để chèn 1 hàng mới vào trong bảng cơ sở dữ liệu. Mỗi đối tượng ContentValues biểu diễn một hàng và ánh xạ cột sang giá trị tương ứng.
        ContentValues contentValues= new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());

        long kiemtra = database.insert(CreateDatabase.TB_NHANVIEN,null,contentValues);

        return kiemtra;
    }


    public int LayQuyenNhanVien(int manv){
        int maquyen = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }

        return maquyen;
    }

    public boolean SuaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());

        long kiemtra = database.update(CreateDatabase.TB_NHANVIEN, contentValues, CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMANV(), null);
        if(kiemtra !=0 ){
            return true;
        }else{
            return false;
        }
    }
    public boolean KiemTraNhanVien()
    {
        String truyVan=" SELECT * FROM "+ CreateDatabase.TB_NHANVIEN;
//        rawquery cho phép chứa câu truy vấn đơn giảng
        Cursor cursor = database.rawQuery(truyVan,null);
//        nếu cursor !=0 thì có tài khoản
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int  KiemtraDangNhap(String tendangnhap, String matkhau){
        String truyVan=" SELECT * FROM "+ CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_TENDN+" = '"+tendangnhap
                + "' AND " + CreateDatabase.TB_NHANVIEN_MATKHAU+ " = '"+matkhau+"'";

      int manhanvien = 0;
        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manhanvien = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }
        return manhanvien;
    }
    public List<NhanVienDTO> LayDanhSachNhanVien(){
        List<NhanVienDTO> nhanVienDTOs = new ArrayList<NhanVienDTO>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));

            nhanVienDTOs.add(nhanVienDTO);
            cursor.moveToNext();
        }

        return nhanVienDTOs;
    }

    public NhanVienDTO LayDanhSachNhanVienTheoMa(int manv){
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));

            cursor.moveToNext();
        }

        return nhanVienDTO;
    }

    public boolean XoaNhanVien(int manv){

        long kiemtra = database.delete(CreateDatabase.TB_NHANVIEN,CreateDatabase.TB_NHANVIEN_MANV + " = " + manv,null);
        if(kiemtra !=0 ){
            return true;
        }else{
            return false;
        }
    }
}
