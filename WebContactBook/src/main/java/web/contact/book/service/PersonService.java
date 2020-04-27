package web.contact.book.service;

import web.contact.book.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findById(Long id);

    void create(Person person);

    boolean update(Person person, Long id);

    boolean delete(Long id);
}
