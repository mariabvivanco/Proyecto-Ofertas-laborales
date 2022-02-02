package equipo1.ofertasLaborales;


import equipo1.ofertasLaborales.entities.Ofert;
import equipo1.ofertasLaborales.entities.Role;
import equipo1.ofertasLaborales.entities.Technology;
import equipo1.ofertasLaborales.entities.User;
import equipo1.ofertasLaborales.repositories.OfertRepository;
import equipo1.ofertasLaborales.repositories.RoleRepository;
import equipo1.ofertasLaborales.repositories.TechnologyRepository;
import equipo1.ofertasLaborales.repositories.UserRepository;
import equipo1.ofertasLaborales.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class OfertasLaboralesApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OfertasLaboralesApplication.class,
				args);
		OfertRepository ofertRepository = context.getBean(OfertRepository.class);
		TechnologyRepository technologyRepository = context.getBean(TechnologyRepository.class);
		UserRepository userRepository = context.getBean(UserRepository.class);
		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		Technology tecJava = new Technology(null, "Java");
		Technology tecSpring = new Technology(null, "Spring");
		Technology tecJS = new Technology(null, "Javascript");
		Technology tecAngular = new Technology(null, "Angular");
		Technology tecCSS = new Technology(null, "CSS");
		Technology tecPython = new Technology(null, "Python");
		Technology tecReact = new Technology(null, "React");
		Technology tecDjango = new Technology(null, "Django");
		Technology tecAndroid = new Technology(null, "Android");
		Technology tecKotlin = new Technology(null, "Kotlin");
		Technology tecIOS = new Technology(null, "IOS");
		Technology tecSwift = new Technology(null, "Swift");



		technologyRepository.saveAll(Arrays.asList(tecJava, tecSpring, tecJS, tecAngular, tecCSS, tecPython, tecReact, tecDjango));

		LocalDate date1 = LocalDate.of(2020, Calendar.DECEMBER, 23);
		LocalDate date2 = LocalDate.of(2021, Calendar.FEBRUARY, 7);
		LocalDate date3 = LocalDate.of(2021, Calendar.MARCH, 28);
		LocalDate date4 = LocalDate.of(2020, Calendar.JULY, 1);
		LocalDate date5 = LocalDate.of(2021, Calendar.OCTOBER, 19);
		LocalDate date6 = LocalDate.of(2021, Calendar.AUGUST, 22);


		Ofert ofert1 = new Ofert(null,"Desarrollador Backend Jr.", "Orange","Comunicaciones",
				4,"Barcelona",12000,18000,
				"Presencial",2,
				"Ingeniero","Frontend","Indefinido" ,date1,true,"") ;

		Ofert ofert2 = new Ofert(null, "Desarrollador Frontend Jr.","Indra","Consultoría",
				2,"Madrid",11000,16000,
				"Remoto",1,
				"CFGS","Frontend","Practicas" ,date2,false,"");

		Ofert ofert3 = new Ofert(null, "Desarrollador Fullstack Sr.","Facebook", "Tecnología",
				2, "Zaragoza", 50000, 100000,
				"Remoto", 5,
				"Ingeniero", "Fullstack", "Practicas", date3, true, "");

		Ofert ofert4 = new Ofert(null,"Desarrollador Mobile Android Jr.", "Everis","Consultoría",
				4,"Barcelona",12000,18000,
				"Presencial",2,
				"Ingeniero","Frontend","Indefinido" ,date1,true,"") ;

		Ofert ofert5 = new Ofert(null, "Desarrollador Frontend Sr.","Capgemini","Consultoría",
				2,"Madrid",25000,35000,
				"Remoto",4,
				"CFGS","Frontend","Practicas" ,date2,false,"");

		Ofert ofert6 = new Ofert(null, "Desarrollador Mobile IOS Sr.","Google", "Tecnología",
				2, "Bay Area, SF.", 50000, 100000,
				"Remoto", 8,
				"Ingeniero", "Mobile", "Indefinido", date3, true, "");

		ofertRepository.saveAll(Arrays.asList(ofert1, ofert2, ofert3, ofert4, ofert5, ofert6));


		List<Technology> technologies1 = Arrays.asList(tecJava, tecSpring);
		List<Technology> technologies2 = Arrays.asList(tecJS, tecAngular, tecCSS);
		List<Technology> technologies3 = Arrays.asList(tecJS, tecCSS, tecReact, tecPython, tecDjango);
		List<Technology> technologies4 = Arrays.asList(tecAndroid, tecKotlin);
		List<Technology> technologies5 = Arrays.asList(tecJS, tecAngular, tecReact, tecCSS);
		List<Technology> technologies6 = Arrays.asList(tecIOS, tecSwift);

		for (Technology technology : technologies1) {
			ofert1.addTechnology(technology);
		}
		for (Technology technology : technologies2) {
			ofert2.addTechnology(technology);
		}
		for (Technology technology : technologies3) {
			ofert3.addTechnology(technology);

		}
		for (Technology technology : technologies4) {
			ofert4.addTechnology(technology);

		}
		for (Technology technology : technologies5) {
			ofert5.addTechnology(technology);
		}
		for (Technology technology : technologies6) {
			ofert6.addTechnology(technology);
		}


		ofertRepository.saveAll(Arrays.asList(ofert1, ofert2, ofert3, ofert4, ofert5, ofert6));



		List<Ofert> oferts = ofertRepository.findAll();

		for (Ofert ofert : oferts) {
			Set<Technology> technologies = ofert.getTechnology();
			StringBuilder idTechnologies = new StringBuilder();
			for(Technology technology : technologies) {
				idTechnologies.append(technology.getId()).append(" ");
			}

		}



		BCryptPasswordEncoder bcryptEncoder = null;
		Role role1 = new Role(1,"ADMIN", "Rol Admin");
		Role role2 = new Role(2,"USER", "Rol User");
		roleRepository.save(role1);
		roleRepository.save(role2);
		User user = new User(1,"admin","$2a$10$DTAejq8zVwf.dMadV1SAvuNXAbXjroY.G7dWpS1tzoGolwn7nexTm","","","","");
		Set<Role> useradmin =new HashSet<>();
		useradmin.add(role1);
		useradmin.add(role2);
		user.setRoles(useradmin);
		UserServiceImpl encriptarUser = new UserServiceImpl();
		userRepository.save(user);



	}
}
