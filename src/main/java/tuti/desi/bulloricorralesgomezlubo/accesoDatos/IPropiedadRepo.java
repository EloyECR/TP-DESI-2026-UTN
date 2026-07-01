package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;

@Repository
public interface IPropiedadRepo extends JpaRepository<Propiedad, Long> {

	List<Propiedad> findByEliminadaFalse();

	List<Propiedad> findByEstadoDisponibilidadAndEliminadaFalse(EstadoDisponibilidad estadoDisponibilidad);

	boolean existsByDireccionAndCiudadAndEliminadaFalse(String direccion, Ciudad ciudad);

	boolean existsByDireccionAndCiudadAndEliminadaFalseAndIdNot(String direccion, Ciudad ciudad, Long id);
}
