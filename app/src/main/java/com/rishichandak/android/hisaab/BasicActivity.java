package com.rishichandak.android.hisaab;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class BasicActivity extends AppCompatActivity {

    private AdapterClass adapterClass;
    private HelperClass helper;
    private Toolbar toolbar;
    private Spinner spinner_nav;

    public static String selectedHeadNumber="0";
    public static String selectedHeadName="hisaab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
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
        addItemsToSpinner();
        // Toolbar Ends
/*

        adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD}, new String[]{"Milk"});
        adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD}, new String[]{"Laundry"});

        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Toned","1","28"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Cream","1","35"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Full Cream","1","40"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Ram Dudh wala morning","1","35"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Ram Dudh wala evening","1","40"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Dahi 100gm","1","20"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Dahi 250gm","1","50"});

        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Shirt","2","5"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Pant","2","5"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Saree","2","15"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Coat","2","30"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Salwar","2","10"});
        adapterClass.insertValues(helper.TABLE_SUBHEADS, new String[]{helper.COL_SUB_HEAD,helper.COL_UNDER_HEAD_ID,helper.COL_PRICE}, new String[]{"Kurta","2","15"});
*/



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                goToCalendar();
            }
        });
    }

    public void addItemsToSpinner() {

        final Cursor allDataCursor= adapterClass.getAllHeads();
        String[] from=new String[]{helper.COL_HEAD};
        int[] to=new int[]{R.id.tvItemName};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getBaseContext(),R.layout.spinner_row,allDataCursor,from,to,0);
        simpleCursorAdapter.notifyDataSetChanged();
        spinner_nav.setAdapter(simpleCursorAdapter);
        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                Cursor item = (Cursor)adapter.getItemAtPosition(position);
                selectedHeadNumber=item.getString(item.getColumnIndex(helper.COL_ID));
                selectedHeadName=item.getString(item.getColumnIndex(helper.COL_HEAD));

                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  : " + selectedHeadNumber+" Name:"+selectedHeadName,
                        Toast.LENGTH_LONG).show();
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
        } else if (id == "Add") {
            Toast.makeText(getApplicationContext(), "Add Clicked",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == "Delete") {
            Toast.makeText(getApplicationContext(), "Delete Clicked",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
