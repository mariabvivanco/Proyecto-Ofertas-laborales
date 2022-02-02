package equipo1.ofertasLaborales.repositories;

import equipo1.ofertasLaborales.entities.Ofert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OfertRepository extends JpaRepository<Ofert, Long> {
}
