package com.rishichandak.android.hisaab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class AddLessActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    AdapterClass adapterClass;
    ArrayList<String> stringArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        textView=(TextView) findViewById(R.id.textView);
        listView=(ListView) findViewById(R.id.listView);
        Intent intent=getIntent();
        Date selectedDate=(Date) intent.getSerializableExtra("Date");
        textView.setText(selectedDate.toString());
        adapterClass=new AdapterClass(getApplicationContext());
       //   stringArrayList.add("Add new");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,stringArrayList);
        listView.setAdapter(arrayAdapter);
        stringArrayList=adapterClass.getHeadsNames(stringArrayList);
        arrayAdapter.notifyDataSetChanged();

    }
}
