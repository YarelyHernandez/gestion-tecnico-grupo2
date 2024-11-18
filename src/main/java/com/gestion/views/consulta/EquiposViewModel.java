package com.gestion.views.consulta;

import java.util.List;

import com.gestion.data.ConsultaEquipos;
import com.gestion.data.Orden;

public interface EquiposViewModel {
	
	void mostrarEquiposEnGrid(List<ConsultaEquipos> list);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);

}
