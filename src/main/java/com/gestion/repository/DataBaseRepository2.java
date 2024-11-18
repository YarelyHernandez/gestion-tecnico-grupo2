package com.gestion.repository;

import com.gestion.data.tecnicosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface DataBaseRepository2 {
	
	@Headers({
	    "Accept: application/vnd.github.v3.full+json",
	    "User-Agent: gestion-tecnico"
	})
	@GET("/pls/apex/jader_202310110488/gestiontecnico2/tecnico")
	Call<tecnicosResponse> obtenerTecnicos();

}
