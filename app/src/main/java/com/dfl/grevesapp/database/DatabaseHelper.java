package com.dfl.grevesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Loureiro on 10/06/2017.
 * <p>
 * Database SQlite open helper implementation
 */

class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "com_dfl_grevesapp.db";
    private static final int DATABASE_VERSION = 1;

    //company
    static final String COMPANY_TABLE_NAME = "company_table";
    static final String COMPANY_ID = "company_id";
    static final String COMPANY_NAME = "company_name";
    private static final String COMPANY_TABLE_CREATE = "create table "
            + COMPANY_TABLE_NAME + "( " + COMPANY_ID
            + " integer primary key autoincrement, " + COMPANY_NAME
            + " text not null);";

    //strike
    static final String STRIKE_ID = "strike_id";
    static final String STRIKE_START_DATE = "strike_start_date";
    static final String STRIKE_END_DATE = "strike_end_date";
    static final String STRIKE_CANCELED = "strike_canceled";
    static final String STRIKE_ALL_DAY = "strike_all_day";
    static final String STRIKE_SOURCE_LINK = "strike_source_link";
    static final String STRIKE_DESCRIPTION = "strike_description";
    static final String STRIKE_SUBMITTER_FIRST_NAME = "strike_submitter_first_name";
    static final String STRIKE_SUBMITTER_LAST_NAME = "strike_submitter_last_name";
    static final String STRIKE_TABLE_NAME = "strike_table";
    private static final String STRIKE_TABLE_CREATE = "create table "
            + STRIKE_TABLE_NAME + "( " + STRIKE_ID
            + " integer primary key autoincrement, "
            + STRIKE_START_DATE + " text not null, "
            + STRIKE_END_DATE + " text not null, "
            + STRIKE_CANCELED + " integer not null, "
            + STRIKE_ALL_DAY + " integer not null, "
            + STRIKE_SOURCE_LINK + " text, "
            + COMPANY_ID + " integer not null, "
            + STRIKE_DESCRIPTION + " text,"
            + STRIKE_SUBMITTER_FIRST_NAME + " text,"
            + STRIKE_SUBMITTER_LAST_NAME + " text,"
            + "foreign key ([" + COMPANY_ID + "]) references " + COMPANY_TABLE_NAME + "(" + COMPANY_ID + ")"
            + ");";

    /**
     * constructor
     *
     * @param context application context
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * returns database instance to avoid multiple iterations
     *
     * @param context context
     * @return database instance
     */
    static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * creates tables
     *
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMPANY_TABLE_CREATE);
        db.execSQL(STRIKE_TABLE_CREATE);
    }

    /**
     * called when the database is upgraded. first iteration so it will recreate all tables
     *
     * @param db         database
     * @param oldVersion older database version
     * @param newVersion new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + COMPANY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STRIKE_TABLE_NAME);
        onCreate(db);
    }


    /**
     * close database
     */
    public void close() {
        if (sInstance == null) {
            throw new NullPointerException("Tried to closed null database instance");
        }
        sInstance.close();
    }
}