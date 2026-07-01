package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.desi.bulloricorralesgomezlubo.entidades.HistorialEstadoContrato;

public interface IHistorialEstadoContratoRepo extends JpaRepository<HistorialEstadoContrato, Long> {
}
