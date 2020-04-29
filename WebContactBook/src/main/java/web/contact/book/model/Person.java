package web.contact.book.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.List;
import java.util.Set;

/**
 * Отображение таблицы Личность в виде POJO класса
 */
@Entity
public class Person {

    @Id
    // автоинкрементируемое значение поля id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // задаём длину строки в БД
    @Length(max = 80)
    // поле автоматически мапится в колонку first_name
    private String firstName;

    // задаём длину строки в БД
    @Length(max = 80)
    // поле автоматически мапится в last_name
    private String lastName;

    // задаём длину строки в БД
    @Length(max = 80)
    // поле автоматически мапится в middle_name
    private String middleName;

    // задаём явную длину колонки, хоть по умолчанию длина строки в БД 255 символов
    @Length(max = 255)
    private String position;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();

    public Person() {}

    public Person(String firstName,
                  String lastName,
                  String middleName,
                  String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(middleName, person.middleName) &&
                Objects.equals(position, person.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName, position);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position='" + position + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
