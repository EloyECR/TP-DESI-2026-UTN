package tuti.desi.bulloricorralesgomezlubo.servicios;

import org.springframework.stereotype.Service;
import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

@Service
public interface PropiedadContratoService {
    Contrato guardarContrato(Contrato contrato) throws Excepcion;
    Contrato actualizarContrato(Contrato contrato) throws Excepcion;
}