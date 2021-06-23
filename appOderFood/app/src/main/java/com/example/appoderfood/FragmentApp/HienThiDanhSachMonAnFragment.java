package com.example.appoderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.appoderfood.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.example.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.appoderfood.DAO.MonAnDAO;
import com.example.appoderfood.DTO.MonAnDTO;
import com.example.appoderfood.R;
import com.example.appoderfood.SuaBanAnActivity;
import com.example.appoderfood.SuaMonAnActivity;
import com.example.appoderfood.ThemSoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {
    GridView gridView;
    public static int RESQUEST_CODE_SUAMON = 143;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);

        gridView= (GridView)view.findViewById(R.id.gv_HienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());

     Bundle bundle= getArguments();
     if(bundle != null){
         int maloai = bundle.getInt("maloai");
          maban = bundle.getInt("maban");

         monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);

        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.customlayout_hienthidanhsachmonan,monAnDTOList);
        gridView.setAdapter(adapterHienThiDanhSachMonAn)    ;
        adapterHienThiDanhSachMonAn.notifyDataSetChanged();


        registerForContextMenu(gridView);




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             if(maban !=0){
                 Intent iSoluong = new Intent(getActivity(), ThemSoLuongActivity.class);
                 iSoluong.putExtra("maban",maban);
                 iSoluong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());

                 startActivity(iSoluong);
             }
            }
        });


     }
     view.setOnKeyListener(new View.OnKeyListener() {
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event) {
             if(event.getAction()== KeyEvent.ACTION_DOWN){
                 getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
             }
             return false;
         }

     });

        return view;
    }

    // thử nghiệm  sửa xóa  trong đây
//    ==========================
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
        int mamon = monAnDTOList.get(vitri).getMaMonAn();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaMonAnActivity.class);
                intent.putExtra("mamon",mamon);
                startActivityForResult(intent,RESQUEST_CODE_SUAMON);

                break;

            case R.id.itXoa:
                boolean kiemtra = monAnDAO.XoaMonAnTheoMa(mamon);
                if(kiemtra){
                    Bundle bundle= getArguments();
                    int maloai = bundle.getInt("maloai");
                    maban = bundle.getInt("maban");

                    monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);
                    adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.customlayout_hienthidanhsachmonan,monAnDTOList);
                    gridView.setAdapter(adapterHienThiDanhSachMonAn)    ;
                    adapterHienThiDanhSachMonAn.notifyDataSetChanged();


                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban,Toast.LENGTH_SHORT).show();
                }
                ;break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_SUAMON){
            if(resultCode== Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtrasuamon",false);
                if(kiemtra)
                {
                         Bundle bundle= getArguments();
                        int maloai = bundle.getInt("maloai");
                        maban = bundle.getInt("maban");

                        monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);
                        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.customlayout_hienthidanhsachmonan,monAnDTOList);
                        gridView.setAdapter(adapterHienThiDanhSachMonAn)    ;
                        adapterHienThiDanhSachMonAn.notifyDataSetChanged();

                    Toast.makeText(getActivity(),getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
                }
            }}
    }

//    =====================
}

