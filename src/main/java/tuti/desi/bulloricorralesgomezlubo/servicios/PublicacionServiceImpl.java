package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IPublicacionRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;
import tuti.desi.bulloricorralesgomezlubo.excepciones.EntidadNoEncontradaException;

@Service
public class PublicacionServiceImpl implements PublicacionService {

	@Autowired
	IPublicacionRepo repo;

	@Override
	public List<Publicacion> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Publicacion> getNoEliminadas() {
		return repo.findByEliminadaFalse();
	}

	@Override
	public Publicacion getById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaException("la publicacion", id));
	}
}
