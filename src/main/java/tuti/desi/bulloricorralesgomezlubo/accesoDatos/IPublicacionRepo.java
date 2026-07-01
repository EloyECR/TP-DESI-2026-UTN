package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;

@Repository
public interface IPublicacionRepo extends JpaRepository<Publicacion, Long> {

	List<Publicacion> findByEliminadaFalse();

	boolean existsByPropiedadAndEstadoAndEliminadaFalse(Propiedad propiedad, EstadoPublicacion estado);

	boolean existsByPropiedadAndEstadoAndEliminadaFalseAndIdNot(Propiedad propiedad, EstadoPublicacion estado, Long id);
}
