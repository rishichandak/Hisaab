package com.rishichandak.android.hisaab;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class AddLessActivity extends AppCompatActivity {

    TextView tvDate;
    ListView listView;
    AdapterClass adapterClass;
    HelperClass helper;
    ArrayList<String> stringArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        tvDate=(TextView) findViewById(R.id.tvDate); //date time
        listView=(ListView) findViewById(R.id.listView);
        //GetDate
        Intent intent=getIntent();
        Date selectedDate=(Date) intent.getSerializableExtra("Date");
        tvDate.setText(selectedDate.toString());

        adapterClass=new AdapterClass(getApplicationContext());
        helper=new HelperClass(getApplicationContext());

       //   stringArrayList.add("Add new");
       // ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,stringArrayList);
        //listView.setAdapter(arrayAdapter);
        //stringArrayList=adapterClass.getHeadsNames(stringArrayList);
        //arrayAdapter.notifyDataSetChanged();

        Cursor allDataCursor= adapterClass.getAllData();
        String[] from=new String[]{helper.COL_ID,helper.COL_HEAD};
        int[] to=new int[]{R.id.tvItemNo,R.id.tvItemName};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getBaseContext(),R.layout.list_add_less,allDataCursor,from,to,0);
        simpleCursorAdapter.notifyDataSetChanged();
        listView.setAdapter(simpleCursorAdapter);
    }
}
