package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;

@Repository
public interface IContratoRepo extends JpaRepository<Contrato, Long> {

	List<Contrato> findByEliminadoFalse();

	List<Contrato> findByEstadoAndEliminadoFalse(EstadoContrato estado);

	boolean existsByPropiedadAndEstadoAndEliminadoFalse(Propiedad propiedad, EstadoContrato estado);

	boolean existsByPropiedadAndEstadoAndEliminadoFalseAndIdNot(Propiedad propiedad, EstadoContrato estado, Long id);
}
