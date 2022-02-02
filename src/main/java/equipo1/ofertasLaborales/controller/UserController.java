package equipo1.ofertasLaborales.controller;


import equipo1.ofertasLaborales.config.TokenProvider;
import equipo1.ofertasLaborales.dto.AuthToken;
import equipo1.ofertasLaborales.dto.LoginUser;
import equipo1.ofertasLaborales.dto.UserDto;
import equipo1.ofertasLaborales.entities.User;
import equipo1.ofertasLaborales.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenUtil;
    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }


    @CrossOrigin
    @PostMapping("/register")
    public User saveUser(@RequestBody UserDto user){

        return userService.save(user);
    }



}
