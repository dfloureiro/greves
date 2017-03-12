package com.dfl.grevesapp.datamodels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class company
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Company extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;

    public Company() {
    }
}
