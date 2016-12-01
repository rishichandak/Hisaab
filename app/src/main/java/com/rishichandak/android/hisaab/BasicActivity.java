package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class BasicActivity extends AppCompatActivity {

    private AdapterClass adapterClass;
    private HelperClass helper;
    private Toolbar toolbar;
    private Spinner spinner_nav;

    public static String selectedHeadNumber="0";
    public static String selectedHeadName="hisaab";
Calendar calendar;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);


        calendar= Calendar.getInstance();
        int mn=calendar.get(Calendar.MONTH);


        adapterClass = new AdapterClass(this);
        helper = new HelperClass(this);

        //Custom ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner_nav = (Spinner) findViewById(R.id.spinner_nav);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setIcon(R.drawable.ic_launcher);
        }
        // Toolbar Ends

        addItemsToSpinner();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int spinnerLastPosition = sharedPref.getInt("spinnerLastPosition", 0);
        spinner_nav.setSelection(spinnerLastPosition);







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                goToCalendar();
            }
        });
    }

    public void addItemsToSpinner() {

        final Cursor allDataCursor= adapterClass.getAllHeads();
        String[] from=new String[]{helper.COL_HEAD};
        int[] to=new int[]{R.id.tvItemName};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(this, R.layout.spinner_row, allDataCursor, from, to, 0);
        simpleCursorAdapter.notifyDataSetChanged();
        spinner_nav.setAdapter(simpleCursorAdapter);
        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                // On selecting a spinner item
                Cursor item = (Cursor)adapter.getItemAtPosition(position);
                selectedHeadNumber=item.getString(item.getColumnIndex(helper.COL_ID));
                selectedHeadName=item.getString(item.getColumnIndex(helper.COL_HEAD));
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  :"+selectedHeadName,
                        Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("spinnerLastPosition", spinner_nav.getSelectedItemPosition());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void goToCalendar() {
        //Intent intent=new Intent(this,CalendarActivity.class);
        //startActivity(intent);
        DateDialog dialog = new DateDialog();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "Choose Date");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String id = (String) item.getTitle();
        if (id == "Setting") {
            Toast.makeText(getApplicationContext(), "Settings Clicked",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == "Search") {
            Toast.makeText(getApplicationContext(), "Search Clicked",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (id.equals("Add")) {
            Toast.makeText(getApplicationContext(), "Add Clicked",
                    Toast.LENGTH_SHORT).show();
            adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD}, new String[]{"Milk"});
            adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD}, new String[]{"Laundry"});

            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Toned","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Cream","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Full Cream","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Ram Dudh wala morning","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Ram Dudh wala evening","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Dahi 100gm","1"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Dahi 250gm","1"});

            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Shirt","2"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Pant","2"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Saree","2"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Coat","2"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Salwar","2"});
            adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID}, new String[]{"Kurta","2"});

            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"1","2016-11-10","10"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"2","2016-11-10","20"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"3","2016-11-10","30"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"1","2016-11-30","11"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"3","2016-11-25","33"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"2","2016-11-20","22"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"4","2016-11-20","40"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"7","2016-11-10","70"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"5","2016-11-10","50"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"4","2016-11-10","44"});
            adapterClass.insertValues(helper.TABLE_RATES, new String[]{helper.COL_UNDER_SUB_HEAD_ID,helper.COL_WEFDATE,helper.COL_PRICE}, new String[]{"6","2016-11-10","60"});

            return true;
        } else if (id.equals("Delete")) {
            Toast.makeText(getApplicationContext(), "Delete Clicked",
                    Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,CreationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}