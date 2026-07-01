package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;

@Repository
public interface IPublicacionRepo extends JpaRepository<Publicacion, Long> {

	@Query("select p from Publicacion p join fetch p.propiedad prop join fetch prop.ciudad where p.eliminada = false")
	List<Publicacion> findByEliminadaFalse();

	boolean existsByPropiedadAndEstadoAndEliminadaFalse(Propiedad propiedad, EstadoPublicacion estado);

	boolean existsByPropiedadAndEstadoAndEliminadaFalseAndIdNot(Propiedad propiedad, EstadoPublicacion estado, Long id);

	@Query("""
			select p from Publicacion p
			join fetch p.propiedad prop
			where p.eliminada = false
			and (:propiedadId is null or prop.id = :propiedadId)
			and (:ciudadId is null or prop.ciudad.id = :ciudadId)
			and (:estado is null or p.estado = :estado)
			and (:precioMin is null or p.precioMensual >= :precioMin)
			and (:precioMax is null or p.precioMensual <= :precioMax)
			""")
	List<Publicacion> filter(@Param("propiedadId") Long propiedadId, @Param("ciudadId") Long ciudadId,
			@Param("estado") EstadoPublicacion estado, @Param("precioMin") BigDecimal precioMin,
			@Param("precioMax") BigDecimal precioMax);
}
