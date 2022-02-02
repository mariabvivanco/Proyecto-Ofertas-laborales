package equipo1.ofertasLaborales.repositories;

import equipo1.ofertasLaborales.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long>, CrudRepository<Technology, Long> {
    boolean existsByName(String name);
}
