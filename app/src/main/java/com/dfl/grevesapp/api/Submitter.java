package com.dfl.grevesapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 */
public class Submitter {

    @Getter @Setter private String first_name;
    @Getter @Setter private String last_name;

    @Override
    public String toString() {
        return "first_name: "+first_name+" , "+
                "last_name: "+last_name;
    }
}
