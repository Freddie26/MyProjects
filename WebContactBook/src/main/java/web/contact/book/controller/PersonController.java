package web.contact.book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.contact.book.model.Person;
import web.contact.book.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
class PersonController {

    private final PersonService personService;

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        List<Person> persons = personService.findAll();

        return (persons == null || persons.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = personService.findById(id);

        return (person == null)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Person person) {
        personService.create(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@RequestBody Person person, @PathVariable Long id) {
        return (personService.update(person, id))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return (personService.delete(id))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
