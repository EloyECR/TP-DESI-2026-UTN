package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;

@Repository
public interface ICiudadRepo extends JpaRepository<Ciudad, Long> {
}
