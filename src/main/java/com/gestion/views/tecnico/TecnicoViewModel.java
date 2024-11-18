package com.gestion.views.tecnico;

import java.util.List;

import com.gestion.data.Tecnico;

public interface TecnicoViewModel {

	void refrescarGridTecnicos(List<Tecnico> items);

	void mostrarMensajeCreacion(boolean respuesta);

	void mostrarMensajeError(String string);

	void mostrarMensajeActualizacion(boolean respuesta);

	void mostrarMensajeEliminacion(boolean respuesta);


}
