package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.rishichandak.android.hisaab.HelperClass.COL_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_JOIN_ENTRY_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_PRICE;
import static com.rishichandak.android.hisaab.HelperClass.COL_QUANTITY;
import static com.rishichandak.android.hisaab.HelperClass.COL_SUB_HEAD;

public class AddLessActivity extends AppCompatActivity {

    private ListView listView;
    static Toolbar toolbar;
    private Intent intent;
    private AdapterClass adapterClass;
    private HelperClass helper;
    public static Date incomingDate;
    private String subTitleDate="Sat, 10 Sep,'94" ;
    private String sqlTypeDate="1994-09-10";
    public static int isEdited=0;
    public static View parentView;
    CustomAdapter customAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        listView=(ListView) findViewById(R.id.listView);
        adapterClass=new AdapterClass(this);
        helper=new HelperClass(this);
        parentView= findViewById(R.id.layoutAddLess);
        listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS); // for correct et keyboard in lv.. one line in manifest also

        //Get Intent Date
        intent=getIntent();
        incomingDate=(Date)intent.getSerializableExtra("date");
        subTitleDate = getFormattedDate(incomingDate);
        sqlTypeDate=getSqlDate(incomingDate);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((TextView)toolbar.findViewById(R.id.tvToolbarTitle)).setText(BasicActivity.selectedHeadName);
            ((TextView)toolbar.findViewById(R.id.tvToolbarSubTitle)).setText(subTitleDate);
            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ BasicActivity.selectedHeadName  +" </font>"));
            //getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>"+subTitleDate +" </font>"));
            getSupportActionBar().setElevation((float) 4.0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        customAdapter=new CustomAdapter(this);
        listView.setAdapter(customAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
             //   CustomAdapter.dataList.add(new SingleRow(null,null,null,null,null));
             //   customAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               // Cursor cursor = (Cursor) parent.getItemAtPosition(position);
               // int item_id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
               // String item_content1 = cursor.getString(cursor.getColumnIndex(helper.COL_HEAD));
               // String item = String.valueOf(item_id) + " : " + item_content1 +"\n";

                Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_LONG).show();
               // Log.i("Click Event","List Item: "+item);
                return true;
            }
        });
/*      adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"1","2"});
        adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"2","4"});
        adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"4","1"});
*/

       // adapterClass.getMonthlyTotal("07","2016", BasicActivity.selectedHeadNumber);
        //adapterClass.getMonthlyTotal("06","2016", BasicActivity.selectedHeadNumber);
        //adapterClass.getMonthlyTotal("07","2015", BasicActivity.selectedHeadNumber);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_less_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId=item.getItemId();
        switch(menuItemId){
            case R.id.home:
                super.onBackPressed();
                break;

            case R.id.action_discard:
                if (AddLessActivity.isEdited == 1) {
                    new AlertDialog.Builder(this)
                            .setTitle("Really Exit?")
                            .setMessage("Are you sure you want to exit without saving?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(R.string.dialog_exit, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finish();
                                    AddLessActivity.isEdited=0;
                                }
                            }).create().show();
                }
                else{
                    super.onBackPressed();
                }
                break;

            case R.id.action_save:
                try {
                    String id,entryId,quantity;
                    int total=listView.getCount();
                    for(int i=0; i<total; i++){
                        quantity=CustomAdapter.dataList.get(i).etQuantity;
                        entryId=CustomAdapter.dataList.get(i).tvEntryNo;
                       if(entryId.equals("-1") ) {
                           if (!quantity.equals("0")) {
                               id=CustomAdapter.dataList.get(i).tvItemNo;
                               String[] columnNames = new String[]{helper.COL_DATE, helper.COL_SUB_HEAD_ID, helper.COL_QUANTITY};
                               String[] columnValues = new String[]{sqlTypeDate, id, quantity};
                               adapterClass.insertValues(helper.TABLE_ENTRIES, columnNames, columnValues);
                           }
                       }
                        else {
                            adapterClass.updateValues(helper.TABLE_ENTRIES,new String[]{helper.COL_QUANTITY},new String[]{quantity}, COL_ID+" = ? ",new String[]{entryId});
                       }
                    }

                    Snackbar.make(parentView, "Record Saved", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    listView.setAdapter(new CustomAdapter(this));
                    AddLessActivity.isEdited=0;

                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(parentView, "Error Saving", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM, ''yy", Locale.getDefault());
        return dateFormat.format(date);
    }
    public String getSqlDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
        if (AddLessActivity.isEdited == 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit without saving?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(R.string.dialog_exit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            AddLessActivity.isEdited=0;
                        }
                    }).create().show();
        }
        else{
            super.onBackPressed();
        }
    }
}

