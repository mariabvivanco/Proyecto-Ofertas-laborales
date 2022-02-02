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
public class OfertController {

    private final Logger log = LoggerFactory.getLogger(OfertController.class);

    private OfertRepository ofertRepository;
    private TechnologyRepository technologyRepository;

    public OfertController(OfertRepository ofertRepository, TechnologyRepository technologyRepository) {
        this.ofertRepository = ofertRepository;
        this.technologyRepository = technologyRepository;
    }

    /**
     * Search all offers in database
     */

    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/ofertas")
    public ResponseEntity<List<Ofert>> findAll() {
         return ResponseEntity.ok(ofertRepository.findAll());
    }

    /**
     * Search offers by id
     * Request
     * Response
     */
    @CrossOrigin
    @GetMapping("/api/ofertas/{id}")
    public ResponseEntity<Ofert> findById(@PathVariable Long id) {
        Optional<Ofert> ofertOpt = ofertRepository.findById(id);
        if (ofertOpt.isPresent()) {
            return ResponseEntity.ok(ofertOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create new offer in the database.

     * @param ofert
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/api/ofertas")
    public ResponseEntity<Ofert> create(@RequestBody Ofert ofert) {
        if(ofert.getId() != null) {
            log.warn("Intentando crear una oferta con id");
            return ResponseEntity.badRequest().build();
        }

        for (Technology technology : ofert.getTechnology()) {
            if(!technologyRepository.existsByName(technology.getName())){
                log.info("Creando tecnología inexistente: " + technology.getName());
                technologyRepository.save(technology);
            }
        }


        ofertRepository.save(ofert);
        return ResponseEntity.ok(ofert);
    }

    /**
     * Update an offer in the database.
     * @param ofert
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/api/ofertas")
    public ResponseEntity<Ofert> update(@RequestBody Ofert ofert) {
        if ((ofert.getId() == null)||(!ofertRepository.existsById(ofert.getId())))  {
            log.warn("Intentando actualizar una oferta inexistente");
            return ResponseEntity.badRequest().build();
        }

        for (Technology technology : ofert.getTechnology()) {
            if(!technologyRepository.existsByName(technology.getName())){
                log.info("Creando tecnología inexistente: " + technology.getName());
                technologyRepository.save(technology);
            }
        }

        ofertRepository.save(ofert);
        return ResponseEntity.ok(ofert);
    }

    /**
     * delete an offer from the database
     * @param id
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/ofertas/{id}")
    public ResponseEntity<Ofert> delete(@PathVariable Long id) {

        Optional<Ofert> ofertOpt = ofertRepository.findById(id);
        if (ofertOpt.isPresent()) {
            Ofert ofertOld = ofertOpt.get();
            ofertOld.setTechnology(null);
            ofertRepository.save(ofertOld);
            ofertRepository.delete(ofertOld);
            return ResponseEntity.noContent().build();
        }else{
            log.warn("Intentando eliminar una oferta inexistente");
            return ResponseEntity.notFound().build();
        }


    }

    /**
     * Remove all offers from the database
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/ofertas")
    public ResponseEntity<Ofert> deleteAll() {

        log.info("Petición REST para eliminar todas las ofertas");
        for (Technology technology: technologyRepository.findAll()) {
            technology.setOferts(null);
            technologyRepository.save(technology);
        }
        ofertRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
