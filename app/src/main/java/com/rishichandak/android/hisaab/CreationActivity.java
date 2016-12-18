package com.rishichandak.android.hisaab;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CreationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((TextView)toolbar.findViewById(R.id.tvToolbarTitle)).setText(BasicActivity.selectedHeadName);
            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ BasicActivity.selectedHeadName  +" </font>"));
            //getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>"+subTitleDate +" </font>"));
            getSupportActionBar().setElevation((float) 4.0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
class SingleRowCreation{
    String tvItemNo;
    String tvEntryNo;
    String etItemName;
    String etMinInc;
    String etRate;
    int imgShow;
    SingleRowCreation(String tvItemNo,String tvEntryNo,String etItemName,String etMinInc, String etRate,int imgShow) {
        this.tvItemNo = tvItemNo;
        this.tvEntryNo = tvEntryNo;
        this.etItemName = etItemName;
        this.etMinInc = etMinInc;
        this.etRate = etRate;
        this.imgShow=imgShow;
    }
}

class CustomAdapterCreation extends BaseAdapter{

    static ArrayList<SingleRowCreation> dataList;
    Cursor allDataCursor;
    Context context;
    AdapterClass adapterClass;
    CustomAdapterCreation(Context c){
        context=c;
        adapterClass=new AdapterClass(context);
        dataList=new ArrayList<SingleRowCreation>();
        
    }




    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}