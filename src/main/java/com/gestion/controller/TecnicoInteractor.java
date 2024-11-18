package com.gestion.controller;

import com.gestion.data.Tecnico;

public interface TecnicoInteractor {

	void consultarTecnicos();

	void crearNuevoTecnico(Tecnico nuevo);

	void actualizarTecnico(Tecnico actualizar);

	void eliminarTecnico(Integer idTecnico);

}
