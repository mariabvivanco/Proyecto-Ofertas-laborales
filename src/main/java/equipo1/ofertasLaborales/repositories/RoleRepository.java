package equipo1.ofertasLaborales.repositories;


import equipo1.ofertasLaborales.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}