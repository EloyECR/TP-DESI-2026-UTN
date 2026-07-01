package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;

public interface PublicacionService {

	List<Publicacion> getAll();

	List<Publicacion> getNoEliminadas();

	Publicacion getById(Long id);
}
