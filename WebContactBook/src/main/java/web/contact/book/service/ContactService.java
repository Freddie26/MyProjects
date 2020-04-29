package web.contact.book.service;

import org.springframework.stereotype.Service;
import web.contact.book.model.Contact;
import web.contact.book.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        // выбираем из БД все контакты
        contactRepository.findAll()
                // последовательно добавляем контакты в список
                .forEach(contacts::add);
        // возвращаем список контактов
        return contacts;
    }

    public List<Contact> findByPartName(String partName) {
        // Spring автоматически строит запрос по имени метода
        // здесь мы ищем список всех контактов по частичному совпадению фамилии без учёта регистра
        return contactRepository.findAllByPersonFirstNameContainingIgnoreCase(partName);
    }

    public List<Contact> findByNumber(String number) {
        // ищем список контактов по номеру контакта
        return contactRepository.findAllByNumber(number);
    }

    public void create(Contact contact) {
        // сохранение записи контакта в БД
        contactRepository.save(contact);
    }

    public boolean update(Contact contact, Long id) {
        // если нашли контакт по идентификатору - обновляем
        if (contactRepository.existsById(id)) {
            contact.setId(id);
            contactRepository.save(contact);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        // если нашли контакт по идентификатору - удаляем
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
