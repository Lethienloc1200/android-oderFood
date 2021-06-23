package com.example.appoderfood;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appoderfood.FragmentApp.HienThiBanAnFragment;
import com.example.appoderfood.FragmentApp.HienThiNhanVienFragment;
import com.example.appoderfood.FragmentApp.HienThiThucDonFragment;
import com.google.android.material.navigation.NavigationView;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawerLayout;
        NavigationView navTrangChu;
        Toolbar toolbar;
        TextView txtTenNhanViennav;
        FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        navTrangChu = (NavigationView)findViewById(R.id.navTrangChu);
        toolbar =findViewById(R.id.toolbar);

        View view = navTrangChu.getHeaderView(0);

        txtTenNhanViennav =(TextView)view.findViewById(R.id.txtTenNhanVien_nav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.mo,R.string.dong)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navTrangChu.setItemIconTintList(null);
        navTrangChu.setNavigationItemSelectedListener(this);

        Intent intent= getIntent();
     //   String tendn= intent.getStringExtra("tendangnhap");
        String tendn= intent.getStringExtra("tendn");
        txtTenNhanViennav.setText(tendn);

        fragmentManager = getSupportFragmentManager();

        //hiển thị content tại trang chủ
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
        tranHienThiBanAn.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch (id)
        {
            case R.id.itemTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
                tranHienThiBanAn.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itemNhanVien:
                FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranNhanVien.replace(R.id.content,hienThiNhanVienFragment);
                tranNhanVien.commit();

//                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itemThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                tranHienThiThucDon.commit();

//                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
        }
        return false;
    }
}