package web.contact.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.contact.book.model.Contact;

import java.util.List;

/**
 * Spring в run-time создаёт класс, который реализует основные методы работу с БД
 */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    // дописываем автогенерируемые методы поиска списков контактов по части фамилии и номеру контакта
    List<Contact> findAllByPersonFirstNameContainingIgnoreCase(String firstName);
    List<Contact> findAllByNumber(String number);
}
