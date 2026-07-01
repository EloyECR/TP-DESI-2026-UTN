package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IContratoRepo;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IHistorialEstadoPropiedadRepo;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IPropiedadRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.HistorialEstadoPropiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

@Service
public class PropiedadServiceImpl implements PropiedadService {

	@Autowired
	IPropiedadRepo repo;

	@Autowired
	IContratoRepo contratoRepo;

	@Autowired
	IHistorialEstadoPropiedadRepo historialRepo;

	@Override
	public List<Propiedad> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Propiedad> getNoEliminadas() {
		return repo.findByEliminadaFalse();
	}

	@Override
	public List<Propiedad> filter(String direccion, Long ciudadId, TipoPropiedad tipo, EstadoDisponibilidad estado) {
		return repo.filter(normalizar(direccion), ciudadId, tipo, estado);
	}

	@Override
	public void save(Propiedad propiedad) throws Excepcion {
		boolean direccionDuplicada = propiedad.getId() == null
				? repo.existsByDireccionAndCiudadAndEliminadaFalse(propiedad.getDireccion(), propiedad.getCiudad())
				: repo.existsByDireccionAndCiudadAndEliminadaFalseAndIdNot(propiedad.getDireccion(),
						propiedad.getCiudad(), propiedad.getId());

		if (direccionDuplicada) {
			throw new Excepcion("Ya existe una propiedad activa con la misma direccion y ciudad", "direccion");
		}

		EstadoDisponibilidad estadoAnterior = null;
		if (propiedad.getId() != null) {
			Propiedad actual = getById(propiedad.getId());
			estadoAnterior = actual.getEstadoDisponibilidad();

			if (tieneContratoActivo(actual) && esEstadoNoPermitidoConContrato(propiedad.getEstadoDisponibilidad())) {
				throw new Excepcion("No se puede cambiar el estado porque la propiedad tiene un contrato activo",
						"estadoDisponibilidad");
			}
		}

		Propiedad guardada = repo.save(propiedad);

		if (estadoAnterior == null || estadoAnterior != guardada.getEstadoDisponibilidad()) {
			guardarHistorial(guardada);
		}
	}

	@Override
	public Propiedad getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la propiedad", id));
	}

	@Override
	public void deleteById(Long id) throws Excepcion {
		Propiedad propiedad = getById(id);

		if (tieneContratoActivo(propiedad)) {
			throw new Excepcion("No se puede eliminar una propiedad con contrato activo");
		}

		propiedad.setEliminada(true);
		repo.save(propiedad);
	}

	private boolean tieneContratoActivo(Propiedad propiedad) {
		return contratoRepo.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO);
	}

	private boolean esEstadoNoPermitidoConContrato(EstadoDisponibilidad estado) {
		return estado == EstadoDisponibilidad.DISPONIBLE || estado == EstadoDisponibilidad.INACTIVA;
	}

	private void guardarHistorial(Propiedad propiedad) {
		HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
		historial.setPropiedad(propiedad);
		historial.setEstado(propiedad.getEstadoDisponibilidad());
		historial.setFechaHora(LocalDateTime.now());
		historialRepo.save(historial);
	}

	private String normalizar(String valor) {
		return valor == null || valor.isBlank() ? null : valor.trim();
	}
}
