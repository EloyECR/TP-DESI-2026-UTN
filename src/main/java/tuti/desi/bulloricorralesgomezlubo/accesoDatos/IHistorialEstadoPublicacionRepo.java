package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.HistorialEstadoPublicacion;

@Repository
public interface IHistorialEstadoPublicacionRepo extends JpaRepository<HistorialEstadoPublicacion, Long> {
}
