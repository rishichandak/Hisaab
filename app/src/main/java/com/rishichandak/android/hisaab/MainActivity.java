package com.rishichandak.android.hisaab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AdapterClass adapterClass;
    private HelperClass helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterClass=new AdapterClass(this);
        helper=new HelperClass(this);
        //long id=adapterClass.insertHeads("Laundry");
        long id=adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD},new String[]{"Newspaper"});
        if(id>0){
            Toast.makeText(this,"Insert Success",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Insert Fail",Toast.LENGTH_SHORT).show();
        }

       // String heads=adapterClass.showHeads();
       // Toast.makeText(this,heads,Toast.LENGTH_LONG).show();

    }
    public void goToCalendar(View view){
        //Intent intent=new Intent(this,CalendarActivity.class);
        //startActivity(intent);
        DateDialog dialog=new DateDialog();
        android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
        dialog.show(ft,"Choose Date");

    }
}
