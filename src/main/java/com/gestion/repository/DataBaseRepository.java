package com.gestion.repository;

import com.gestion.data.ConsultaEquiposResponse;
import com.gestion.data.Orden;
import com.gestion.data.OrdenResponse;
import com.gestion.data.Tecnico;
import com.gestion.data.tecnicosResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DataBaseRepository {
	
	
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
    @GET("/pls/apex/jader_202310110488/gestiontecnico3/tecnicos")
    Call<tecnicosResponse> obtenerTecnicos();

	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
    @POST("/pls/apex/jader_202310110488/gestiontecnico3/tecnicos")
    Call<ResponseBody> crearTecnico(@Body Tecnico nuevo);

	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
    @PUT("/pls/apex/jader_202310110488/gestiontecnico3/tecnicos")
    Call<ResponseBody> actualizarTecnico(@Body Tecnico actualizar);

	@Headers({
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
    @DELETE("/pls/apex/jader_202310110488/gestiontecnico3/tecnicos")
    Call<ResponseBody> eliminarTecnico(@Query("id_tecnicos") Integer ID_TECNICOS);


	
	// VISTA DE ORDENES
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/jader_202310110488/gestiontecnico3/ordentrabajo")
	Call<OrdenResponse> obtenerOrdenes();

	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/jader_202310110488/gestiontecnico3/ordentrabajo")
	Call<ResponseBody> crearOrden(@Body Orden nuevo);

	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/jader_202310110488/gestiontecnico3/ordentrabajo")
	Call<ResponseBody> actualizarOrden(@Body Orden actualizar);

	@Headers({
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/jader_202310110488/gestiontecnico3/ordentrabajo")
	Call<ResponseBody> eliminarOrden(@Query("id_orden") Integer idOrden);
	
	
	//VISTA DE CONSULTAR EQUIPOS
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/jader_202310110488/gestiontecnico3/consulta_equipos")
	Call<ConsultaEquiposResponse> obtenerEquipos();
}
