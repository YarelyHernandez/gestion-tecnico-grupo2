package com.gestion.controller;

import com.gestion.data.Orden;

public interface OrdenInteractor {

	void consultarOrdenes();

	void crearNuevaOrden(Orden nuevo);

	void actualizarOrden(Orden actualizar);

	void eliminarOrden(Integer idOrden);
	
    void consultarTecnicos();

    void consultarEquipos();

}
