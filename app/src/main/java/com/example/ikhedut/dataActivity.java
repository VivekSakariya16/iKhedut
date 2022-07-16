package com.example.ikhedut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class dataActivity extends AppCompatActivity {
    TextView date;
    Spinner spinner;
    EditText noteX;
    EditText price;
    Button save;
    String dateC;
    final Calendar myCalendar= Calendar.getInstance();

    private String updateLabel(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(myCalendar.getTime());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        save = findViewById(R.id.save);
        date=findViewById(R.id.CurrentDate);
        spinner=findViewById(R.id.spinner2);
        noteX = findViewById(R.id.notes);
        price=findViewById(R.id.itemPrice);

        ArrayList<String> itemDATA = new ArrayList<>();
        itemDATA.add(0,getString(R.string.biyo));
        itemDATA.add(1,getString(R.string.dava));
        itemDATA.add(2,getString(R.string.k));
        itemDATA.add(3,getString(R.string.d));
        itemDATA.add(4,getString(R.string.mu));
        itemDATA.add(5,getString(R.string.m));
        itemDATA.add(6,getString(R.string.o));
        itemDATA.add(7,getString(R.string.l));
        itemDATA.add(8,getString(R.string.other));


        ArrayAdapter<String> adapter = new ArrayAdapter<>(dataActivity.this, android.R.layout.simple_spinner_item,itemDATA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        DatePickerDialog.OnDateSetListener dateX =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                dateC=updateLabel();
                date.setText(dateC);
            }
        };
        dateC=updateLabel();
        date.setText(getString(R.string.date)+dateC);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                new DatePickerDialog(dataActivity.this,dateX,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(price.getText().toString().isEmpty()){
                    price.setError(getString(R.string.price));
                }
                else{
                    closeKeyboard();
                    String dateDB = dateC;
                    String notes = noteX.getText().toString();
                    String ItemPrice = price.getText().toString();
                    insertDATA(dateDB, spinner.getSelectedItemPosition()+1,notes,Integer.parseInt(ItemPrice));
                    Log.e("MX",dateDB+" "+notes+" "+ItemPrice+" "+spinner.getSelectedItemPosition()+1);
                    date.setText(getString(R.string.date)+updateLabel());
                    spinner.setSelection(0);
                    noteX.setText("");
                    price.setText("");
                    Toast.makeText(dataActivity.this, getString(R.string.save), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void insertDATA (String date,int itemID,String note,int price){
        TODO todo=new TODO(date,itemID,note,price);
        Insert insert = new Insert();
        insert.execute(todo);
    }

    public void closeKeyboard()
    {
        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class Insert extends AsyncTask<TODO,Void,Void>{

        @Override
        protected Void doInBackground(TODO... todos) {
            TodoRoomDB.getInstance(dataActivity.this).todoDao().insertTodo(todos[0]);
            return null;
        }
    }
}