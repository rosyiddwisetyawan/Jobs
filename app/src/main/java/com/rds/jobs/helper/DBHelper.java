package com.rds.jobs.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rds.jobs.data.Jobs;
import com.rds.jobs.data.JobsWish;

import java.util.ArrayList;
import java.util.List;

import static com.rds.jobs.helper.Contant.CITY;
import static com.rds.jobs.helper.Contant.CONTENT_COMPANY;
import static com.rds.jobs.helper.Contant.CONTENT_IMAGE;
import static com.rds.jobs.helper.Contant.CURRENCY;
import static com.rds.jobs.helper.Contant.DATE;
import static com.rds.jobs.helper.Contant.DESCRIPTION;
import static com.rds.jobs.helper.Contant.JOBTYPE;
import static com.rds.jobs.helper.Contant.KEY_ID;
import static com.rds.jobs.helper.Contant.MAX;
import static com.rds.jobs.helper.Contant.MIN;
import static com.rds.jobs.helper.Contant.POSITION;
import static com.rds.jobs.helper.Contant.REQUIREMENT;
import static com.rds.jobs.helper.Contant.RESPONSIBILITY;
import static com.rds.jobs.helper.Contant.TABLE_NAME;
import static com.rds.jobs.helper.Contant.URL;

/**
 * Created by Team 2 on 11/10/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final List<JobsWish> wishList = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, Contant.DATABASE_NAME, null, Contant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our table
        String CREATE_WISHES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + CURRENCY + " TEXT, " + MIN + " TEXT, " + MAX + " TEXT, "
                + KEY_ID + " TEXT, " + CONTENT_IMAGE +
                " TEXT, " + Contant.CONTENT_COMPANY + " TEXT, " + DATE + " TEXT, "+ POSITION + " TEXT, "
                + JOBTYPE + " TEXT, " + CITY + " TEXT, "
                + URL + " TEXT, " + DESCRIPTION + " TEXT, "
                + RESPONSIBILITY + " TEXT, " + REQUIREMENT + " TEXT );";

        db.execSQL(CREATE_WISHES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        onCreate(db);


    }


    //delete a wish
    public void deleteWish(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ? ",
                new String[]{ id});

        db.close();

    }

    //add content to table
    public void addWishes(Jobs wish) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CURRENCY, wish.getMatauang());
        values.put(MIN, wish.getGajiawal());
        values.put(MAX, wish.getGajiakhir());
        values.put(KEY_ID, wish.getId());
        values.put(CONTENT_IMAGE, wish.getImage());
        values.put(CONTENT_COMPANY, wish.getCompany());
        values.put(DATE, wish.getDate());
        values.put(POSITION, wish.getPosition());
        values.put(JOBTYPE, wish.getJobtype());
        values.put(CITY, wish.getCity());
        values.put(URL, wish.getUrl());
        values.put(DESCRIPTION, wish.getDescription());
        values.put(RESPONSIBILITY, wish.getResponsibility());
        values.put(REQUIREMENT, wish.getRequirement());


        db.insert(TABLE_NAME, null, values);
        //db.insert(Constants.TABLE_NAME, null, values);

        // Log.v("Wish successfully!", "yeah!!");

        db.close();


    }
    //Delete all data
    public String deleteAll(){
        String status = "false";
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from "+ TABLE_NAME);
        return status;
    }

    // Getting single contact
    public String fetchdatabyfilter(String inputText) throws SQLException {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor row = null;
        String status = "false";
        String query = "SELECT * FROM "+Contant.TABLE_NAME;
        if (inputText == null  ||  inputText.length () == 0)  {
            row = database.rawQuery(query, null);
        }else {
            query = "SELECT COUNT(*) FROM "+Contant.TABLE_NAME+" WHERE "+Contant.KEY_ID+"="+inputText+"";
            row = database.rawQuery(query, null);
        }
        row.moveToFirst();
        int count = row.getCount();
        Log.i("QUERY", query.toString());
        Log.i("COUNT",String.valueOf(count));
        return status;
    }
    //Get all wishes
    public List<JobsWish> getWishes() {

        wishList.clear();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME, new String[]{CURRENCY, MIN, MAX, KEY_ID, CONTENT_IMAGE, CONTENT_COMPANY, DATE,
                POSITION, JOBTYPE, CITY, URL, DESCRIPTION, RESPONSIBILITY, REQUIREMENT}, null, null, null, null, DATE+" ASC");

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {

                JobsWish wish = new JobsWish();
                wish.setMatauang(cursor.getString(cursor.getColumnIndex(CURRENCY)));
                wish.setGajiawal(cursor.getString(cursor.getColumnIndex(MIN)));
                wish.setGajiakhir(cursor.getString(cursor.getColumnIndex(Contant.MAX)));
                wish.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                wish.setImage(cursor.getString(cursor.getColumnIndex(CONTENT_IMAGE)));
                wish.setCompany(cursor.getString(cursor.getColumnIndex(Contant.CONTENT_COMPANY)));
                wish.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                wish.setPosition(cursor.getString(cursor.getColumnIndex(POSITION)));
                wish.setJobtype(cursor.getString(cursor.getColumnIndex(JOBTYPE)));
                wish.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
                wish.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
                wish.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                wish.setResponsibility(cursor.getString(cursor.getColumnIndex(RESPONSIBILITY)));
                wish.setRequirement(cursor.getString(cursor.getColumnIndex(REQUIREMENT)));

                wishList.add(wish);

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return wishList;

    }
}
