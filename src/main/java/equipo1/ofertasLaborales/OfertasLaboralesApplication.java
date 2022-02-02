package equipo1.ofertasLaborales;


import equipo1.ofertasLaborales.entities.Oferta;
import equipo1.ofertasLaborales.entities.Role;
import equipo1.ofertasLaborales.entities.Tecnologia;
import equipo1.ofertasLaborales.entities.User;
import equipo1.ofertasLaborales.repositories.OfertaRepository;
import equipo1.ofertasLaborales.repositories.RoleRepository;
import equipo1.ofertasLaborales.repositories.TecnologiaRepository;
import equipo1.ofertasLaborales.repositories.UserRepository;
import equipo1.ofertasLaborales.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
		OfertaRepository ofertaRepository = context.getBean(OfertaRepository.class);
		TecnologiaRepository tecnologiaRepository = context.getBean(TecnologiaRepository.class);
		UserRepository userRepository = context.getBean(UserRepository.class);
		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		Tecnologia tecJava = new Tecnologia(null, "Java");
		Tecnologia tecSpring = new Tecnologia(null, "Spring");
		Tecnologia tecJS = new Tecnologia(null, "Javascript");
		Tecnologia tecAngular = new Tecnologia(null, "Angular");
		Tecnologia tecCSS = new Tecnologia(null, "CSS");
		Tecnologia tecPython = new Tecnologia(null, "Python");
		Tecnologia tecReact = new Tecnologia(null, "React");
		Tecnologia tecDjango = new Tecnologia(null, "Django");
		Tecnologia tecAndroid = new Tecnologia(null, "Android");
		Tecnologia tecKotlin = new Tecnologia(null, "Kotlin");
		Tecnologia tecIOS = new Tecnologia(null, "IOS");
		Tecnologia tecSwift = new Tecnologia(null, "Swift");

		System.out.println("Número de ofertas en base de datos: " + ofertaRepository.findAll().size());
		System.out.println("Número de tecnologías en base de datos: " + tecnologiaRepository.findAll().size());

		tecnologiaRepository.saveAll(Arrays.asList(tecJava, tecSpring, tecJS, tecAngular, tecCSS, tecPython, tecReact, tecDjango));

		LocalDate fecha1 = LocalDate.of(2020, Calendar.DECEMBER, 23);
		LocalDate fecha2 = LocalDate.of(2021, Calendar.FEBRUARY, 7);
		LocalDate fecha3 = LocalDate.of(2021, Calendar.MARCH, 28);
		LocalDate fecha4 = LocalDate.of(2020, Calendar.JULY, 1);
		LocalDate fecha5 = LocalDate.of(2021, Calendar.OCTOBER, 19);
		LocalDate fecha6 = LocalDate.of(2021, Calendar.AUGUST, 22);


		Oferta oferta1 = new Oferta(null,"Desarrollador Backend Jr.", "Orange","Comunicaciones",
				4,"Barcelona",12000,18000,
				"Presencial",2,
				"Ingeniero","Frontend","Indefinido" ,fecha1,true,"") ;

		Oferta oferta2 = new Oferta(null, "Desarrollador Frontend Jr.","Indra","Consultoría",
				2,"Madrid",11000,16000,
				"Remoto",1,
				"CFGS","Frontend","Practicas" ,fecha2,false,"");

		Oferta oferta3 = new Oferta(null, "Desarrollador Fullstack Sr.","Facebook", "Tecnología",
				2, "Zaragoza", 50000, 100000,
				"Remoto", 5,
				"Ingeniero", "Fullstack", "Practicas", fecha3, true, "");

		Oferta oferta4 = new Oferta(null,"Desarrollador Mobile Android Jr.", "Everis","Consultoría",
				4,"Barcelona",12000,18000,
				"Presencial",2,
				"Ingeniero","Frontend","Indefinido" ,fecha1,true,"") ;

		Oferta oferta5 = new Oferta(null, "Desarrollador Frontend Sr.","Capgemini","Consultoría",
				2,"Madrid",25000,35000,
				"Remoto",4,
				"CFGS","Frontend","Practicas" ,fecha2,false,"");

		Oferta oferta6 = new Oferta(null, "Desarrollador Mobile IOS Sr.","Google", "Tecnología",
				2, "Bay Area, SF.", 50000, 100000,
				"Remoto", 8,
				"Ingeniero", "Mobile", "Indefinido", fecha3, true, "");

		ofertaRepository.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4, oferta5, oferta6));


		List<Tecnologia> tecnologias1 = Arrays.asList(tecJava, tecSpring);
		List<Tecnologia> tecnologias2 = Arrays.asList(tecJS, tecAngular, tecCSS);
		List<Tecnologia> tecnologias3 = Arrays.asList(tecJS, tecCSS, tecReact, tecPython, tecDjango);
		List<Tecnologia> tecnologias4 = Arrays.asList(tecAndroid, tecKotlin);
		List<Tecnologia> tecnologias5 = Arrays.asList(tecJS, tecAngular, tecReact, tecCSS);
		List<Tecnologia> tecnologias6 = Arrays.asList(tecIOS, tecSwift);

		for (Tecnologia tecnologia : tecnologias1) {
			oferta1.addTecnologia(tecnologia);
		}
		for (Tecnologia tecnologia : tecnologias2) {
			oferta2.addTecnologia(tecnologia);
		}
		for (Tecnologia tecnologia : tecnologias3) {
			oferta3.addTecnologia(tecnologia);
		}
		for (Tecnologia tecnologia : tecnologias4) {
			oferta4.addTecnologia(tecnologia);
		}
		for (Tecnologia tecnologia : tecnologias5) {
			oferta5.addTecnologia(tecnologia);
		}
		for (Tecnologia tecnologia : tecnologias6) {
			oferta6.addTecnologia(tecnologia);
		}

		ofertaRepository.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4, oferta5, oferta6));

		System.out.println("Número de ofertas en base de datos: " + ofertaRepository.findAll().size());
		System.out.println("Número de tecnologías en base de datos: " + tecnologiaRepository.findAll().size());

		List<Oferta> ofertas = ofertaRepository.findAll();

		for (Oferta oferta : ofertas) {
			Set<Tecnologia> tecnologias = oferta.getTecnologias();
			StringBuilder idTecnologias = new StringBuilder();
			for(Tecnologia tecnologia : tecnologias) {
				idTecnologias.append(tecnologia.getId()).append(" ");
			}
			System.out.println(" Id Oferta: " + oferta.getId() + " - Id Tecnologia: " + idTecnologias);
		}

		//Inicializar usuarios y roles

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
