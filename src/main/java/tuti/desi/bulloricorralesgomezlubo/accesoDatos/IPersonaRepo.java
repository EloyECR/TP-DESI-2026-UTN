package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;

@Repository
public interface IPersonaRepo extends JpaRepository<Persona, Long> {

	List<Persona> findByEliminadaFalse();
}
