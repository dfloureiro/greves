package com.dfl.grevesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.dfl.grevesapp.datamodels.Company;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.datamodels.Submitter;

import java.util.ArrayList;

/**
 * Created by Loureiro on 10/06/2017.
 * <p>
 * Database adapter
 * Use this class to communicate with the database
 */

public class DatabaseAdapter {

    private DatabaseHelper databaseHelper;

    private String[] allColumnsStrike = {DatabaseHelper.STRIKE_ID, DatabaseHelper.STRIKE_START_DATE,
            DatabaseHelper.STRIKE_END_DATE, DatabaseHelper.STRIKE_CANCELED, DatabaseHelper.STRIKE_ALL_DAY,
            DatabaseHelper.STRIKE_SOURCE_LINK, DatabaseHelper.COMPANY_ID, DatabaseHelper.STRIKE_DESCRIPTION,
            DatabaseHelper.STRIKE_SUBMITTER_FIRST_NAME, DatabaseHelper.STRIKE_SUBMITTER_LAST_NAME};

    private String[] allColumnsCompany = {DatabaseHelper.COMPANY_ID, DatabaseHelper.COMPANY_NAME};


    /**
     * Constructor
     *
     * @param context context
     */
    public DatabaseAdapter(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    /**
     * insert/update list of strikes to database
     *
     * @param strikes list of strikes
     */
    public void addStrikes(ArrayList<Strike> strikes) {
        for (Strike strike : strikes) {
            if (!isStrikeAlreadyInserted(strike.getId())) {
                insertStrike(strike);
            } else {
                updateStrike(strike);
            }
        }
    }

    /**
     * insert strike to database
     *
     * @param strike strike to insert
     */
    private void insertStrike(Strike strike) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.STRIKE_ID, strike.getId());
        values.put(DatabaseHelper.STRIKE_START_DATE, strike.getStart_date());
        values.put(DatabaseHelper.STRIKE_END_DATE, strike.getEnd_date());
        values.put(DatabaseHelper.STRIKE_CANCELED, strike.isCanceled());
        values.put(DatabaseHelper.STRIKE_ALL_DAY, strike.isAll_day());
        values.put(DatabaseHelper.STRIKE_SOURCE_LINK, strike.getSource_link());
        values.put(DatabaseHelper.COMPANY_ID, strike.getCompany().getId());
        values.put(DatabaseHelper.STRIKE_DESCRIPTION, strike.getDescription());
        values.put(DatabaseHelper.STRIKE_SUBMITTER_FIRST_NAME, strike.getSubmitter().getFirst_name());
        values.put(DatabaseHelper.STRIKE_SUBMITTER_LAST_NAME, strike.getSubmitter().getLast_name());
        databaseHelper.getWritableDatabase().beginTransaction();
        databaseHelper.getWritableDatabase().insert(DatabaseHelper.STRIKE_TABLE_NAME, null, values);
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    /**
     * update strike to database
     *
     * @param strike strike to update
     */
    private void updateStrike(Strike strike) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.STRIKE_ID, strike.getId());
        values.put(DatabaseHelper.STRIKE_START_DATE, strike.getStart_date());
        values.put(DatabaseHelper.STRIKE_END_DATE, strike.getEnd_date());
        values.put(DatabaseHelper.STRIKE_CANCELED, strike.isCanceled());
        values.put(DatabaseHelper.STRIKE_ALL_DAY, strike.isAll_day());
        values.put(DatabaseHelper.STRIKE_SOURCE_LINK, strike.getSource_link());
        values.put(DatabaseHelper.COMPANY_ID, strike.getCompany().getId());
        values.put(DatabaseHelper.STRIKE_DESCRIPTION, strike.getDescription());
        values.put(DatabaseHelper.STRIKE_SUBMITTER_FIRST_NAME, strike.getSubmitter().getFirst_name());
        values.put(DatabaseHelper.STRIKE_SUBMITTER_LAST_NAME, strike.getSubmitter().getLast_name());
        databaseHelper.getWritableDatabase().beginTransaction();
        databaseHelper.getWritableDatabase().update(DatabaseHelper.STRIKE_TABLE_NAME, values,
                DatabaseHelper.STRIKE_ID + "=" + strike.getId(), null);
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    /**
     * get list of all strikes in the database
     *
     * @return list of strikes
     */
    public ArrayList<Strike> getStrikes() {
        ArrayList<Strike> strikes = new ArrayList<>();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "select " + DatabaseHelper.STRIKE_ID
                        + ", " + DatabaseHelper.STRIKE_START_DATE
                        + ", " + DatabaseHelper.STRIKE_END_DATE
                        + ", " + DatabaseHelper.STRIKE_CANCELED
                        + ", " + DatabaseHelper.STRIKE_ALL_DAY
                        + ", " + DatabaseHelper.STRIKE_SOURCE_LINK
                        + ", " + DatabaseHelper.COMPANY_ID
                        + ", " + DatabaseHelper.STRIKE_DESCRIPTION
                        + ", " + DatabaseHelper.STRIKE_SUBMITTER_FIRST_NAME
                        + ", " + DatabaseHelper.STRIKE_SUBMITTER_LAST_NAME
                        + " from " + DatabaseHelper.STRIKE_TABLE_NAME
                        + " ORDER BY " + DatabaseHelper.STRIKE_START_DATE + " DESC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Strike strike = new Strike(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.STRIKE_ID))
                        , cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_START_DATE))
                        , cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_END_DATE))
                        , (cursor.getInt(cursor.getColumnIndex(DatabaseHelper.STRIKE_CANCELED)) == 1)
                        , (cursor.getInt(cursor.getColumnIndex(DatabaseHelper.STRIKE_ALL_DAY)) == 1)
                        , cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_SOURCE_LINK))
                        , getCompany(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPANY_ID)))
                        , cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_DESCRIPTION))
                        , new Submitter(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_SUBMITTER_FIRST_NAME))
                        , cursor.getString(cursor.getColumnIndex(DatabaseHelper.STRIKE_SUBMITTER_LAST_NAME)))
                );
                strikes.add(strike);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return strikes;
    }

    /**
     * check if strike already exists in the database
     *
     * @param strikeID strike id
     * @return true if already exists
     */
    private boolean isStrikeAlreadyInserted(int strikeID) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.STRIKE_TABLE_NAME,
                allColumnsStrike, DatabaseHelper.STRIKE_ID + " = " + strikeID, null, null, null, null);
        boolean isInserted = cursor.getCount() > 0;
        cursor.close();
        return isInserted;
    }

    /**
     * insert/update list of companies to database
     *
     * @param companies list of companies
     */
    public void addCompanies(ArrayList<Company> companies) {
        for (Company company : companies) {
            if (!isCompanyAlreadyInserted(company.getId())) {
                insertCompany(company);
            } else {
                updateCompany(company);
            }
        }
    }

    /**
     * insert company into the database
     *
     * @param company company
     */
    private void insertCompany(Company company) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COMPANY_ID, company.getId());
        values.put(DatabaseHelper.COMPANY_NAME, company.getName());
        databaseHelper.getWritableDatabase().beginTransaction();
        databaseHelper.getWritableDatabase().insert(DatabaseHelper.COMPANY_TABLE_NAME, null, values);
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    /**
     * update company into the database
     *
     * @param company company
     */
    private void updateCompany(Company company) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COMPANY_ID, company.getId());
        values.put(DatabaseHelper.COMPANY_NAME, company.getName());
        databaseHelper.getWritableDatabase().beginTransaction();
        databaseHelper.getWritableDatabase().update(DatabaseHelper.COMPANY_TABLE_NAME, values,
                DatabaseHelper.COMPANY_ID + "=" + company.getId(), null);
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    /**
     * get list of companies from the database
     *
     * @return list of databases
     */
    public ArrayList<Company> getCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "select " + DatabaseHelper.COMPANY_ID
                        + ", " + DatabaseHelper.COMPANY_NAME
                        + " from " + DatabaseHelper.COMPANY_TABLE_NAME
                        + " ORDER BY " + DatabaseHelper.COMPANY_NAME + " ASC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Company company = new Company(cursor.getInt(0), cursor.getString(0));
                companies.add(company);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return companies;
    }

    /**
     * get company from the database
     *
     * @param companyID company id
     * @return company
     */
    private Company getCompany(int companyID) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.COMPANY_TABLE_NAME,
                allColumnsCompany, DatabaseHelper.COMPANY_ID + " = " + companyID, null, null, null, null);
        cursor.moveToFirst();
        Company company = new Company(cursor.getInt(0), cursor.getString(0));
        cursor.close();
        return company;
    }

    /**
     * check if company already exists in the database
     *
     * @param companyID company id
     * @return true if already exists
     */
    private boolean isCompanyAlreadyInserted(int companyID) {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.COMPANY_TABLE_NAME,
                allColumnsCompany, DatabaseHelper.COMPANY_ID + " = " + companyID, null, null, null, null);
        boolean isInserted = cursor.getCount() > 0;
        cursor.close();
        return isInserted;
    }

    /**
     * close database
     */
    public void close() {
        databaseHelper.close();
    }
}