package com.proyecto.asn.ccovid19.utilities;

import com.proyecto.asn.ccovid19.models.HoraFecha;
import com.proyecto.asn.ccovid19.models.Lugar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.proyecto.asn.ccovid19.utilities.Constants.LINK_API_WORLD_TIME;


public interface WorldTimeService {
    @GET(LINK_API_WORLD_TIME)
    Call<HoraFecha> getWorldTime();
}
