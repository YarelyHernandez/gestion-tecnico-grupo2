package com.gestion.controller;

import com.gestion.data.Tecnico;
import com.gestion.data.tecnicosResponse;
import com.gestion.repository.DataBaseRepository;
import com.gestion.repository.DataBaseRepositoryImpl;
import com.gestion.views.tecnico.TecnicoViewModel;
import java.io.IOException;

public class TecnicoInteractorImpl implements TecnicoInteractor {

    private DataBaseRepositoryImpl modelo;
    private TecnicoViewModel vista;

    public TecnicoInteractorImpl(TecnicoViewModel vista) {
        this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 600000L);
        this.vista = vista;
    }

    @Override
    public void consultarTecnicos() {
        try {
            tecnicosResponse respuesta = this.modelo.getTecnicos();
            this.vista.refrescarGridTecnicos(respuesta.getItems());
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al consultar técnicos");
        }
    }

    @Override
    public void crearNuevoTecnico(Tecnico nuevo) {
        try {
            boolean respuesta = this.modelo.createTecnico(nuevo);
            this.vista.mostrarMensajeCreacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al crear técnico");
        }
    }

    @Override
    public void actualizarTecnico(Tecnico actualizar) {
        try {
            boolean respuesta = this.modelo.updateTecnico(actualizar);
            this.vista.mostrarMensajeActualizacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al actualizar técnico");
        }
    }

    @Override
    public void eliminarTecnico(Integer idTecnico) {
        try {
            boolean respuesta = this.modelo.deleteTecnico(idTecnico);
            this.vista.mostrarMensajeEliminacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al eliminar técnico");
        }
    }
}