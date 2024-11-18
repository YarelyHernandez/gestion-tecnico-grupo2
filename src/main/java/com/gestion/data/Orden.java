package com.gestion.data;


public class Orden /*extends AbstractEntity*/ {

    private Integer id_orden;
    private String fecha_creacion;
    private String estado;
    private String descripcion;
    private String id_tecnicos; 
    private String id_equipos;
    
	public Integer getId_orden() {
		return id_orden;
	}
	public void setId_orden(Integer id_orden) {
		this.id_orden = id_orden;
	}
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId_tecnicos() {
		return id_tecnicos;
	}
	public void setId_tecnicos(String id_tecnicos) {
		this.id_tecnicos = id_tecnicos;
	}
	public String getId_equipos() {
		return id_equipos;
	}
	public void setId_equipos(String id_equipos) {
		this.id_equipos = id_equipos;
	}

    
}
