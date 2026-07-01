package tuti.desi.bulloricorralesgomezlubo.servicios;


import org.springframework.stereotype.Service;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IContratoRepo;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IHistorialEstadoContratoRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.*;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContratoServiceImpl implements ContratoService  {
	private final IContratoRepo contratoRepository;
	private final IHistorialEstadoContratoRepo historialEstadoRepository;

	public ContratoServiceImpl (IContratoRepo contratoRepository, IHistorialEstadoContratoRepo historialEstadoRepository) {
		this.contratoRepository = contratoRepository;
		this.historialEstadoRepository = historialEstadoRepository;
	}

	public Contrato obtenerContrato(Long id) throws Excepcion {
		Contrato contrato = contratoRepository.findById(id).isPresent() ? contratoRepository.findById(id).get() : null;
		if (contrato == null || contrato.isEliminado()) {
			throw new Excepcion("El contrato no existe");
		}

		return contrato;
	}

	public List<Contrato> obtenerContratos(Long propiedad, Long persona, EstadoContrato estadoContrato, LocalDate fechaInicio, LocalDate fechaFin) {
		return contratoRepository.findByFilters(propiedad, persona, estadoContrato, fechaInicio, fechaFin);
	}

	public Contrato guardar(Contrato contrato) throws Excepcion {
		if (contrato.getEstado() == EstadoContrato.ACTIVO) {
			Contrato existeContratoActivo = contratoRepository.findByEliminadoFalseAndIdNotAndPropiedadAndEstado(contrato.getId(), contrato.getPropiedad(), EstadoContrato.ACTIVO);
			if (existeContratoActivo != null) {
				throw new Excepcion("Ya existe un contrato activo");
			}
		}

		Contrato nuevoContrato = contratoRepository.save(contrato);

		return nuevoContrato;
	}

	public Contrato actualizar(Contrato contrato) throws Excepcion {
		if (contrato.getId() == null)
			throw new Excepcion("Debes ingresar el id para editar");

		Contrato contratoExistente = obtenerContrato(contrato.getId());

		contrato.setId(contratoExistente.getId());
		if (contrato.getEstado() == EstadoContrato.ACTIVO) {
			Contrato existeContratoActivo = contratoRepository.findByEliminadoFalseAndIdNotAndPropiedadAndEstado(contrato.getId(), contrato.getPropiedad(), EstadoContrato.ACTIVO);
			if (existeContratoActivo != null) {
				throw new Excepcion("Ya existe un contrato activo");
			}
		}

		if (contrato.getEstado() == EstadoContrato.ACTIVO && (contratoExistente.getEstado() == EstadoContrato.RESCINDIDO || contratoExistente.getEstado() == EstadoContrato.FINALIZADO)) {
			throw new Excepcion("No puedes volver a activar un contrato que ya finalizo o se rescindio");
		}
		if (contratoExistente.getEstado() == EstadoContrato.BORRADOR && contrato.getEstado() != EstadoContrato.ACTIVO) {
			throw new Excepcion("No puedes cambiar el estado borrador de un contrato a uno diferente a activo");
		}

		if (contrato.getEstado() != contratoExistente.getEstado()) {
			var historialEstado = new HistorialEstadoContrato();
			historialEstado.setContrato(contrato);
			historialEstado.setFechaHora(LocalDateTime.now());
			historialEstado.setEstado(contratoExistente.getEstado());
			historialEstadoRepository.save(historialEstado);
		}

		return contratoRepository.save(contrato);
	}

	public Long eliminar(Long id) throws Excepcion {
		Contrato contrato = obtenerContrato(id);

		if (contrato.getEstado() != EstadoContrato.BORRADOR) {
			throw new Excepcion("No se puede eliminar un contrato que no este en estado borrador");
		}

		contrato.setEliminado(true);
		contratoRepository.save(contrato);

		return id;
	}
}
