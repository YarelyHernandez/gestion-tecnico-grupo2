package com.gestion.data;

public class Tecnico /*extends AbstractEntity*/ {
	
	private Integer id_tecnicos;
    private String nombre;
    private String especializacion;
    private String telefono;

    public Integer getId_tecnicos() {
		return id_tecnicos;
	}
	public void setId_tecnicos(Integer tecnico_seleccionado_id) {
		this.id_tecnicos = tecnico_seleccionado_id;
	}
	public String getnombre() {
		return nombre;
	}
	public void setnombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEspecializacion() {
		return especializacion;
	}
	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
