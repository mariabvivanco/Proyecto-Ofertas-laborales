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
        ResponseEntity<Tecnologia[]> response = testRestTemplate.getForEntity("/api/tecnologias", Tecnologia[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Tecnologia> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findById() {
        ResponseEntity<Tecnologia> response = testRestTemplate.getForEntity("/api/tecnologias/2", Tecnologia.class);
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
        ResponseEntity<Tecnologia> response = testRestTemplate.exchange("/api/tecnologias", HttpMethod.POST, request, Tecnologia.class);

        Tecnologia result = response.getBody();
        assertEquals(1L, result.getId());
    }

    @Test
    void update() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Tecnologia> response = testRestTemplate.getForEntity("/api/tecnologias/2", Tecnologia.class);
        String oferta = """
                 {
                      "nombre": "Spring"
                    },
                """;

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Tecnologia> response2 =testRestTemplate.exchange("/api/tecnologias",HttpMethod.PUT,request,Tecnologia.class);
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

        ResponseEntity<Oferta> respuesta = testRestTemplate.exchange("/api/tecnologias/1",
                HttpMethod.DELETE,httpEntity, Oferta.class);

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