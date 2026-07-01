package tuti.desi.bulloricorralesgomezlubo.presentacion.propiedades;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;

public class PropiedadesBuscarForm {

	private String direccion;
	private Long ciudadSeleccionada;
	private TipoPropiedad tipo;
	private EstadoDisponibilidad estadoDisponibilidad;

	public String getDireccion() {
		return normalizar(direccion);
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getCiudadSeleccionada() {
		return ciudadSeleccionada;
	}

	public void setCiudadSeleccionada(Long ciudadSeleccionada) {
		this.ciudadSeleccionada = ciudadSeleccionada;
	}

	public TipoPropiedad getTipo() {
		return tipo;
	}

	public void setTipo(TipoPropiedad tipo) {
		this.tipo = tipo;
	}

	public EstadoDisponibilidad getEstadoDisponibilidad() {
		return estadoDisponibilidad;
	}

	public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
		this.estadoDisponibilidad = estadoDisponibilidad;
	}

	private String normalizar(String valor) {
		return valor == null || valor.isBlank() ? null : valor.trim();
	}
}
