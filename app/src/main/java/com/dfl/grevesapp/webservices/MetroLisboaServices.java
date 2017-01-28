package com.dfl.grevesapp.webservices;

import com.dfl.grevesapp.datamodels.LisbonSubwayLinesStatus;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Diogo Loureiro on 28/01/2017.
 * <p>
 * metrolisboa.pt services
 */

public interface MetroLisboaServices {

    @GET("status/getLinhas.php")
    Call<LisbonSubwayLinesStatus> getLineStatus();
}
