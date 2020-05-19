package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.PersonRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

  @InjectMocks
  PersonController controller;

  @Mock
  PersonRepository repository;

  private final Person testPerson1 = new Person("Test1", "Person1");
  private final Person testPerson2 = new Person("Test2", "Person2");

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testPerson1, testPerson2));

    // when
    List<Person> result = controller.getAllPersons();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testPerson1.getId());
    assertThat(result.get(0).getFirstName()).isEqualTo(testPerson1.getFirstName());
    assertThat(result.get(0).getSurname()).isEqualTo(testPerson1.getSurname());
    assertThat(result.get(1).getId()).isEqualTo(testPerson2.getId());
    assertThat(result.get(1).getFirstName()).isEqualTo(testPerson2.getFirstName());
    assertThat(result.get(1).getSurname()).isEqualTo(testPerson2.getSurname());
  }
}