class SingleRow{
    String tvItemNo;
    String tvEntryNo;
    String tvItemName;
    String etQuantity;
    String tvRate;
    SingleRow(String tvItemNo,String tvEntryNo,String tvItemName,String tvRate,String etQuantity){
        this.tvItemNo=tvItemNo;
        this.tvEntryNo=tvEntryNo;
        this.tvItemName=tvItemName;
        this.etQuantity=etQuantity;
        this.tvRate=tvRate;
    }
}

class CustomAdapter extends BaseAdapter {
    static ArrayList<SingleRow> dataList;
    private AddLessActivity mainClass;
    Cursor allDataCursor;
    Context context;
    AdapterClass adapterClass;
    Date incomingDate;
    CustomAdapter(Context c){
        context=c;
        adapterClass=new AdapterClass(context);
        mainClass=new AddLessActivity();
        incomingDate=mainClass.incomingDate;
        dataList=new ArrayList<SingleRow>();
        try {
            //New Table Structure:  _id | entryId | subHead | quantity | underHearId | price |
            //helper.COL_ID,  helper.COL_JOIN_ENTRY_ID,  helper.COL_SUB_HEAD,  helper.COL_QUANTITY,   COL_UNDER_HEAD_ID,   helper.COL_PRICE
            allDataCursor= adapterClass.getAllSubHeadsInfo(mainClass.getSqlDate(AddLessActivity.incomingDate),BasicActivity.selectedHeadNumber);
            while(allDataCursor.moveToNext()){
                String id=allDataCursor.getString(allDataCursor.getColumnIndex(COL_ID));  //0
                String rowId=allDataCursor.getString(allDataCursor.getColumnIndex(COL_JOIN_ENTRY_ID));  //1
                String subhead=allDataCursor.getString(allDataCursor.getColumnIndex(COL_SUB_HEAD));  //2
                String quantity=allDataCursor.getString(allDataCursor.getColumnIndex(COL_QUANTITY));  //3
                String price=allDataCursor.getString(allDataCursor.getColumnIndex(COL_PRICE));  //5

              /*  id=allDataCursor.getString(0);  //0
                rowId=allDataCursor.getString(1);  //1
                subhead=allDataCursor.getString(2);  //2
                quantity=allDataCursor.getString(3);  //3
                price=allDataCursor.getString(5);  //5  */

                dataList.add(new SingleRow(id,rowId,subhead,price,quantity));
            }
            notifyDataSetChanged();
            getTotalAmount();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf( dataList.get(position).tvItemNo);
    }

    class ViewHolder{
        TextView tvItemNo;
        TextView tvEntryNo;
        TextView tvItemName;
        EditText etQuantity;
        TextView tvRate;
        Button btnPlus;
        Button btnMinus;
        ViewHolder(View row){
             tvItemNo= (TextView) row.findViewById(R.id.tvItemNo);
             tvEntryNo= (TextView) row.findViewById(R.id.tvEntryNo);
             tvItemName= (TextView) row.findViewById(R.id.tvItemName);
             etQuantity= (EditText) row.findViewById(R.id.etQuantity);
             tvRate= (TextView) row.findViewById(R.id.tvRate);
             btnPlus= (Button) row.findViewById(R.id.btnPlus);
             btnMinus= (Button) row.findViewById(R.id.btnMinus);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder viewHolder=null;
        if(viewHolder==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_add_less, parent, false);
            viewHolder=new ViewHolder(row);
            row.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) row.getTag();
        }
        SingleRow temp=dataList.get(position);

        viewHolder.tvItemNo.setText(temp.tvItemNo);
        viewHolder.tvEntryNo.setText(temp.tvEntryNo);
        viewHolder.tvItemName.setText(temp.tvItemName);
        viewHolder.tvRate.setText(temp.tvRate);
        viewHolder.etQuantity.setText(temp.etQuantity);

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty= Integer.parseInt(finalViewHolder.etQuantity.getText().toString());
                finalViewHolder.etQuantity.setText(String.valueOf(++qty));
            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty= Integer.parseInt( finalViewHolder1.etQuantity.getText().toString());
                if(qty>0) {
                    finalViewHolder.etQuantity.setText(String.valueOf(--qty));
                }
                else {
                    //Toast.makeText(context, "Quantity cannot be less than 0", Toast.LENGTH_SHORT).show();
                    Snackbar.make(AddLessActivity.parentView, "Quantity cannot be less than 0", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });

        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddLessActivity.isEdited=1;
                dataList.get(position).etQuantity= finalViewHolder2.etQuantity.getText().toString();
                getTotalAmount();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return row;
    }

    public void getTotalAmount(){
        int total=0;
        for(int i=0;i<dataList.size();i++) {
            SingleRow row=dataList.get(i);

            total+= Float.valueOf(row.etQuantity)* Float.valueOf(row.tvRate) ;
        }
        ((TextView)AddLessActivity.toolbar.findViewById(R.id.tvToolbarTotal)).setText(String.valueOf(total));
    }
}
