package com.gestion.data;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Orden extends AbstractEntity {

    private String id_orden;
    private LocalDate fecha;
    private String descripcion;
    private String tecnico;

    public String getId_orden() {
        return id_orden;
    }
    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getTecnico() {
        return tecnico;
    }
    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

}
