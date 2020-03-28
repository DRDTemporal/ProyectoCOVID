package com.proyecto.asn.ccovid19.utilities;

import com.proyecto.asn.ccovid19.models.HoraFecha;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.proyecto.asn.ccovid19.utilities.Constants.LINK_HORA_COLOMBIANA;


public interface WorldTimeService {
    @GET(LINK_HORA_COLOMBIANA)
    Call<HoraFecha> getWorldTime();
}
