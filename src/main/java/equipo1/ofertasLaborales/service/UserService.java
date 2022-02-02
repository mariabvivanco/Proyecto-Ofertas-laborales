package equipo1.ofertasLaborales.service;



import equipo1.ofertasLaborales.dto.UserDto;
import equipo1.ofertasLaborales.entities.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
