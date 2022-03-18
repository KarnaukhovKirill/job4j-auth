package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.service.EmployeeService;
import ru.job4j.service.PersonService;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PersonService personService;

    public EmployeeController(final EmployeeService employeeService, final PersonService personService) {
        this.employeeService = employeeService;
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var person = employeeService.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Employee()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                this.employeeService.save(employee),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Employee employee = new Employee();
        employee.setId(id);
        this.employeeService.delete(employee);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/")
    public ResponseEntity<Employee> createAccount(@PathVariable int id, @RequestBody Person person) {
        if (employeeService.findById(id).isPresent()) {
            Employee employee = employeeService.findById(id).get();
            employee.addAccount(person);
            this.employeeService.save(employee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new Employee(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Void> updateAccount(@PathVariable int id,  @RequestBody Person person) {
        if (employeeService.findById(id).isPresent()) {
            Employee employee = employeeService.findById(id).get();
            employee.addAccount(person);
            this.employeeService.save(employee);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{empId}/{accId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int empId, @PathVariable int accId) {
        if (employeeService.findById(empId).isPresent()) {
            Person person = new Person();
            person.setId(accId);
            personService.delete(person);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
