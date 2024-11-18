package com.gestion.controller;

import com.gestion.data.ConsultaEquiposResponse;
import com.gestion.data.Orden;
import com.gestion.data.OrdenResponse;
import com.gestion.data.tecnicosResponse;
import com.gestion.repository.DataBaseRepositoryImpl;
import com.gestion.views.consulta.EquiposViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquiposInteractorImpl implements EquiposInteractor {

    private DataBaseRepositoryImpl modelo;
    private EquiposViewModel vista;

    public EquiposInteractorImpl(EquiposViewModel vista) {
        super();
        this.vista = vista;
        this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 600000L);
    }
    
    @Override
    public void consultarEquipos() {
        try {
            ConsultaEquiposResponse respuesta = this.modelo.getConsultaEquipos();
            this.vista.mostrarEquiposEnGrid(respuesta.getItems());
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al consultar técnicos");
        }
    }

}