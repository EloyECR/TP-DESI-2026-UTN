package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;

@Repository
public interface IContratoRepo extends JpaRepository<Contrato, Long> {
	@Query("SELECT c FROM Contrato c WHERE c.eliminado = false " +
			"AND (:propiedad IS NULL OR c.propiedad.id = :propiedad) " +
			"AND (:inquilino IS NULL OR c.inquilino.id = :inquilino) " +
			"AND (:estadoContrato IS NULL OR c.estado = :estadoContrato)" +
			"AND (:fechaInicio IS NULL OR c.fechaInicio >= :fechaInicio)" +
			"AND (:fechaFin IS NULL OR c.fechaInicio <= :fechaFin)")
	List<Contrato> findByFilters(
			@Param("propiedad") Long propiedad,
			@Param("inquilino") Long inquilino,
			@Param("estadoContrato") EstadoContrato estadoContrato,
			@Param("fechaInicio") LocalDate fechaInicio,
			@Param("fechaFin") LocalDate fechaFin
	);
	Contrato findByEliminadoFalseAndIdNotAndPropiedadAndEstado(Long id, Propiedad propiedad, EstadoContrato estado);
	Boolean existsByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad, EstadoContrato estado);
}