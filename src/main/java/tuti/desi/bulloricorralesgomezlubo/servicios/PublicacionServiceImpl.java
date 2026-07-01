package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IHistorialEstadoPublicacionRepo;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IPublicacionRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.HistorialEstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

@Service
public class PublicacionServiceImpl implements PublicacionService {

	@Autowired
	IPublicacionRepo repo;

	@Autowired
	IHistorialEstadoPublicacionRepo historialRepo;

	@Override
	public List<Publicacion> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Publicacion> getNoEliminadas() {
		return repo.findByEliminadaFalse();
	}

	@Override
	public Publicacion getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la publicacion", id));
	}

	@Override
	public List<Publicacion> filter(Long propiedadId, Long ciudadId, EstadoPublicacion estado, BigDecimal precioMin,
			BigDecimal precioMax) {
		return repo.filter(propiedadId, ciudadId, estado, precioMin, precioMax);
	}

	@Override
	public void save(Publicacion publicacion) throws Excepcion {
		if (publicacion.getPropiedad() == null || publicacion.getPropiedad().getId() == null) {
			throw new Excepcion("La propiedad es obligatoria", "idPropiedad");
		}

		if (publicacion.getPropiedad().isEliminada()) {
			throw new Excepcion("Solo pueden publicarse propiedades activas y no eliminadas", "idPropiedad");
		}

		EstadoPublicacion estadoAnterior = null;
		boolean esNueva = publicacion.getId() == null;

		if (!esNueva) {
			Publicacion actual = getById(publicacion.getId());
			estadoAnterior = actual.getEstado();

			if (actual.getEstado() == EstadoPublicacion.FINALIZADA) {
				if (!actual.getCondiciones().equals(publicacion.getCondiciones())) {
					throw new Excepcion("No se pueden modificar las condiciones de una publicacion finalizada",
							"condiciones");
				}
			}

			if (!esTransicionValida(actual.getEstado(), publicacion.getEstado())) {
				throw new Excepcion(
						"No se puede cambiar el estado de " + actual.getEstado() + " a " + publicacion.getEstado(),
						"estado");
			}

			if (publicacion.getEstado() == EstadoPublicacion.ACTIVA
					&& actual.getEstado() != EstadoPublicacion.ACTIVA
					&& publicacion.getPropiedad().getEstadoDisponibilidad() != EstadoDisponibilidad.DISPONIBLE) {
				throw new Excepcion("Solo se puede activar una publicacion si la propiedad esta disponible",
						"idPropiedad");
			}
		} else {
			if (publicacion.getPropiedad().getEstadoDisponibilidad() != EstadoDisponibilidad.DISPONIBLE) {
				throw new Excepcion("Solo pueden publicarse propiedades que se encuentren disponibles", "idPropiedad");
			}

			publicacion.setEstado(EstadoPublicacion.ACTIVA);
		}

		boolean activaDuplicada = esNueva
				? repo.existsByPropiedadAndEstadoAndEliminadaFalse(publicacion.getPropiedad(), EstadoPublicacion.ACTIVA)
				: repo.existsByPropiedadAndEstadoAndEliminadaFalseAndIdNot(publicacion.getPropiedad(),
						EstadoPublicacion.ACTIVA, publicacion.getId());

		if (activaDuplicada) {
			throw new Excepcion("Ya existe una publicacion activa para la misma propiedad", "idPropiedad");
		}

		Publicacion guardada = repo.save(publicacion);

		if (estadoAnterior == null || estadoAnterior != guardada.getEstado()) {
			guardarHistorial(guardada);
		}
	}

	private boolean esTransicionValida(EstadoPublicacion anterior, EstadoPublicacion nuevo) {
		if (anterior == nuevo) {
			return true;
		}
		if (anterior == EstadoPublicacion.FINALIZADA) {
			return false;
		}
		if (nuevo == EstadoPublicacion.FINALIZADA) {
			return anterior == EstadoPublicacion.ACTIVA;
		}
		return true;
	}

	@Override
	public void deleteById(Long id) throws Excepcion {
		Publicacion publicacion = getById(id);

		if (publicacion.getEstado() != EstadoPublicacion.ACTIVA) {
			throw new Excepcion("Solo pueden eliminarse publicaciones activas");
		}

		publicacion.setEliminada(true);
		repo.save(publicacion);
	}

	private void guardarHistorial(Publicacion publicacion) {
		HistorialEstadoPublicacion historial = new HistorialEstadoPublicacion();
		historial.setPublicacion(publicacion);
		historial.setEstado(publicacion.getEstado());
		historial.setFechaHora(LocalDateTime.now());
		historialRepo.save(historial);
	}
}
