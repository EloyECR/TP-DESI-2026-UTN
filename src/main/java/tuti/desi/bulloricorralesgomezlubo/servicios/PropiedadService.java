package tuti.desi.bulloricorralesgomezlubo.servicios;

import java.util.List;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;

public interface PropiedadService {

	List<Propiedad> getAll();

	List<Propiedad> getNoEliminadas();

	List<Propiedad> filter(String direccion, Long ciudadId, TipoPropiedad tipo, EstadoDisponibilidad estado);

	void save(Propiedad propiedad) throws Excepcion;

	Propiedad getById(Long id);

	void deleteById(Long id) throws Excepcion;
}
