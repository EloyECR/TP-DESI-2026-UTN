package tuti.desi.bulloricorralesgomezlubo.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "La propiedad es obligatoria")
	@ManyToOne(optional = false)
	private Propiedad propiedad;

	@NotNull(message = "El propietario es obligatorio")
	@ManyToOne(optional = false)
	private Persona propietario;

	@NotNull(message = "El inquilino es obligatorio")
	@ManyToOne(optional = false)
	private Persona inquilino;

	@NotNull(message = "La fecha de inicio es obligatoria")
	@Column(nullable = false)
	private LocalDate fechaInicio;

	@NotNull(message = "La duracion en meses es obligatoria")
	@Positive(message = "La duracion en meses debe ser positiva")
	@Column(nullable = false)
	private Integer duracionMeses;

	@NotNull(message = "El importe mensual es obligatorio")
	@DecimalMin(value = "0.01", message = "El importe mensual debe ser positivo")
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal importeMensual;

	@NotNull(message = "El dia de vencimiento es obligatorio")
	@Min(value = 1, message = "El dia de vencimiento debe ser entre 1 y 31")
	@Max(value = 31, message = "El dia de vencimiento debe ser entre 1 y 31")
	@Column(nullable = false)
	private Integer diaVencimientoMensual;

	@NotBlank(message = "La descripcion es obligatoria")
	@Size(max = 1000)
	@Column(nullable = false, length = 1000)
	private String descripcion;

	@NotNull(message = "El estado es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private EstadoContrato estado = EstadoContrato.BORRADOR;

	private boolean eliminado = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public Persona getPropietario() {
		return propietario;
	}

	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
	}

	public Persona getInquilino() {
		return inquilino;
	}

	public void setInquilino(Persona inquilino) {
		this.inquilino = inquilino;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Integer getDuracionMeses() {
		return duracionMeses;
	}

	public void setDuracionMeses(Integer duracionMeses) {
		this.duracionMeses = duracionMeses;
	}

	public BigDecimal getImporteMensual() {
		return importeMensual;
	}

	public void setImporteMensual(BigDecimal importeMensual) {
		this.importeMensual = importeMensual;
	}

	public Integer getDiaVencimientoMensual() {
		return diaVencimientoMensual;
	}

	public void setDiaVencimientoMensual(Integer diaVencimientoMensual) {
		this.diaVencimientoMensual = diaVencimientoMensual;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoContrato getEstado() {
		return estado;
	}

	public void setEstado(EstadoContrato estado) {
		this.estado = estado;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
}
