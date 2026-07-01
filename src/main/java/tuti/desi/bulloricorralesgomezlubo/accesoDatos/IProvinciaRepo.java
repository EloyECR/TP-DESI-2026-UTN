package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Provincia;

@Repository
public interface IProvinciaRepo extends JpaRepository<Provincia, Long> {
}
