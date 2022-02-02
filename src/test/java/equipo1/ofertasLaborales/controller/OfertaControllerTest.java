package equipo1.ofertasLaborales.controller;

import equipo1.ofertasLaborales.entities.Oferta;
import equipo1.ofertasLaborales.entities.Tecnologia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfertaControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Oferta[]> response = testRestTemplate.getForEntity("/api/ofertas",Oferta[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Oferta> ofertas = Arrays.asList(response.getBody());
        System.out.println(ofertas.size());
    }

    @Test
    void findById() {
        ResponseEntity<Oferta[]> response = testRestTemplate.getForEntity("/api/ofertas", Oferta[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Oferta> ofertas = Arrays.asList(response.getBody());
        System.out.println(ofertas.size());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String oferta = """
                 {
                        "nombre" : "Desarrollador Frontend Jr.",
                         "tecnologias": [
                             {                     
                                 "nombre": "C#"
                             }
                         ],
                         "empresa": "IT",
                         "descripcion": "Comunicaciones",
                         "numeroVacantes": 4,
                         "localidad": "Barcelona",
                         "salarioMinimo": 20000,
                         "salarioMaximo": 24000,
                         "modalidad": "Remoto",
                         "anyosExperiencia": 2,
                         "titulacion": "Ingeniero",
                         "categoria": 4,
                         "tipoContrato": "Indefinido",
                         "fechaPublicacion": "2021-12-01T00:00:00.000+00:00",
                         "estadoProceso": true
                     }
                """;

        HttpEntity<String> request = new HttpEntity<>(oferta,headers);
        ResponseEntity<Tecnologia> response = testRestTemplate.exchange("/api/ofertas", HttpMethod.POST, request, Tecnologia.class);

        Tecnologia result = response.getBody();
        assert result != null;
        assertEquals(1L, result.getId());
    }


    @Test
    void update() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Oferta> response = testRestTemplate.getForEntity("/api/ofertas/4", Oferta.class);
        String oferta = """
                 {
                         "tecnologias": [
                             {
                     
                                 "nombre": "C#"
                             }
                         ],
                         "empresa": "IT",
                         "descripcion": "Comunicaciones",
                         "numeroVacantes": 4,
                         "localidad": "Barcelona",
                         "salarioMinimo": 20000,
                         "salarioMaximo": 24000,
                         "modalidad": "Remoto",
                         "anyosExperiencia": 2,
                         "titulacion": "Ingeniero",
                         "categoria": 4,
                         "tipoContrato": "Indefinido",
                         "fechaPublicacion": "2021-12-01T00:00:00.000+00:00",
                         "estadoProceso": true
                     }
                """;
        HttpEntity<String> request =new HttpEntity<String>(headers);
        ResponseEntity<Oferta> response2 =testRestTemplate.exchange("/api/ofertas",HttpMethod.PUT,request,Oferta.class);
        //assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    void delete() {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        ResponseEntity<Tecnologia> response2= testRestTemplate.exchange("/api/ofertas/1",
                HttpMethod.DELETE,httpEntity,Tecnologia.class);

        assertEquals(HttpStatus.NO_CONTENT,response2.getStatusCode());
        //assertEquals(HttpStatus.NOT_FOUND,response2.getStatusCode());
    }

    @Test
    void deleteAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity=new HttpEntity<String>(headers);

        ResponseEntity<String> respuesta= testRestTemplate.exchange("/api/ofertas",
                HttpMethod.DELETE,httpEntity,String.class);

        //assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }
}