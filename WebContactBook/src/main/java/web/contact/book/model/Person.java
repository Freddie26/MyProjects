package web.contact.book.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Person {

    private Long id;

    private String first_name;

    private String last_name;

    private String middle_name;

    private String position;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(first_name, person.first_name) &&
                Objects.equals(last_name, person.last_name) &&
                Objects.equals(middle_name, person.middle_name) &&
                Objects.equals(position, person.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, middle_name, position);
    }
}
