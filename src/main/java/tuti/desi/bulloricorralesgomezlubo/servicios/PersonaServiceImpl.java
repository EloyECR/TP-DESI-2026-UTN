package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IPersonaRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	IPersonaRepo repo;

	@Override
	public List<Persona> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Persona> getNoEliminadas() {
		return repo.findByEliminadaFalse();
	}

	@Override
	public Persona getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la persona", id));
	}
}
