package com.gestion.repository;

import retrofit2.Response;

import java.io.IOException;

import com.gestion.data.ConsultaEquiposResponse;
import com.gestion.data.Orden;
import com.gestion.data.OrdenResponse;
import com.gestion.data.Tecnico;
import com.gestion.data.tecnicosResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DataBaseRepositoryImpl {
	
	private static DataBaseRepositoryImpl INSTANCE;
	private DataBaseEquipos equipos;
	
	private DataBaseRepositoryImpl(String url, Long timeout) {
		equipos = new DataBaseEquipos(url, timeout);
	}
		
	public static DataBaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DataBaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DataBaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	
	//VISTA DE TECNICOS
    public tecnicosResponse getTecnicos() throws IOException {
        Call<tecnicosResponse> call = equipos.getDataBase().obtenerTecnicos();
        Response<tecnicosResponse> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    public boolean createTecnico(Tecnico nuevo) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().crearTecnico(nuevo);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }

    public boolean updateTecnico(Tecnico actualizar) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().actualizarTecnico(actualizar);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }

    public boolean deleteTecnico(Integer idTecnico) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().eliminarTecnico(idTecnico);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }
    
    
 // VISTA DE ORDENES
    public OrdenResponse getOrdenes() throws IOException {
        Call<OrdenResponse> call = equipos.getDataBase().obtenerOrdenes();
        Response<OrdenResponse> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    public boolean createOrden(Orden nuevo) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().crearOrden(nuevo);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }

    public boolean updateOrden(Orden actualizar) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().actualizarOrden(actualizar);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }

    public boolean deleteOrden(Integer idOrden) throws IOException {
        Call<ResponseBody> call = equipos.getDataBase().eliminarOrden(idOrden);
        Response<ResponseBody> response = call.execute();
        return response.isSuccessful();
    }
    
    //Vista de Consultar Equipos
    public ConsultaEquiposResponse getConsultaEquipos() throws IOException {
        Call<ConsultaEquiposResponse> call = equipos.getDataBase().obtenerEquipos();
        Response<ConsultaEquiposResponse> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null; 
        }
    }
}
