package com.example.ikhedut;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "item_data")
public class TODO {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "itemID")
    private int itemID;

    @ColumnInfo(name = "Note")
    private String note;

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ColumnInfo(name = "Price")
    private int price;


    public TODO(String date, int itemID,String note, int price) {
        this.date = date;
        this.itemID = itemID;
        this.price = price;
        this.note=note;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }


    public int getItemID() {
        return itemID;
    }


    public int getPrice() {
        return price;
    }


    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "TODO{" +
                "uid=" + uid +
                ", date='" + date + '\'' +
                ", itemID=" + itemID +
                ", note='" + note + '\'' +
                ", price=" + price +
                '}';
    }
}
