package com.example.ikhedut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class pdfActivity extends AppCompatActivity{
    TextView dateB;
    Spinner spinnerB;
    Button showDATA;
    Button getPDF;
    TextView printDATA;
    ArrayList<String> itemDATA = new ArrayList<>(15);
    ArrayList<TODO> stringArrayList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        dateB=findViewById(R.id.dateB);
        spinnerB=findViewById(R.id.spinner2);
        showDATA=findViewById(R.id.getDATA);
        getPDF=findViewById(R.id.getPDF);
        printDATA=findViewById(R.id.showData);

        itemDATA.add(0,getString(R.string.all));
        itemDATA.add(1,getString(R.string.biyo));
        itemDATA.add(2,getString(R.string.dava));
        itemDATA.add(3,getString(R.string.k));
        itemDATA.add(4,getString(R.string.d));
        itemDATA.add(5,getString(R.string.mu));
        itemDATA.add(6,getString(R.string.m));
        itemDATA.add(7,getString(R.string.o));
        itemDATA.add(8,getString(R.string.l));
        itemDATA.add(9,getString(R.string.other));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(pdfActivity.this, android.R.layout.simple_spinner_item,itemDATA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerB.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        dateB.setText(getString(R.string.date)+date);

        if((date.charAt(0) + "" + date.charAt(1)).equals("01")){
            try {
                printPDF(0);

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        stringArrayList= new ArrayList<>();

        showDATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printing(spinnerB.getSelectedItemPosition());
                printing(spinnerB.getSelectedItemPosition());
                stringArrayList.clear();
            }
        });


        getPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    printPDF(spinnerB.getSelectedItemPosition());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(pdfActivity.this, "PDF Downloaded.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void printing(int id){
        printDATA.setText("");
        StringBuilder printed = new StringBuilder();

        switch (id) {
            case 0:
                printed.append(getString(R.string.ak));
                getAllItem();
                int i=0;
                int sum = 0;
                Log.e("MX",stringArrayList.size()+"");
                while (i<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(i+1).append(".\t\t").append(stringArrayList.get(i).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(i).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(i).getPrice()).append("\t\t").append(stringArrayList.get(i).getNote()).append("\n\n");
                    sum+=stringArrayList.get(i).getPrice();
                    i++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sum);
                Log.e("MX",printed.toString());
                printDATA.setText(printed.toString());
                break;
            case 1:
                printed.append(getString(R.string.bk));
                getBiyaranItem();
                int j=0;
                int sumj=0;
                while (j<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(j+1).append(".\t\t").append(stringArrayList.get(j).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(j).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(j).getPrice()).append("\t\t").append(stringArrayList.get(j).getNote()).append("\n\n");
                    sumj+=stringArrayList.get(j).getPrice();
                    j++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumj);
                printDATA.setText(printed.toString());
                break;
            case 2:
                printed.append(getString(R.string.dk));
                getDavaItem();
                int k=0;
                int sumk=0;
                while (k<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(k+1).append(".\t\t").append(stringArrayList.get(k).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(k).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(k).getPrice()).append("\t\t").append(stringArrayList.get(k).getNote()).append("\n\n");
                    sumk+=stringArrayList.get(k).getPrice();
                    k++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumk);
                printDATA.setText(printed.toString());
                break;
            case 3:
                printed.append(getString(R.string.kk));
                getKhatarItem();
                int l=0;
                int suml=0;
                while (l<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(l+1).append(".\t\t").append(stringArrayList.get(l).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(l).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(l).getPrice()).append("\t\t").append(stringArrayList.get(l).getNote()).append("\n\n");
                    suml+=stringArrayList.get(l).getPrice();
                    l++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(suml);
                printDATA.setText(printed.toString());
                break;
            case 4:
                printed.append(getString(R.string.dpk));
                getDieselItem();
                int m=0;
                int summ=0;
                while (m<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(m+1).append(".\t\t").append(stringArrayList.get(m).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(m).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(m).getPrice()).append("\t\t").append(stringArrayList.get(m).getNote()).append("\n\n");
                    summ+=stringArrayList.get(m).getPrice();
                    m++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(summ);
                printDATA.setText(printed.toString());
                break;
            case 5:
                printed.append(getString(R.string.muk));
                getMajurUpadItem();
                int n=0;
                int sumn=0;
                while (n<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(n+1).append(".\t\t").append(stringArrayList.get(n).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(n).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(n).getPrice()).append("\t\t").append(stringArrayList.get(n).getNote()).append("\n\n");
                    sumn+=stringArrayList.get(n).getPrice();
                    n++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumn);
                printDATA.setText(printed.toString());
                break;
            case 6:
                printed.append(getString(R.string.mk));
                getMajuriItem();
                int o=0;
                int sumo=0;
                while (o<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(o+1).append(".\t\t").append(stringArrayList.get(o).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(o).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(o).getPrice()).append("\t\t").append(stringArrayList.get(o).getNote()).append("\n\n");
                    sumo+=stringArrayList.get(o).getPrice();
                    o++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumo);
                printDATA.setText(printed.toString());
                break;
            case 7:
                printed.append(getString(R.string.ok));
                getOjarItem();
                int p=0;
                int sump=0;
                while (p<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(p+1).append(".\t\t").append(stringArrayList.get(p).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(p).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(p).getPrice()).append("\t\t").append(stringArrayList.get(p).getNote()).append("\n\n");
                    sump+=stringArrayList.get(p).getPrice();
                    p++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sump);
                printDATA.setText(printed.toString());
                break;
            case 8:
                printed.append(getString(R.string.lk));
                getLightItem();
                int q=0;
                int sumq=0;
                while (q<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(q+1).append(".\t").append(stringArrayList.get(q).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(q).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(q).getPrice()).append("\t\t").append(stringArrayList.get(q).getNote()).append("\n\n");
                    sumq+=stringArrayList.get(q).getPrice();
                    q++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumq);
                printDATA.setText(printed.toString());
                break;
            case 9:
                printed.append(getString(R.string.otk));
                getOtherItem();
                int r=0;
                int sumr = 0;
                while (r<stringArrayList.size()&&stringArrayList.size()!=0){
                    printed.append(r+1).append(".\t\t").append(stringArrayList.get(r).getDate()).append("\t\t").append(itemDATA.get(stringArrayList.get(r).getItemID())).append("\t\t")
                            .append("₹").append(stringArrayList.get(r).getPrice()).append("\t\t").append(stringArrayList.get(r).getNote()).append("\n\n");
                    sumr+=stringArrayList.get(r).getPrice();
                    r++;
                }
                printed.append("\n\n\t\tTotal : ₹").append(sumr);
                printDATA.setText(printed.toString());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + spinnerB.getSelectedItemPosition());
        }
    }

    private int count = 1;
    public void printPDF(int id) throws IOException {
        printing(id);
        printing(id);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file;
        switch (id){
            case 0 :
                file = new File(path,"AllItem_iKhedut("+count+").pdf");
                getAllItem();
                break;
            case 1 :
                file = new File(path,"Biyaran_iKhedut("+count+").pdf");
                getBiyaranItem();
                break;
            case 2 :
                file = new File(path,"Dava_iKhedut("+count+").pdf");
                 getDavaItem();
                break;
            case 3 :
                file = new File(path,"Khatar_iKhedut("+count+").pdf");
                 getKhatarItem();
                break;
            case 4 :
                file = new File(path,"Diesel_Petrol_iKhedut("+count+").pdf");
                 getDieselItem();
                break;
            case 5 :
                file = new File(path,"Majur_Upad_iKhedut("+count+").pdf");
                 getMajurUpadItem();
                break;
            case 6 :
                file = new File(path,"Majuri_iKhedut("+count+").pdf");
                 getMajuriItem();
                break;
            case 7 :
                file = new File(path,"Ojar_iKhedut("+count+").pdf");
                getOjarItem();
                break;
            case 8 :
                file = new File(path,"Light_Bill_iKhedut("+count+").pdf");
                 getLightItem();
                break;
            case 9 :
                file = new File(path,"Other_iKhedut("+count+").pdf");
                 getOtherItem();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        count++;

        int pageWidth=2100;
        PdfDocument pdfDocument= new PdfDocument();
        Paint myPaint = new Paint();


        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(2100 ,2970,1).create();
        PdfDocument.Page myPage1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas = myPage1.getCanvas();

        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        myPaint.setTextSize(70);
        canvas.drawText("iKhedut   -  "+itemDATA.get(id),(float) (pageWidth/2),200,myPaint);

        //make rectangle of corner of pdf
        myPaint.setStrokeWidth(2);
        canvas.drawLine(100,250,pageWidth-100,250,myPaint);
        canvas.drawLine(100,100,pageWidth-100,100,myPaint);
        canvas.drawLine(100,2870,pageWidth-100,2870,myPaint);
        canvas.drawLine(100,100,100,2870,myPaint);
        canvas.drawLine(pageWidth-100,100,pageWidth-100,2870,myPaint);

        myPaint.setTextSize(45);
        canvas.drawText("Page : 1",pageWidth-320,2930,myPaint);

        myPaint.setTextSize(60);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(getString(R.string.sr),300,335,myPaint);
        canvas.drawText(getString(R.string.da),540,335,myPaint);
        canvas.drawText(getString(R.string.in),920,335,myPaint);
        canvas.drawText(getString(R.string.note),1290,335,myPaint);
        canvas.drawText(getString(R.string.pri),1820,335,myPaint);

        canvas.drawLine(100,400,pageWidth-100,400,myPaint);

        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
        myPaint.setTextSize(60.0f);
        myPaint.setTextAlign(Paint.Align.LEFT);

        int y = 500;
        double totalx=0;
        int i=0;

        while(i<stringArrayList.size() && i<32) {
            String a = stringArrayList.get(i).getDate();
            String b = itemDATA.get(stringArrayList.get(i).getItemID());
            int c =stringArrayList.get(i).getPrice();
            String d=stringArrayList.get(i).getNote();

            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
            canvas.drawText((i+1)+".", 250, y, myPaint);
            canvas.drawText(a, 420, y, myPaint);
            canvas.drawText(b, 820, y, myPaint);
            canvas.drawText(d, 1250, y, myPaint);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("₹"+c, 1760, y, myPaint);

            totalx+=c;
            y+=70;
            i++;
        }



        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawLine(200,y,pageWidth-200,y,myPaint);
        canvas.drawText("Total",1500,y+65,myPaint);
        canvas.drawText(":",1600,y+65,myPaint);
        canvas.drawText("₹"+totalx,1800,y+65,myPaint);
        Log.e("MX","total1:"+totalx);

        pdfDocument.finishPage(myPage1);
        Log.e("mx","page 1 done");
//--------------------------------------------------------------------------------------------------------------------------//
        for (int j = 2; j < 100; j++) {

            int pagesize = 31;
            int i2 = pagesize * (j-1) + 1;
            int y2 = 500;
            if(stringArrayList.size()>i2) {
                PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(pageWidth, 2970, j).create();
                PdfDocument.Page myPage2 = pdfDocument.startPage(pageInfo2);
                Canvas canvas2 = myPage2.getCanvas();

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                myPaint.setTextSize(70);
                canvas2.drawText("iKhedut   -  " + itemDATA.get(id), (float) (pageWidth / 2), 200, myPaint);

                myPaint.setStrokeWidth(2);
                canvas2.drawLine(100, 250, pageWidth - 100, 250, myPaint);
                canvas2.drawLine(100, 100, pageWidth - 100, 100, myPaint);
                canvas2.drawLine(100, 2870, pageWidth - 100, 2870, myPaint);
                canvas2.drawLine(100, 100, 100, 2870, myPaint);
                canvas2.drawLine(pageWidth - 100, 100, pageWidth - 100, 2870, myPaint);

                myPaint.setTextSize(45);
                canvas2.drawText("Page : "+j,pageWidth-320,2930,myPaint);

                myPaint.setTextSize(60);
                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setStyle(Paint.Style.FILL);
                canvas2.drawText(getString(R.string.sr), 300, 335, myPaint);
                canvas2.drawText(getString(R.string.da), 540, 335, myPaint);
                canvas2.drawText(getString(R.string.in), 920, 335, myPaint);
                canvas2.drawText(getString(R.string.note), 1290, 335, myPaint);
                canvas2.drawText(getString(R.string.pri), 1820, 335, myPaint);

                canvas2.drawLine(100, 400, pageWidth - 100, 400, myPaint);


                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                myPaint.setTextSize(60.0f);

                canvas2.drawText("@", 280, y2, myPaint);
                canvas2.drawText("---  Total of Page "+(j-1)+" ---", 1000, y2, myPaint);
                canvas2.drawText("₹"+totalx, 1760, y2, myPaint);
                y2 += 70;

                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
                myPaint.setTextSize(60.0f);
                myPaint.setTextAlign(Paint.Align.LEFT);

                while (i2 < stringArrayList.size() && i2 <= pagesize * j) {
                    String a = stringArrayList.get(i2).getDate();
                    String b = itemDATA.get(stringArrayList.get(i2).getItemID());
                    int c = stringArrayList.get(i2).getPrice();
                    String d = stringArrayList.get(i2).getNote();

                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
                    canvas2.drawText((i2 + 1) + ".", 250, y2, myPaint);
                    canvas2.drawText(a, 420, y2, myPaint);
                    canvas2.drawText(b, 820, y2, myPaint);
                    canvas2.drawText(d, 1250, y2, myPaint);
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                    canvas2.drawText("₹"+c, 1760, y2, myPaint);

                    totalx += c;
                    y2 += 70;
                    i2++;
                }
                Log.e("MX", "total"+j+":" + totalx);


                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas2.drawLine(200, y2, pageWidth-200, y2, myPaint);
                canvas2.drawText("Total", 1500, y2 + 65, myPaint);
                canvas2.drawText(":", 1600, y2 + 65, myPaint);
                canvas2.drawText("₹"+totalx, 1800, y2 + 65, myPaint);
                Log.e("mx", "page "+j+"done");
                pdfDocument.finishPage(myPage2);
            }
        }

        OutputStream outputStream = new FileOutputStream(file);

        pdfDocument.writeTo(outputStream);

        pdfDocument.close();
    }

    public void getAllItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getAllItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getBiyaranItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getBiyaranItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getDavaItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getDavaItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getKhatarItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getKhatarItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getDieselItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getDieselItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getMajurUpadItem(){
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getMajurUpadItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getMajuriItem(){
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getMajuriItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getOjarItem(){
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getOjarItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getLightItem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getLightItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }

    public void getOtherItem(){
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<TODO> todoList = TodoRoomDB.getInstance(getApplicationContext()).todoDao().getotherItem();
                stringArrayList.clear();
                int i=0;
                while (i<todoList.size()){
                    TODO m =todoList.get(i);
                    stringArrayList.add(m);
                    i++;
                }
            }
        });
        thread.start();
    }


}