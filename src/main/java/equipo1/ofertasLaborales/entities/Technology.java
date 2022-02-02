package equipo1.ofertasLaborales.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "technology")
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "technology", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Ofert> oferts= new HashSet<>();


    private String name;


    public Technology() {}


    public Technology(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Technology(String name) {
        this.name = name;
    }

    public Technology(Long id, Set<Ofert> oferts, String name) {
        this.id = id;
        this.oferts = oferts;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Ofert> getOferts() {
        return oferts;
    }

    public void setOferts(Set<Ofert> oferts) {
        this.oferts = oferts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Technology techhnology = (Technology) obj;
        return Objects.equals(name, techhnology.name);
    }
}
