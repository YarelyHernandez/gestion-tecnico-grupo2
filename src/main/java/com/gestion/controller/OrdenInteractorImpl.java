package com.gestion.controller;

import com.gestion.data.ConsultaEquiposResponse;
import com.gestion.data.Orden;
import com.gestion.data.OrdenResponse;
import com.gestion.data.tecnicosResponse;
import com.gestion.repository.DataBaseRepository;
import com.gestion.repository.DataBaseRepositoryImpl;
import com.gestion.views.orden.OrdenView;
import com.gestion.views.orden.OrdenViewModel; // Asegúrate de que esta clase exista o créala

import java.io.IOException;
import java.util.List;

public class OrdenInteractorImpl implements OrdenInteractor {

    private DataBaseRepositoryImpl modelo;
    private OrdenViewModel vista;

    public OrdenInteractorImpl(OrdenViewModel vista) {
        this.modelo = DataBaseRepositoryImpl.getInstance("https://apex.oracle.com", 600000L);
        this.vista = vista;
    }

    @Override
    public void consultarOrdenes() {
        try {
            OrdenResponse respuesta = this.modelo.getOrdenes();
            this.vista.refrescarGridOrdenes(respuesta.getItems());
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al consultar órdenes");
        }
    }

    @Override
    public void crearNuevaOrden(Orden nuevo) {
        try {
            boolean respuesta = this.modelo.createOrden(nuevo);
            this.vista.mostrarMensajeCreacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al crear orden");
        }
    }

    @Override
    public void actualizarOrden(Orden actualizar) {
        try {
            boolean respuesta = this.modelo.updateOrden(actualizar);
            this.vista.mostrarMensajeActualizacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al actualizar orden");
        }
    }

    @Override
    public void eliminarOrden(Integer idOrden) {
        try {
            boolean respuesta = this.modelo.deleteOrden(idOrden);
            this.vista.mostrarMensajeEliminacion(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al eliminar orden");
        }
    }
    
    
    @Override
    public void consultarTecnicos() {
        try {
            tecnicosResponse respuesta = this.modelo.getTecnicos();
            ((OrdenView) this.vista).setListaTecnicos(respuesta.getItems()); // Pasar la lista de técnicos a OrdenView
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al consultar técnicos");
        }
    }

    @Override
    public void consultarEquipos() {
        try {
            ConsultaEquiposResponse respuesta = this.modelo.getConsultaEquipos();
            ((OrdenView) this.vista).setListaEquipos(respuesta.getItems()); // Pasar la lista de equipos a OrdenView
        } catch (IOException e) {
            e.printStackTrace();
            this.vista.mostrarMensajeError("Error al consultar equipos");
        }
    }
}