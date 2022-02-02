package equipo1.ofertasLaborales.service;



import equipo1.ofertasLaborales.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
