package com.dfl.grevesapp.datamodels;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class submiter
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Submitter extends RealmObject {
    private String first_name;
    private String last_name;

    public Submitter() {
    }
}
