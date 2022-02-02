package equipo1.ofertasLaborales.entities;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;


@Entity
@Table(name = "oferts")
  public class Ofert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="ofert_technology",
            joinColumns={
            @JoinColumn(name="id_ofert", referencedColumnName = "id")
            },
            inverseJoinColumns= {
            @JoinColumn(name = "id_technology", referencedColumnName = "id") })

    private Set<Technology> technology = new HashSet<>();


    private String  name;
    private String business;
    private String description;
    private Integer vacancyNumbers;
    private String location;
    private Integer minSalary;
    private Integer maxSalary;
    private String modality;
    private Integer yearsExperience;
    private String title;
    private String category;
    private String contractType;
    private LocalDate publicationDate;
    private Boolean processState;
    private String imageUrl;





    // Constructores
    public Ofert() {}

    public Ofert(Long id, Set<Technology> technology, String name, String business, String description, Integer vacancyNumbers,
                 String location, Integer minSalary, Integer maxSalary, String modality, Integer yearsExperience, String title,
                 String category, String contractType, LocalDate publicationDate, Boolean processState, String imageUrl) {
        this.id = id;
        this.technology = technology;
        this.name = name;
        this.business = business;
        this.description = description;
        this.vacancyNumbers = vacancyNumbers;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.modality = modality;
        this.yearsExperience = yearsExperience;
        this.title = title;
        this.category = category;
        this.contractType = contractType;
        this.publicationDate = publicationDate;
        this.processState = processState;
        this.imageUrl = imageUrl;
    }

    public Ofert(Long id, String name, String business, String description, Integer vacancyNumbers, String location,
                 Integer minSalary, Integer maxSalary, String modality, Integer yearsExperience, String title,
                 String category, String contractType, LocalDate publicationDate, Boolean processState, String imageUrl) {
        this.id = id;
        this.name = name;
        this.business = business;
        this.description = description;
        this.vacancyNumbers = vacancyNumbers;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.modality = modality;
        this.yearsExperience = yearsExperience;
        this.title = title;
        this.category = category;
        this.contractType = contractType;
        this.publicationDate = publicationDate;
        this.processState = processState;
        this.imageUrl = imageUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Technology> getTechnology() {
        return technology;
    }

    public void setTechnology(Set<Technology> technology) {
        this.technology = technology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVacancyNumbers() {
        return vacancyNumbers;
    }

    public void setVacancyNumbers(Integer vacancyNumbers) {
        this.vacancyNumbers = vacancyNumbers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Boolean getProcessState() {
        return processState;
    }

    public void setProcessState(Boolean processState) {
        this.processState = processState;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public void addTechnology(Technology technology){

        this.technology.add(technology);
        technology.getOferts().add(this);
    }

    public void removeTechnology(Technology technology, boolean technologyExists){
        this.technology.remove(technology);
        if (technologyExists) {
            technology.getOferts().remove(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ofert)) return false;
        return id != null && id.equals(((Ofert) obj).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}