package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IFacturaRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Factura;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	IFacturaRepo repo;

	@Override
	public List<Factura> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Factura> getNoEliminadas() {
		return repo.findByEliminadaFalse();
	}

	@Override
	public Factura getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la factura", id));
	}
}
