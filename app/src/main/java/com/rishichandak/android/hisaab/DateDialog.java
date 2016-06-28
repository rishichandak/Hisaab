package com.rishichandak.android.hisaab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rishi on 10-06-2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DateDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(year,monthOfYear,dayOfMonth);
        Date selectedDate= c.getTime();
        Intent intent=new Intent(getActivity(),AddLessActivity.class);
        intent.putExtra("date",selectedDate);
      //  Toast.makeText(getActivity(), dayOfMonth +"/" + (monthOfYear+1) +"/"+ year ,Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}
