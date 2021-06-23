package com.example.appoderfood.FragmentApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.appoderfood.CustomAdapter.AdapterHienThiBanAn;
import com.example.appoderfood.DAO.BanAnDAO;
import com.example.appoderfood.DTO.BanAnDTO;
import com.example.appoderfood.R;
import com.example.appoderfood.SuaBanAnActivity;
import com.example.appoderfood.ThemBanAnActivity;
import com.example.appoderfood.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int RESQUEST_CODE_THEM = 111;
    public static int RESQUEST_CODE_SUA = 16;

    GridView gridViewHienThiBanAn;
    List<BanAnDTO>banAnDTOList;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;
    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,container,false);
        setHasOptionsMenu(true);
       ( (TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trangchu);

        gridViewHienThiBanAn=(GridView) view.findViewById(R.id.gridViewHienThiBanAn);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);


        banAnDAO= new BanAnDAO(getActivity());
//        banAnDTOList= banAnDAO.LayTatCaBanAn();


        HienThiDanhSachBanAn();
        if(maquyen == 1){
            registerForContextMenu(gridViewHienThiBanAn);
        }
        return view;
    }

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
        int maban = banAnDTOList.get(vitri).getMaBan();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban",maban);
                startActivityForResult(intent,RESQUEST_CODE_SUA);

                break;

            case R.id.itXoa:
                
                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
                if(kiemtra){
                    HienThiDanhSachBanAn();
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
            //là quản lý
            MenuItem itThemBanAn = menu.add(1,R.id.mnuThemBanAn,1,R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnuThemBanAn:
                Intent iThemBanAn= new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,RESQUEST_CODE_THEM);

            break;
        }
        return true;
    }
    private void HienThiDanhSachBanAn(){
        banAnDTOList= banAnDAO.LayTatCaBanAn();
        adapterHienThiBanAn= new AdapterHienThiBanAn(getActivity(),R.layout.custom_layout_hienthibanan,banAnDTOList);
        gridViewHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==RESQUEST_CODE_THEM){
            if(resultCode== Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem",false);
                if(kiemtra)
                {
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(),getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }else if(requestCode == RESQUEST_CODE_SUA){
                if(resultCode == Activity.RESULT_OK){
                    Intent intent = data;
                    boolean kiemtra = intent.getBooleanExtra("kiemtrasua",false);

                    if(kiemtra){
                        HienThiDanhSachBanAn();
                        Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
