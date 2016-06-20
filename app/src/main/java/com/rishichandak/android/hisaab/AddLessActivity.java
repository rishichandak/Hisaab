package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddLessActivity extends AppCompatActivity {

    TextView tvDate;
    ListView listView;
    AdapterClass adapterClass;
    HelperClass helper;
    ArrayList<String> stringArrayList=new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        listView=(ListView) findViewById(R.id.listView);
        //GetDate
        Intent intent=getIntent();
        String selectedDate = getFormatedDate((Date)intent.getSerializableExtra("date"));

        adapterClass=new AdapterClass(getApplicationContext());
        helper=new HelperClass(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(BasicActivity.selectedHeadName);
            getSupportActionBar().setSubtitle(selectedDate);
        }

        final Cursor allDataCursor= adapterClass.getAllSubHeads();
        String[] from=new String[]{helper.COL_ID,helper.COL_SUB_HEAD,helper.COL_PRICE};
        int[] to=new int[]{R.id.tvItemNo,R.id.tvItemName,R.id.tvRate};
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(getBaseContext(),R.layout.list_add_less,allDataCursor,from,to,0)
        {
            @Override
            public void bindView(final View view, final Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                final int row_id=cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                Button btnPlus= (Button) view.findViewById(R.id.btnPlus);
                Button btnMinus= (Button) view.findViewById(R.id.btnMinus);

                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText etQuantity= (EditText) view.findViewById(R.id.etQuantity);
                        int qty= Integer.parseInt( etQuantity.getText().toString());
                        etQuantity.setText(String.valueOf(++qty));
                    }
                });

                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText etQuantity= (EditText) view.findViewById(R.id.etQuantity);
                        int qty= Integer.parseInt( etQuantity.getText().toString());
                        if(qty>0) {
                            etQuantity.setText(String.valueOf(--qty));
                        }
                        else {
                            Toast.makeText(context, "Quantity cannot be less than 0 :" + row_id, Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        };

        simpleCursorAdapter.notifyDataSetChanged();
        listView.setAdapter(simpleCursorAdapter);

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int item_id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String item_content1 = cursor.getString(cursor.getColumnIndex(helper.COL_HEAD));
                String item = String.valueOf(item_id) + " : " + item_content1 +"\n";
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                Log.i("Click Event eeee","List Item: "+item);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int item_id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String item_content1 = cursor.getString(cursor.getColumnIndex(helper.COL_HEAD));
                String item = String.valueOf(item_id) + " : " + item_content1 +"\n";
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                Log.i("Click Event","List Item: "+item);
                return true;
            }
        }); */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_less_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private String getFormatedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }
}
