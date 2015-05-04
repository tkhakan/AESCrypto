package com.example.hakan.aesdeneme4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakan on 23.04.2015.
 */
public class VeriTabani extends SQLiteOpenHelper {
    private static final String VERITABANI_ADI = "sifreler";
    private static final int SURUM = 1;
    private static final String VERI_TABLO = "sifrebilgileri";
    private static final String VERI_ID = "id";
    private static final String VERI_SIFRE = "sifre";


    public VeriTabani(Context context) {
        super(context, VERITABANI_ADI, null, SURUM);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sifrebilgileri = "CREATE TABLE " + VERI_TABLO + "("
                + VERI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VERI_SIFRE + " TEXT" + ")";
        db.execSQL(create_sifrebilgileri);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST sifrebilgileri");
        onCreate(db);
    }

    public void SifreEkle(String sifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VERI_SIFRE, sifre);
        db.insert(VERI_TABLO, null, cv);
        db.close();

    }

    public List<SifreDegerleri> sifreCek() {
        List<SifreDegerleri> sonuclar = new ArrayList<SifreDegerleri>();
        try {
            String[] sutunlar = {"*"};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(VERI_TABLO, sutunlar, null, null, null, null, null);
            if (cursor.getCount() != 0) {
                cursor.moveToLast();
                do {
                    SifreDegerleri sifre = new SifreDegerleri();
                    sifre.setSifre(cursor.getString((cursor.getColumnIndex("sifre"))));

                    sonuclar.add(sifre);
                } while (cursor.moveToPrevious());
                cursor.close();
            }
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            sonuclar = new ArrayList<SifreDegerleri>();
        }


        return sonuclar;

    }
}
