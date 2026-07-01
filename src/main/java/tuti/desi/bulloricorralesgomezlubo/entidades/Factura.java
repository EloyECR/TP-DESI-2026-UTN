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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "El contrato es obligatorio")
	@ManyToOne(optional = false)
	private Contrato contrato;

	@NotBlank(message = "El concepto facturado es obligatorio")
	@Size(max = 200)
	@Column(nullable = false)
	private String conceptoFacturado;

	@NotNull(message = "La fecha de emision es obligatoria")
	@Column(nullable = false)
	private LocalDate fechaEmision;

	@NotNull(message = "La fecha de vencimiento es obligatoria")
	@Column(nullable = false)
	private LocalDate fechaVencimiento;

	@NotNull(message = "El importe es obligatorio")
	@DecimalMin(value = "0.01", message = "El importe debe ser positivo")
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal importe;

	@NotNull(message = "El estado es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private EstadoFactura estado = EstadoFactura.PENDIENTE;

	private LocalDate fechaPago;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private MedioPago medio;

	@DecimalMin(value = "0.01", message = "El importe pagado debe ser positivo")
	@Column(precision = 12, scale = 2)
	private BigDecimal importePagado;

	@DecimalMin(value = "0.00", message = "El interes no puede ser negativo")
	@Column(precision = 12, scale = 2)
	private BigDecimal interes;

	private boolean eliminada = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getConceptoFacturado() {
		return conceptoFacturado;
	}

	public void setConceptoFacturado(String conceptoFacturado) {
		this.conceptoFacturado = conceptoFacturado;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public EstadoFactura getEstado() {
		return estado;
	}

	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public MedioPago getMedio() {
		return medio;
	}

	public void setMedio(MedioPago medio) {
		this.medio = medio;
	}

	public BigDecimal getImportePagado() {
		return importePagado;
	}

	public void setImportePagado(BigDecimal importePagado) {
		this.importePagado = importePagado;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}
}
