package equipo1.ofertasLaborales.controller;

import equipo1.ofertasLaborales.entities.Ofert;
import equipo1.ofertasLaborales.entities.Technology;
import equipo1.ofertasLaborales.repositories.OfertRepository;
import equipo1.ofertasLaborales.repositories.TechnologyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TechnologyController {

    private final Logger log = LoggerFactory.getLogger(TechnologyController.class);

    private TechnologyRepository technologyRepository;
    private OfertRepository ofertRepository;

    public TechnologyController(TechnologyRepository technologyRepository, OfertRepository ofertRepository) {
        this.technologyRepository = technologyRepository;
        this.ofertRepository = ofertRepository;
    }

    /**
     * Search all technologies in the database
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/tecnologias")
    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

    /**
     * Search technologies by id
     * Request
     * Response
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/tecnologias/{id}")
    public ResponseEntity<Technology> findById(@PathVariable Long id) {
        Optional<Technology> technologyOpt = technologyRepository.findById(id);
        if (technologyOpt.isPresent()) {
            return ResponseEntity.ok(technologyOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create new technology in the database.
     *
     * @param technology
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/api/tecnologias")
    public ResponseEntity<Technology> create(@RequestBody Technology technology) {
        if (technology.getId() != null) {
            log.warn("Intentando crear una tecnología con id");
            return ResponseEntity.badRequest().build();}

        if ((technologyRepository.getById(technology.getId())!=null)||(technologyRepository.existsByName(technology.getName()))){
            log.warn("Intentando crear una tecnología existente");
            return ResponseEntity.badRequest().build();}

        technologyRepository.save(technology);
        return ResponseEntity.ok(technology);
    }

    /**
     *Update a technology in the database
     * @param technology
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/api/tecnologias")
    public ResponseEntity<Technology> update(@RequestBody Technology technology) {
        if ((technology.getId() == null)||(!technologyRepository.existsById(technology.getId()))) {
            log.warn("Intentando actualizar una tecnología inexistente");
            return ResponseEntity.badRequest().build();
        }

        Technology result = technologyRepository.save(technology);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete a technology in the database
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/tecnologias/{id}")
    public ResponseEntity<Technology> delete(@PathVariable Long id) {

        Optional<Technology> technologyOpt = technologyRepository.findById(id);
        if (technologyOpt.isPresent()) {
            Set<Ofert> oferts = technologyOpt.get().getOferts();
            for (Ofert ofert : oferts) {
                ofert.removeTechnology(technologyOpt.get(), false);
                ofertRepository.save(ofert);
            }
        }else {
            log.warn("Intentando eliminar una tecnologia inexistente");
            return ResponseEntity.notFound().build();
        }

        technologyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all the technologies in the database
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/tecnologias")
    public ResponseEntity<Technology> deleteAll() {
        log.info("Petición REST para eliminar todas las tecnologias");
        for (Ofert ofert: ofertRepository.findAll()) {
            ofert.setTechnology(null);
            ofertRepository.save(ofert);
        }
        technologyRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
