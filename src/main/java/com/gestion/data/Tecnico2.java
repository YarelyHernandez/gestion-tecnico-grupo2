package com.gestion.data;

import jakarta.persistence.Entity;

@Entity
public class Tecnico2 extends AbstractEntity {

    private Integer id_tecnico;
    private String nombre;
    private String especialidad;
    private String telefono;

    public Integer getId_tecnico() {
        return id_tecnico;
    }
    public void setId_tecnico(Integer id_tecnico) {
        this.id_tecnico = id_tecnico;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
