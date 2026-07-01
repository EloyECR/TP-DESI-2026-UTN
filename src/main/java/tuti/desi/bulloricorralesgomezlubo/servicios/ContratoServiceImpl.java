package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IContratoRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;

@Service
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	IContratoRepo repo;

	@Override
	public List<Contrato> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Contrato> getNoEliminados() {
		return repo.findByEliminadoFalse();
	}

	@Override
	public Contrato getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("el contrato", id));
	}
}
