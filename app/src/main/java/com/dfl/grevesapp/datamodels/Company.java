package com.dfl.grevesapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class company
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class Company {
    private int id;
    private String name;
}
