package equipo1.ofertasLaborales.controller;

import equipo1.ofertasLaborales.entities.Ofert;
import equipo1.ofertasLaborales.entities.Technology;
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
class TecnologiaControllerTest {
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
        ResponseEntity<Technology[]> response = testRestTemplate.getForEntity("/api/tecnologias", Technology[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Technology> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findById() {
        ResponseEntity<Technology> response = testRestTemplate.getForEntity("/api/tecnologias/2", Technology.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String tecnologia = """
                 {
                        "nombre": "Html"
                    }
                """;

        HttpEntity<String> request = new HttpEntity<>(tecnologia, headers);
        ResponseEntity<Technology> response = testRestTemplate.exchange("/api/tecnologias", HttpMethod.POST, request, Technology.class);

        Technology result = response.getBody();
        assertEquals(1L, result.getId());
    }

    @Test
    void update() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Technology> response = testRestTemplate.getForEntity("/api/tecnologias/2", Technology.class);
        String oferta = """
                 {
                      "nombre": "Spring"
                    },
                """;

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Technology> response2 =testRestTemplate.exchange("/api/tecnologias",HttpMethod.PUT,request, Technology.class);
        //assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    void delete() {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //creamos el httpEntity para pasarlo al testresttempalte
        HttpEntity<String> httpEntity=new HttpEntity<String>(headers);

        ResponseEntity<Ofert> respuesta = testRestTemplate.exchange("/api/tecnologias/1",
                HttpMethod.DELETE,httpEntity, Ofert.class);

        //assertEquals(HttpStatus.NOT_FOUND,respuesta.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT,respuesta.getStatusCode());
    }

    @Test
    void deleteAll() {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        ResponseEntity<String> respuesta= testRestTemplate.exchange("/api/tecnologias",
                HttpMethod.DELETE,httpEntity,String.class);

        //assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }
    }