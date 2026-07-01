package tuti.desi.bulloricorralesgomezlubo.presentacion.publicaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;

public class PublicacionForm {

	private Long id;

	@NotNull(message = "La propiedad es obligatoria")
	private Long idPropiedad;

	@NotNull(message = "El precio mensual es obligatorio")
	@DecimalMin(value = "0.01", message = "El precio mensual debe ser positivo")
	private BigDecimal precioMensual;

	@NotBlank(message = "Las condiciones son obligatorias")
	@Size(max = 2000)
	private String condiciones;

	@NotBlank(message = "La descripcion es obligatoria")
	@Size(max = 2000)
	private String descripcion;

	@NotNull(message = "La fecha de publicacion es obligatoria")
	private LocalDate fechaPublicacion;

	@NotNull(message = "El estado es obligatorio")
	private EstadoPublicacion estado = EstadoPublicacion.ACTIVA;

	public PublicacionForm() {
	}

	public PublicacionForm(Publicacion publicacion) {
		this.id = publicacion.getId();
		this.idPropiedad = publicacion.getPropiedad() == null ? null : publicacion.getPropiedad().getId();
		this.precioMensual = publicacion.getPrecioMensual();
		this.condiciones = publicacion.getCondiciones();
		this.descripcion = publicacion.getDescripcion();
		this.fechaPublicacion = publicacion.getFechaPublicacion();
		this.estado = publicacion.getEstado();
	}

	public Publicacion toPojo() {
		Publicacion publicacion = new Publicacion();
		publicacion.setId(id);
		publicacion.setPrecioMensual(precioMensual);
		publicacion.setCondiciones(condiciones);
		publicacion.setDescripcion(descripcion);
		publicacion.setFechaPublicacion(fechaPublicacion);
		publicacion.setEstado(estado);
		return publicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(Long idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public BigDecimal getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(BigDecimal precioMensual) {
		this.precioMensual = precioMensual;
	}

	public String getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}
}
