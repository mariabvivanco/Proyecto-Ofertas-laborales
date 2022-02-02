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

import java.util.*;

@RestController
public class OfertaController {

    private final Logger log = LoggerFactory.getLogger(OfertaController.class);

    private OfertaRepository ofertaRepository;
    private TecnologiaRepository tecnologiaRepository;

    public OfertaController(OfertaRepository ofertaRepository, TecnologiaRepository tecnologiaRepository) {
        this.ofertaRepository = ofertaRepository;
        this.tecnologiaRepository = tecnologiaRepository;
    }

    /**
     * Buscar todas las ofertas en BBDD
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/api/ofertas")
    public List<Oferta> findAll() {
        return ofertaRepository.findAll();
    }

    /**
     * Buscar ofertas según id
     * Request
     * Response
     */
    @CrossOrigin
    @GetMapping("/api/ofertas/{id}")
    public ResponseEntity<Oferta> findById(@PathVariable Long id) {
        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            return ResponseEntity.ok(ofertaOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crear oferta nueva en la bbdd.
     *
     * @param oferta
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/api/ofertas")
    public ResponseEntity<Oferta> create(@RequestBody Oferta oferta) {
        if(oferta.getId() != null) {
            log.warn("Intentando crear una oferta con id");
            return ResponseEntity.badRequest().build();
        }

        Set<Tecnologia> tecnologias = oferta.getTecnologias();

        for (Tecnologia tecnologia : tecnologias) {
            if(tecnologia.getId() == null) {
                log.info("Creando tecnología inexistente: " + tecnologia.getNombre());
                tecnologiaRepository.save(tecnologia);
            }
        }

        Oferta ofertaAGuardar = new Oferta(
                null,
                oferta.getNombre(),
                oferta.getEmpresa(),
                oferta.getDescripcion(),
                oferta.getNumeroVacantes(),
                oferta.getLocalidad(),
                oferta.getSalarioMinimo(),
                oferta.getSalarioMaximo(),
                oferta.getModalidad(),
                oferta.getAnyosExperiencia(),
                oferta.getTitulacion(),
                oferta.getCategoria(),
                oferta.getTipoContrato(),
                oferta.getFechaPublicacion(),
                oferta.getEstadoProceso(),
                oferta.getUrlImagen()
        );

        for (Tecnologia tecnologia : oferta.getTecnologias()) {
            ofertaAGuardar.addTecnologia(tecnologia);
        }

        Oferta result = ofertaRepository.save(ofertaAGuardar);
        return ResponseEntity.ok(result);
    }

    /**
     * Actualizar una oferta en la bbdd.
     *
     * @param oferta
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/api/ofertas")
    public ResponseEntity<Oferta> update(@RequestBody Oferta oferta) {
        if (oferta.getId() == null) {
            log.warn("Intentando actualizar una oferta inexistente");
            return ResponseEntity.badRequest().build();
        }
        if (!ofertaRepository.existsById(oferta.getId())) {
            log.warn("Intentando actualizar una oferta inexistente");
            return ResponseEntity.notFound().build();
        }

        Set<Tecnologia> tecnologias = oferta.getTecnologias();

        for (Tecnologia tecnologia : tecnologias) {
            if(tecnologia.getId() == null) {
                log.info("Creando tecnología inexistente: " + tecnologia.getNombre());
                tecnologiaRepository.save(tecnologia);
            }
        }

        Optional<Oferta> ofertaOpt = ofertaRepository.findById(oferta.getId());
        if (ofertaOpt.isPresent()) {
            Oferta ofertaAntigua = ofertaOpt.get();
            desvincularTecnologias(ofertaAntigua);
        }

        Oferta ofertaAGuardar = new Oferta(
                oferta.getId(),
                oferta.getNombre(),
                oferta.getEmpresa(),
                oferta.getDescripcion(),
                oferta.getNumeroVacantes(),
                oferta.getLocalidad(),
                oferta.getSalarioMinimo(),
                oferta.getSalarioMaximo(),
                oferta.getModalidad(),
                oferta.getAnyosExperiencia(),
                oferta.getTitulacion(),
                oferta.getCategoria(),
                oferta.getTipoContrato(),
                oferta.getFechaPublicacion(),
                oferta.getEstadoProceso(),
                oferta.getUrlImagen()
        );

        for (Tecnologia tecnologia : oferta.getTecnologias()) {
            ofertaAGuardar.addTecnologia(tecnologia);
        }

        Oferta result = ofertaRepository.save(ofertaAGuardar);
        log.info("Actualizando oferta: " + ofertaAGuardar.getId());
        return ResponseEntity.ok(result);
    }

    /**
     * Eliminar una oferta de la bbdd.
     * @param id
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/ofertas/{id}")
    public ResponseEntity<Oferta> delete(@PathVariable Long id) {

        if (!ofertaRepository.existsById(id)) {
            log.warn("Intentando eliminar una oferta inexistente");
            return ResponseEntity.notFound().build();
        }

        Optional<Oferta> ofertaOpt = ofertaRepository.findById(id);
        if (ofertaOpt.isPresent()) {
            Oferta ofertaAntigua = ofertaOpt.get();
            desvincularTecnologias(ofertaAntigua);
        }

        ofertaRepository.deleteById(id);
        log.info("Eliminando oferta: " + id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar todas las ofertas de la bbdd.
     *
     * @return
     */
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/ofertas")
    public ResponseEntity<Oferta> deleteAll() {

        List<Oferta> ofertas = ofertaRepository.findAll();
        for (Oferta oferta : ofertas) {
            desvincularTecnologias(oferta);
        }

        List<Tecnologia> tecnologias = tecnologiaRepository.findAll();

        ofertaRepository.deleteAll();
        log.info("Eliminando todas las ofertas");
        return ResponseEntity.noContent().build();
    }

    /**
     * Método que desvincula cada oferta de las tecnologias que están ligadas a ellas.
     *
     * @param oferta
     */
    private void desvincularTecnologias(Oferta oferta) {
        Set<Tecnologia> tecnologiasABorrar = new HashSet<>(oferta.getTecnologias());
        for (Tecnologia tecnologia : tecnologiasABorrar) {
            oferta.removeTecnologia(tecnologia, true);
        }
    }
}
