package pl.oskarkowalski.recruitment.ticketbookingapp.verification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.PersonService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = PersonService.class)
@TestPropertySource(locations = "/application.properties")
public class PersonVerificationTest {

  @Autowired
  PersonService personService;

  @Test
  public void testVariousNames_positive() {
    Person person0 = new Person("Oskar", "Kowalski");
    Person person1 = new Person("Róża", "Łobodzińska-Waszczyńska");
    Person person2 = new Person("Kaz", "Wis");

    assertTrue(personService.isPersonsFullNameLegit(person0));
    assertTrue(personService.isPersonsFullNameLegit(person1));
    assertTrue(personService.isPersonsFullNameLegit(person2));
  }

  @Test
  void testVariousNames_negative() {
    Person person0 = new Person("marek", "nowak");
    Person person1 = new Person("Zb", "Kb");
    Person person2 = new Person("Cw101", "581KZ");

    assertFalse(personService.isPersonsFullNameLegit(person0));
    assertFalse(personService.isPersonsFullNameLegit(person1));
    assertFalse(personService.isPersonsFullNameLegit(person2));
  }


}
