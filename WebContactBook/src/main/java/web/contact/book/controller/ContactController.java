package web.contact.book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.contact.book.model.Contact;
import web.contact.book.service.ContactService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Метод поиска всех контактов.
     * @return В случае успеха возвращает список контактов и устанавливает статус запроса - 200.
     *      В случае отсутствия данных устанавливает статус запроса - 404
     */
    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {
        List<Contact> contacts = contactService.findAll();

        return (contacts == null || contacts.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /**
     * Метод поиска контактов по части фамилии
     * @param partName - часть фамилии
     * @return В случае успеха возвращает список контактов и устанавливает статус запроса - 200.
     *      В случае отсутствия данных устанавливает статус запроса - 404
     */
    @GetMapping("/by_part_name/{partName}")
    public ResponseEntity<List<Contact>> findByPartName(@PathVariable @NotNull String partName) {
        List<Contact> contacts = contactService.findByPartName(partName);

        return (contacts == null || contacts.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /**
     * Метод поиска контактов по номеру
     * @param number - номер контакта
     * @return В случае успеха возвращает список контактов и устанавливает статус запроса - 200.
     *      В случае отсутствия данных устанавливает статус запроса - 404
     */
    @GetMapping("/by_number/{number}")
    public ResponseEntity<List<Contact>> findByNumber(@PathVariable @NotNull String number) {
        List<Contact> contacts = contactService.findByNumber(number);

        return (contacts == null || contacts.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /**
     * Метод добавления нового контакта
     * @param contact - десериализованная сущность контакта
     * @return В случае успеха возвращает статус запроса - 201
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Contact contact) {
        contactService.create(contact);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Метод обновления контакта
     * @param contact - десериализованная сущность контакта
     * @param id - идентификатор записи контакта
     * @return В случае успеха возвращает статус запроса - 200.
     *      Если сущность не найдена - 304
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Contact contact, @PathVariable @NotNull Long id) {
        return (contactService.update(contact, id))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    /**
     * Метод удаления контакта
     * @param id - идентификатор записи контакта
     * @return В случае успеха возвращает статус запроса - 200.
     *      Если сущность не найдена - 304
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long id) {
        return (contactService.delete(id))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
