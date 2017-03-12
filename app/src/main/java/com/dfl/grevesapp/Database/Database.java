package com.dfl.grevesapp.database;

import android.content.Context;

import com.dfl.grevesapp.datamodels.Company;
import com.dfl.grevesapp.datamodels.Strike;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Diogo Loureiro on 23/01/2017.
 * <p>
 * Database implementation
 */

public class Database {

    private static boolean isInit = false;

    /**
     * init database
     *
     * @param context context
     */
    public static void init(Context context) {
        Realm.init(context);
        isInit = true;
    }

    /**
     * get database
     *
     * @return db
     */
    private static Realm get() {
        return Realm.getDefaultInstance();
    }

    /**
     * add companies
     *
     * @param companies arraylist of companies to add
     */
    public static void addCompanies(ArrayList<Company> companies) {
        get().beginTransaction();
        get().insertOrUpdate(companies);
        get().commitTransaction();
    }

    /**
     * add strikes
     *
     * @param strikes arraylist of strikes to add
     */
    public static void addStrikes(ArrayList<Strike> strikes) {
        get().beginTransaction();
        get().insertOrUpdate(strikes);
        get().commitTransaction();
    }

    /**
     * @return list of companies sorted by name
     */
    public static RealmResults<Company> getCompanies() {
        return get().where(Company.class).findAllSorted("name");
    }

    /**
     * @return list of all strikes sorted by start_date
     */
    public static RealmResults<Strike> getAllStrikes() {
        return get().where(Strike.class).findAllSorted("start_date", Sort.DESCENDING);
    }

    /**
     * close database
     */
    public static void close() {
        if (isInit) {
            get().close();
        }
    }
}