package web.contact.book.repository;

import org.springframework.data.repository.CrudRepository;
import web.contact.book.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
