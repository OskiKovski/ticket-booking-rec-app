package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.NameNotCorrectException;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.PersonRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.PersonService;

import java.util.List;

@RestController
public class PersonController {

  private final PersonRepository repository;
  private final PersonService service;

  @Autowired
  public PersonController(PersonRepository repository, PersonService service) {
    this.repository = repository;
    this.service = service;
  }


  @GetMapping("/persons")
  public List<Person> getAllPersons() {
    return repository.findAll();
  }

  @GetMapping("/persons/{id}")
  public Person getOnePerson(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person", id));
  }

  @PostMapping("/persons")
  public Person newPerson(@RequestBody Person newPerson) {
    if (service.isPersonsFullNameLegit(newPerson)) {
      return repository.save(newPerson);
    } else {
      throw new NameNotCorrectException();
    }
  }

  @PutMapping("/persons/{id}")
  public Person replacePerson(@RequestBody Person newPerson, @PathVariable Integer id) {
    return repository.findById(id)
        .map(person -> {
          if (service.isPersonsFullNameLegit(newPerson)) {
            person.setFirstName(newPerson.getFirstName());
            person.setSurname(newPerson.getSurname());
            return repository.save(person);
          } else {
            throw new NameNotCorrectException();
          }
        }).orElseGet(() -> {
          newPerson.setId(id);
          return repository.save(newPerson);
        });
  }

  @DeleteMapping("/persons/{id}")
  public void deletePerson(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
