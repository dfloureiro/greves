package com.dfl.grevesapp.webservices;


import com.dfl.grevesapp.datamodels.Company;
import com.dfl.grevesapp.datamodels.Strike;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 * hagreves.com api
 */
public interface HaGrevesServices {

    @GET("v2/allstrikes")
    Call<Strike[]> getAllStrikes();

    @GET("v1/strikes")
    Call<Strike[]> getStrikes();

    @GET("v2/companies")
    Call<Company[]> getCompanies();

}
