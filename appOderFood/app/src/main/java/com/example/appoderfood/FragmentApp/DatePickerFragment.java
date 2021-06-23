package com.example.appoderfood.FragmentApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appoderfood.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText edtNgaySinh=(EditText)getActivity().findViewById(R.id.edtNgaySinh);
        String sNgaySinh = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        edtNgaySinh.setText(sNgaySinh);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar= Calendar.getInstance();
        int isNam = calendar.get(Calendar.YEAR);
        int isThang = calendar.get(Calendar.MONTH);
        int isNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,isNgay,isThang,isNam);
    }
}
