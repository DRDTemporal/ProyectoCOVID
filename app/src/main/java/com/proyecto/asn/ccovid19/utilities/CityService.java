package com.proyecto.asn.ccovid19.utilities;

import com.proyecto.asn.ccovid19.models.Lugar;
import static com.proyecto.asn.ccovid19.utilities.Constants.LINK_LUGARES;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;



public interface CityService {

    @GET(LINK_LUGARES)
    Call<List<Lugar>> getLugares();

}
