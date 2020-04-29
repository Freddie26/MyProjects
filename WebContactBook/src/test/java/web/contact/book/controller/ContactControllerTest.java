package web.contact.book.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import web.contact.book.model.Contact;
import web.contact.book.model.Person;
//import web.contact.book.model.Contact;
//import web.contact.book.model.ContactType;
//import web.contact.book.model.Person;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateContact() throws Exception {
        Person person = new Person("Анацкий", "Игорь", "", "программист");
        Contact contact = new Contact(person, null, "test@mail.ru");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/contacts",
                contact, String.class);

        assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void testFoundContactByNumber() throws Exception {
        ResponseEntity<Contact[]> response = restTemplate.getForEntity("http://localhost:" + port
                + "/contacts/by_number/test@mail.ru", Contact[].class);

        Contact[] contacts = response.getBody();

        assertThat(response.getStatusCode().equals(HttpStatus.OK));
        assertThat(Objects.requireNonNull(contacts));
    }

    @Test
    public void testNotFoundContactByNumber() throws Exception {
        ResponseEntity<Contact[]> response = restTemplate.getForEntity("http://localhost:" + port
                + "/contacts/by_number/test@gmail.com", Contact[].class);

        Contact[] contacts = response.getBody();

        assertThat(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
        assertThat(Objects.isNull(contacts));
    }
}
