package tuti.desi.bulloricorralesgomezlubo.servicios;

import org.springframework.stereotype.Service;
import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

@Service
public class PropiedadContratoServiceImpl implements PropiedadContratoService {
    private final ContratoService contratoService;
    private final PropiedadService propiedadService;

    public PropiedadContratoServiceImpl(ContratoService contratoService, PropiedadService propiedadService) {
        this.contratoService = contratoService;
        this.propiedadService = propiedadService;
    }

    private void validarPropiedad(Contrato contrato) throws Excepcion {
        if (contrato.getEstado() == EstadoContrato.ACTIVO) {
            Propiedad propiedad = propiedadService.getById(contrato.getPropiedad().getId());
            if (propiedad.getEstadoDisponibilidad() != EstadoDisponibilidad.DISPONIBLE) {
                throw new Excepcion("No se puede hacer un contrato activo si la propiedad no esta disponible");
            }
        }
    }

    public Contrato guardarContrato(Contrato contrato) throws Excepcion {
        validarPropiedad(contrato);
        Contrato nuevoContrato = contratoService.guardar(contrato);

        if (nuevoContrato.getEstado() == EstadoContrato.ACTIVO) {
            Propiedad propiedad = propiedadService.getById(nuevoContrato.getPropiedad().getId());
            propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
            propiedadService.save(propiedad);
        }

        return nuevoContrato;
    }

    public Contrato actualizarContrato(Contrato contrato) throws Excepcion {
        validarPropiedad(contrato);
        Contrato contratoActualizado = contratoService.actualizar(contrato);

        if (contratoActualizado.getEstado() == EstadoContrato.ACTIVO) {
            Propiedad propiedad = propiedadService.getById(contrato.getPropiedad().getId());
            propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
            propiedadService.save(propiedad);
        }

        return contratoActualizado;
    }
}