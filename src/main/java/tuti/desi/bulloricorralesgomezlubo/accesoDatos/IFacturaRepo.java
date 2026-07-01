package tuti.desi.bulloricorralesgomezlubo.accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.Factura;

@Repository
public interface IFacturaRepo extends JpaRepository<Factura, Long> {

	List<Factura> findByEliminadaFalse();

	List<Factura> findByContratoAndEliminadaFalse(Contrato contrato);
}
