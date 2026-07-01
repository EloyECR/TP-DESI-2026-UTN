package tuti.desi.bulloricorralesgomezlubo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Propiedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "La direccion es obligatoria")
	@Size(max = 200)
	@Column(nullable = false)
	private String direccion;

	@NotNull(message = "La ciudad es obligatoria")
	@ManyToOne(optional = false)
	private Ciudad ciudad;

	@NotNull(message = "El tipo de propiedad es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TipoPropiedad tipo;

	@NotNull(message = "La cantidad de ambientes es obligatoria")
	@Positive(message = "La cantidad de ambientes debe ser positiva")
	@Column(nullable = false)
	private Integer cantidadAmbientes;

	@NotNull(message = "Los metros cuadrados son obligatorios")
	@Positive(message = "Los metros cuadrados deben ser positivos")
	@Column(nullable = false)
	private Double metrosCuadrados;

	@NotBlank(message = "La descripcion es obligatoria")
	@Size(max = 1000)
	@Column(nullable = false, length = 1000)
	private String descripcion;

	@Size(max = 1000)
	@Column(length = 1000)
	private String comodidades;

	@NotNull(message = "El estado de disponibilidad es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private EstadoDisponibilidad estadoDisponibilidad = EstadoDisponibilidad.DISPONIBLE;

	@NotNull(message = "El propietario es obligatorio")
	@ManyToOne(optional = false)
	private Persona propietario;

	private boolean eliminada = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public TipoPropiedad getTipo() {
		return tipo;
	}

	public void setTipo(TipoPropiedad tipo) {
		this.tipo = tipo;
	}

	public Integer getCantidadAmbientes() {
		return cantidadAmbientes;
	}

	public void setCantidadAmbientes(Integer cantidadAmbientes) {
		this.cantidadAmbientes = cantidadAmbientes;
	}

	public Double getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(Double metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComodidades() {
		return comodidades;
	}

	public void setComodidades(String comodidades) {
		this.comodidades = comodidades;
	}

	public EstadoDisponibilidad getEstadoDisponibilidad() {
		return estadoDisponibilidad;
	}

	public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
		this.estadoDisponibilidad = estadoDisponibilidad;
	}

	public Persona getPropietario() {
		return propietario;
	}

	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
	}

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}
}
