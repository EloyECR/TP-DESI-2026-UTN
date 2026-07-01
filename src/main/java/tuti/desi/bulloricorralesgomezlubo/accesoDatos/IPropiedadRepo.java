package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;

@Repository
public interface IPropiedadRepo extends JpaRepository<Propiedad, Long> {

	List<Propiedad> findByEliminadaFalse();

	List<Propiedad> findByEstadoDisponibilidadAndEliminadaFalse(EstadoDisponibilidad estadoDisponibilidad);

	boolean existsByDireccionAndCiudadAndEliminadaFalse(String direccion, Ciudad ciudad);

	boolean existsByDireccionAndCiudadAndEliminadaFalseAndIdNot(String direccion, Ciudad ciudad, Long id);

	@Query("""
			select p from Propiedad p
			where p.eliminada = false
			and (:direccion is null or lower(p.direccion) like lower(concat('%', :direccion, '%')))
			and (:ciudadId is null or p.ciudad.id = :ciudadId)
			and (:tipo is null or p.tipo = :tipo)
			and (:estado is null or p.estadoDisponibilidad = :estado)
			""")
	List<Propiedad> filter(@Param("direccion") String direccion, @Param("ciudadId") Long ciudadId,
			@Param("tipo") TipoPropiedad tipo, @Param("estado") EstadoDisponibilidad estado);
}
