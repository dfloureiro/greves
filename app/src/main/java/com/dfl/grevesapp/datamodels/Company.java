package com.dfl.grevesapp.datamodels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 * class company
 */
@EqualsAndHashCode(callSuper = false)
@Data public class Company extends RealmObject {
    private String name;
    @PrimaryKey private int id;
}
