package com.rishichandak.android.hisaab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AdapterClass adapterClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterClass=new AdapterClass(this);
        long id=adapterClass.insertHeads("Milk");
        if(id>0){
            Toast.makeText(this,"Insert Success",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Insert Fail",Toast.LENGTH_SHORT).show();
        }

        String heads=adapterClass.showHeads();
        Toast.makeText(this,heads,Toast.LENGTH_LONG).show();

    }
}
