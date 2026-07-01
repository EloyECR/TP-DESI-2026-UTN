package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;

public interface ContratoService {

	List<Contrato> getAll();

	List<Contrato> getNoEliminados();

	Contrato getById(Long id);
}
