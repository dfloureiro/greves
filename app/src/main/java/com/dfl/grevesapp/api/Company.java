package com.dfl.grevesapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 */
public class Company {

    @Getter @Setter private String name;
    @Getter @Setter private int id;

    @Override
    public String toString() {
        return "name: "+name+" , "+
                "id: "+id;
    }
}
