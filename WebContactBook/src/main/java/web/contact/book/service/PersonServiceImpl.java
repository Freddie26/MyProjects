package web.contact.book.service;

import org.springframework.stereotype.Service;
import web.contact.book.model.Person;
import web.contact.book.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll()
                .forEach(persons::add);
        return persons;
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void create(Person person) {
        personRepository.save(person);
    }

    @Override
    public boolean update(Person person, Long id) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            personRepository.save(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
