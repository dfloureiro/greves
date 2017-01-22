package com.dfl.grevesapp.Utils;

import com.dfl.grevesapp.R;
import com.dfl.grevesapp.datamodels.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Diogo Loureiro on 02/01/2017.
 *
 * Companies Utils
 */
public class CompaniesUtils{
    /**
     * return the icon type of the company
     * @param company company on strike
     * @return id of the resource
     */
    public static int getIconType(String company){
        switch (company){
            case "Metro de Lisboa":
                return R.drawable.ic_subway;
            case "Metro do Porto":
                return R.drawable.ic_subway;
            case "Táxis":
                return R.drawable.ic_car;
            case "TAP":
                return R.drawable.ic_plane;
            case "Aviação":
                return R.drawable.ic_plane;
            case "Soflusa e Transtejo":
                return R.drawable.ic_boat;
            case "Soflusa":
                return R.drawable.ic_boat;
            case "Transtejo":
                return R.drawable.ic_boat;
            case "CP":
                return R.drawable.ic_train;
            case "Fertagus":
                return R.drawable.ic_train;
            case "Carris":
                return R.drawable.ic_bus;
            case "Rodoviária de Lisboa":
                return R.drawable.ic_bus;
            case "Barraqueiro Transportes":
                return R.drawable.ic_bus;
            default:
                return R.drawable.ic_megaphone;
        }
    }

    /**
     * order companies by name
     * @param companies list of companies
     */
    public static void sortCompanies(ArrayList<Company> companies){
        Comparator<Company> comparator = new Comparator<Company>() {
            @Override
            public int compare(Company c1, Company c2) {
                return c1.getName().compareTo(c2.getName());
            }
        };
        Collections.sort(companies, comparator);
    }
}
