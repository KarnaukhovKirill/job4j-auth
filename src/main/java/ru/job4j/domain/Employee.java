package ru.job4j.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String inn;
    @Temporal(value = TemporalType.DATE)
    private Date hired;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_person",
                        joinColumns = @JoinColumn(name = "employee_id"),
                        inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> persons;

    public static Employee of(String name, String surname, String inn, Date dateOfHired) {
        Employee employee = new Employee();
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.hired = dateOfHired;
        employee.persons = new HashSet<>();
        return employee;
    }

    public void addAccount(Person person) {
        persons.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Date getHired() {
        return hired;
    }

    public void setHired(Date dateOfHired) {
        this.hired = dateOfHired;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", inn='" + inn + '\''
                + ", dateOfHired=" + hired
                + ", accounts=" + persons
                + '}';
    }
}
