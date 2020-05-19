package pl.oskarkowalski.recruitment.ticketbookingapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;

@Service
public class PersonService {

  @Value("${validation.first-name.regexp}")
  private String firstNamePattern;

  @Value("${validation.surname.regexp}")
  private String surnamePattern;

  public boolean isPersonsFullNameLegit(Person person) {
    return person.getFirstName().matches(firstNamePattern) && person.getSurname().matches(surnamePattern);
  }

}
