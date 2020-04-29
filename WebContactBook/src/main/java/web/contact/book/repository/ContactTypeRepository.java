package web.contact.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.contact.book.model.ContactType;

/**
 * Spring в run-time создаёт класс, который реализует основные методы работу с БД
 */
@Repository
public interface ContactTypeRepository extends CrudRepository<ContactType, Long> {
}
