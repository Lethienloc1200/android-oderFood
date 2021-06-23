package com.example.appoderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.appoderfood.DAO.LoaiMonAnDAO;
import com.example.appoderfood.DTO.LoaiMonAnDTO;
import com.example.appoderfood.R;
import com.example.appoderfood.SuaBanAnActivity;
import com.example.appoderfood.SuaLoaiMonAnActivity;
import com.example.appoderfood.ThemThucDonActivity;
import com.example.appoderfood.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {

    public static int RESQUEST_CODE_SUALOAIMON = 14;
    GridView gridView;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maban;
    int maquyen;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        ( (TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gridView =(GridView)view.findViewById(R.id.gv_HienThiThucDon);

        fragmentManager=getActivity().getSupportFragmentManager();
        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());


        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        loaiMonAnDTOs = loaiMonAnDAO.layDanhSachLoaiMonAn();
        AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon= new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_hienthiloaithucdon,loaiMonAnDTOs);
        gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
        adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();


//        đăng kí sửa xóa loại món ăn

            registerForContextMenu(gridView);



        Bundle bDulieuThucDon = getArguments();
        if(bDulieuThucDon != null){
            maban = bDulieuThucDon.getInt("maban");
        }

       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int maloai = loaiMonAnDTOs.get(position).getMaLoai();

               HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

               Bundle bundle = new Bundle();
               bundle.putInt("maloai",maloai);
               bundle.putInt("maban",maban);
               hienThiDanhSachMonAnFragment.setArguments(bundle);

               FragmentTransaction transaction = fragmentManager.beginTransaction();
               transaction.replace(R.id.content,hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

               transaction.commit();
           }
       });


        return view;
    }

//    menu them xóa
@Override
public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
}

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonAnDTOs.get(vitri).getMaLoai();

        switch (id){
            case R.id.itSua:
               // Toast.makeText(getActivity(),"mã loai:" +maloai,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SuaLoaiMonAnActivity.class);
                intent.putExtra("maloai",maloai);
                startActivityForResult(intent,RESQUEST_CODE_SUALOAIMON);

                break;

            case R.id.itXoa:
                boolean kiemtra = loaiMonAnDAO.XoaLoaiMonAnTheoMa(maloai);
                if(kiemtra){
                    loaiMonAnDTOs = loaiMonAnDAO.layDanhSachLoaiMonAn();
                    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon= new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_hienthiloaithucdon,loaiMonAnDTOs);
                    gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
                    adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();


                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban,Toast.LENGTH_SHORT).show();
                }
                ;break;
        }
        return super.onContextItemSelected(item);
    }






    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(maquyen == 1){
            MenuItem itThemBanAn = menu.add(1,R.id.mnuThemMonAn,1,R.string.themthucdon);
            itThemBanAn.setIcon(R.drawable.logodangnhap);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.mnuThemMonAn:
                Intent iThemThucDon= new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_SUALOAIMON){
            if(resultCode== Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtrasualoaimon",false);
                if(kiemtra)
                {
                    loaiMonAnDTOs = loaiMonAnDAO.layDanhSachLoaiMonAn();
                    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon= new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_hienthiloaithucdon,loaiMonAnDTOs);
                    gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
                    adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();

                    Toast.makeText(getActivity(),getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
                }
            }}
        }
    }

