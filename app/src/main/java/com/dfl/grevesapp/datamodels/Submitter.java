package com.dfl.grevesapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * class submiter
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
public class Submitter {
    private String first_name;
    private String last_name;
}
