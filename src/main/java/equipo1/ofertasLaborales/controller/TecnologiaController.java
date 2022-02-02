package equipo1.ofertasLaborales.controller;

import equipo1.ofertasLaborales.entities.Oferta;
import equipo1.ofertasLaborales.entities.Tecnologia;
import equipo1.ofertasLaborales.repositories.OfertaRepository;
import equipo1.ofertasLaborales.repositories.TecnologiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
public class TecnologiaController {

    private final Logger log = LoggerFactory.getLogger(TecnologiaController.class);

    private TecnologiaRepository tecnologiaRepository;
    private OfertaRepository ofertaRepository;

    public TecnologiaController(TecnologiaRepository tecnologiaRepository, OfertaRepository ofertaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.ofertaRepository = ofertaRepository;
    }
    /**
     * Buscar todas las tecnologias en BBDD
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/tecnologias")
    public List<Tecnologia> findAll() {
        return tecnologiaRepository.findAll();
    }

    /**
     * Buscar tecnologias según id
     * Request
     * Response
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/tecnologias/{id}")
    public ResponseEntity<Tecnologia> findById(@PathVariable Long id) {
        Optional<Tecnologia> tecnologiaOpt = tecnologiaRepository.findById(id);
        if (tecnologiaOpt.isPresent()) {
            return ResponseEntity.ok(tecnologiaOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear tecnologia nueva en la bbdd.
     *
     * @param tecnologia
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/api/tecnologias")
    public ResponseEntity<Tecnologia> create(@RequestBody Tecnologia tecnologia) {
        if (tecnologia.getId() != null) {
            log.warn("Intentando crear una tecnologia con id");
            return ResponseEntity.badRequest().build();
        }
        List<Tecnologia> tecnologias = tecnologiaRepository.findAll();
        for (Tecnologia tecnologiaEnRepo : tecnologias) {
            if (tecnologiaEnRepo.equals(tecnologia)) {
                log.warn("Intentando crear una tecnologia ya existente");
                return ResponseEntity.badRequest().build();
            }
        }


        Tecnologia result = tecnologiaRepository.save(tecnologia);
        return ResponseEntity.ok(result);
    }

    /**
     * Actualizar una tecnologia en la bbdd.
     *
     * @param tecnologia
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/api/tecnologias")
    public ResponseEntity<Tecnologia> update(@RequestBody Tecnologia tecnologia) {
        if (tecnologia.getId() == null) {
            log.warn("Intentando actualizar una tecnología inexistente");
            return ResponseEntity.badRequest().build();
        }
        if (!tecnologiaRepository.existsById(tecnologia.getId())) {
            log.warn("Intentando actualizar una tecnología inexistente");
            return ResponseEntity.notFound().build();
        }

        Tecnologia result = tecnologiaRepository.save(tecnologia);
        return ResponseEntity.ok(result);
    }

    /**
     * Eliminar una tecnologia de la bbdd.
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/tecnologias/{id}")
    public ResponseEntity<Tecnologia> delete(@PathVariable Long id) {

        if (!tecnologiaRepository.existsById(id)) {
            log.warn("Intentando eliminar una tecnologia inexistente");
            return ResponseEntity.notFound().build();
        }

        Optional<Tecnologia> tecnologiaOpt = tecnologiaRepository.findById(id);
        if (tecnologiaOpt.isPresent()) {
            Tecnologia tecnologia = tecnologiaOpt.get();
            System.out.println(tecnologia );
            System.out.println(tecnologia.getOfertas());
            Set<Oferta> ofertas = tecnologia.getOfertas();
            for (Oferta oferta : ofertas) {
                oferta.removeTecnologia(tecnologia, false);
                ofertaRepository.save(oferta);
            }
        }

        tecnologiaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar todas las tecnologias de la bbdd.
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/tecnologias")
    public ResponseEntity<Tecnologia> deleteAll() {
        log.info("Petición REST para eliminar todas las tecnologias");
        List<Tecnologia> tecnologias = tecnologiaRepository.findAll();

        for (Tecnologia tecnologia : tecnologias) {
            Set<Oferta> ofertas = tecnologia.getOfertas();
            for (Oferta oferta : ofertas) {
                oferta.removeTecnologia(tecnologia, false);
                ofertaRepository.save(oferta);
            }
        }

        tecnologiaRepository.deleteAll();

        return ResponseEntity.noContent().build();
    }
}
