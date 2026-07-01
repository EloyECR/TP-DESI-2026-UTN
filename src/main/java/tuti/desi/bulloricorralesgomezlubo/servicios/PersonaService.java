package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;

public interface PersonaService {

	List<Persona> getAll();

	List<Persona> getNoEliminadas();

	Persona getById(Long id);
}
