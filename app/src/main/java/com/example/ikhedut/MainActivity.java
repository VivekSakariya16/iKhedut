    package com.example.ikhedut;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
ImageView dataImage;
TextView dataText;
ImageView pdfImage;
TextView pdfText;
String date;
ArrayList<TODO> records = new ArrayList<>();

private static final int STORAGE_REQUEST_CODE_EXPORT = 1;
private static final int STORAGE_REQUEST_CODE_IMPORT = 2;
private String [] storagePermissions;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Calendar calendar = Calendar.getInstance();
    date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
    dataImage = findViewById(R.id.data);
    pdfImage = findViewById(R.id.pdf);
    dataText=findViewById(R.id.dataText);
    pdfText=findViewById(R.id.pdfText);

    if((date.charAt(0) + "" + date.charAt(1)).equals("01")){
        exportCSV();
    }
    storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    dataImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,dataActivity.class);
            startActivity(intent);
        }
    });


    dataText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,dataActivity.class);
            startActivity(intent);
        }
    });

    pdfImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,pdfActivity.class);
            startActivity(intent);
        }
    });

    pdfText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,pdfActivity.class);
            startActivity(intent);
        }
    });
}

    private boolean checkPermission(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
    }

    private void checkPermissionForImport(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE_IMPORT);
    }
    private void checkPermissionForExport(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE_EXPORT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.abc){
            if(checkPermission()){
                exportCSV();
            }else {
                checkPermissionForExport();
            }
        }
        if(id==R.id.def){
            if(checkPermission()){
                importCSV();
            }else {
                checkPermissionForImport();
            }
        }
        if(id==R.id.en){
            LocaleHelper.setLocale(MainActivity.this,"en");
            return true;
        }
        if(id==R.id.guj){
            LocaleHelper.setLocale(MainActivity.this,"gu");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case STORAGE_REQUEST_CODE_EXPORT:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    exportCSV();
                }else {
                    Toast.makeText(this, "Storage permission reqired..", Toast.LENGTH_SHORT).show();
                }
            }
            case STORAGE_REQUEST_CODE_IMPORT:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    importCSV();
                }else {
                    Toast.makeText(this, "Storage permission reqired..", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    int i = 1;

    private void importCSV() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/" + "iKhedutBackup("+(i-1)+").csv";
        File csvFile = new File(path);
        CSVReader csvReader;
          if(csvFile.exists()){
              try {
                  csvReader= new CSVReader(new FileReader(csvFile.getAbsolutePath()));
                  String[] nextLine;
                  while ((nextLine = csvReader.readNext()) != null){
                      String date = nextLine[0];
                      int itemID = Integer.parseInt(nextLine[1]);
                      String note = nextLine[2];
                      int price = Integer.parseInt(nextLine[3]);
                      insertDATA(date,itemID,note,price);
                  }
                  Toast.makeText(this, getString(R.string.br), Toast.LENGTH_SHORT).show();
              } catch (Exception e) {
                  Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          }else {
              Toast.makeText(this, "No Backup found...", Toast.LENGTH_SHORT).show();
          }
}

    private void exportCSV() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        String file = "iKhedutBackup("+i+").csv";
        String path = folder + "/" + file;

        getAllItem();
        FileWriter fw;
        try {
            fw = new FileWriter(path);
            for (int i = 0; i < records.size(); i++) {
                fw.append(records.get(i).getDate());
                fw.append(",");
                fw.append(String.valueOf(records.get(i).getItemID()));
                fw.append(",");
                fw.append(records.get(i).getNote());
                fw.append(",");
                fw.append(String.valueOf(records.get(i).getPrice()));
                fw.append("\n");
            }
            fw.flush();
            fw.close();
            i++;
            Toast.makeText(this, getString(R.string.backup), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void getAllItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getAllItem();
                records.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    records.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }
    @SuppressLint("StaticFieldLeak")
    class Insert extends AsyncTask<TODO,Void,Void> {

        @Override
        protected Void doInBackground(TODO... todos) {
            TodoRoomDB.getInstance(MainActivity.this).todoDao().insertTodo(todos[0]);
            return null;
        }
    }
    public void insertDATA (String date,int itemID,String note,int price){
        TODO todo=new TODO(date,itemID,note,price);
        MainActivity.Insert insert = new MainActivity.Insert();
        insert.execute(todo);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.e));
        alert.setMessage(getString(R.string.exit));
        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                finishAffinity();
                System.exit(0);
            }
        });
        alert.show();
    }

}