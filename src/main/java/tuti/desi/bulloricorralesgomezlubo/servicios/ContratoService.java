package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.time.LocalDate;
import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

public interface ContratoService {
	Contrato obtenerContrato(Long id) throws Excepcion;
	List<Contrato> obtenerContratos(Long propiedad, Long inquilino, EstadoContrato estadoContrato, LocalDate fechaDesde, LocalDate fechaHasta);
	Contrato guardar(Contrato contrato) throws Excepcion;
	Contrato actualizar(Contrato contrato) throws Excepcion ;
	Long eliminar(Long id) throws Excepcion;
}