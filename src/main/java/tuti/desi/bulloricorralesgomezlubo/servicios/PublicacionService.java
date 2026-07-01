package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.math.BigDecimal;
import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

public interface PublicacionService {

	List<Publicacion> getAll();

	List<Publicacion> getNoEliminadas();

	Publicacion getById(Long id);

	List<Publicacion> filter(Long propiedadId, Long ciudadId, EstadoPublicacion estado, BigDecimal precioMin,
			BigDecimal precioMax);

	void save(Publicacion publicacion) throws Excepcion;

	void deleteById(Long id) throws Excepcion;
}
