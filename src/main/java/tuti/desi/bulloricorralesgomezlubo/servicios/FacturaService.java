package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Factura;

public interface FacturaService {

	List<Factura> getAll();

	List<Factura> getNoEliminadas();

	Factura getById(Long id);
}
