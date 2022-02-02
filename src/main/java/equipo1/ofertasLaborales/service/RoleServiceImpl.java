package equipo1.ofertasLaborales.service;


import equipo1.ofertasLaborales.entities.Role;
import equipo1.ofertasLaborales.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
