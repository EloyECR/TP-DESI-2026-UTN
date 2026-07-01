package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.ICiudadRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;

@Service
public class CiudadServiceImpl implements CiudadService {

	@Autowired
	ICiudadRepo repo;

	@Override
	public List<Ciudad> getAll() {
		return repo.findAll();
	}

	@Override
	public Ciudad getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la ciudad", id));
	}
}
