package web.contact.book.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;

/**
 * Отображение таблицы Контакты в виде POJO класса
 */
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    // автоинкрементируемое значение поля id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // задаём внешний ключ на таблицу Личность
    @ManyToOne()
    // задаём явное имя колонки
    @JoinColumn(name = "person_id")
    private Person person;

    // задаём внешний ключ на таблицу Типы контактов
    @ManyToOne
    // задаём явное имя колонки
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;

    // ограничиваем длину колонки
    @Length(max = 20)
    private String number;

    public Contact() {}

    public Contact(Person person, ContactType contactType, String number) {
        this.setPerson(person);
        this.setContactType(contactType);
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id) &&
                Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", contactType=" + contactType +
                ", number='" + number + '\'' +
                '}';
    }
}
