package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;

public interface CiudadService {

	List<Ciudad> getAll();

	Ciudad getById(Long id);
}
