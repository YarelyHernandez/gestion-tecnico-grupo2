package com.gestion.views.orden;

import java.util.List;

import com.gestion.data.Orden;

public interface OrdenViewModel {

	void refrescarGridOrdenes(List<Orden> items);

	void mostrarMensajeError(String string);

	void mostrarMensajeCreacion(boolean respuesta);

	void mostrarMensajeActualizacion(boolean respuesta);

	void mostrarMensajeEliminacion(boolean respuesta);

}
