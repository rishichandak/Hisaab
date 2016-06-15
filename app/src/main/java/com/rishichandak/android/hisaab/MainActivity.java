package com.rishichandak.android.hisaab;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AdapterClass adapterClass;
    private HelperClass helper;
    private Toolbar toolbar;
    private Spinner spinner_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterClass = new AdapterClass(this);
        helper = new HelperClass(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner_nav = (Spinner) findViewById(R.id.spinner_nav);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        addItemsToSpinner();


        long id = adapterClass.insertValues(helper.TABLE_HEADS, new String[]{helper.COL_HEAD}, new String[]{"Milk"});
        if (id > 0) {
            Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insert Fail", Toast.LENGTH_SHORT).show();
        }

        // String heads=adapterClass.showHeads();
        // Toast.makeText(this,heads,Toast.LENGTH_LONG).show();
    }

    // add items into spinner dynamically
    public void addItemsToSpinner() {

        final Cursor allDataCursor= adapterClass.getAllData();
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
                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }


    public void goToCalendar(View view) {
        //Intent intent=new Intent(this,CalendarActivity.class);
        //startActivity(intent);
        DateDialog dialog = new DateDialog();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "Choose Date");

    }

}
