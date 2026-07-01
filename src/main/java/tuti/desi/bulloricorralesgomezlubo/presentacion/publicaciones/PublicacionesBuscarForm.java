package tuti.desi.bulloricorralesgomezlubo.presentacion.publicaciones;

import java.math.BigDecimal;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;

public class PublicacionesBuscarForm {

	private Long propiedadId;
	private Long ciudadId;
	private EstadoPublicacion estado;
	private BigDecimal precioMin;
	private BigDecimal precioMax;

	public Long getPropiedadId() {
		return propiedadId;
	}

	public void setPropiedadId(Long propiedadId) {
		this.propiedadId = propiedadId;
	}

	public Long getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(Long ciudadId) {
		this.ciudadId = ciudadId;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	public BigDecimal getPrecioMin() {
		return precioMin;
	}

	public void setPrecioMin(BigDecimal precioMin) {
		this.precioMin = precioMin;
	}

	public BigDecimal getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(BigDecimal precioMax) {
		this.precioMax = precioMax;
	}
}
