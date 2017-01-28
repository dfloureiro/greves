package com.dfl.grevesapp.datamodels;

import lombok.Data;

/**
 * Created by Diogo Loureiro on 28/01/2017.
 * <p>
 * data model from Lisbon subway get lines status
 */

@Data
public class LisbonSubwayLinesStatus {
    private String amarela;
    private String azul;
    private String verde;
    private String vermelha;
    private String tipo_msg_am;
    private String tipo_msg_az;
    private String tipo_msg_vd;
    private String tipo_msg_vm;
}
