package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScreeningControllerTest {

  @InjectMocks
  ScreeningController controller;

  @Mock
  ScreeningRepository repository;

  private final Film testFilm = new Film("Test", "Test", 200);
  private final Room testRoom = new Room(100);
  private final Screening testScreening1 = new Screening(testFilm, testRoom, LocalDateTime.now());
  private final Screening testScreening2 = new Screening(testFilm, testRoom, LocalDateTime.now());

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testScreening1, testScreening2));

    // when
    List<Screening> result = controller.getAllScreenings();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testScreening1.getId());
    assertThat(result.get(0).getTime()).isEqualTo(testScreening1.getTime());
    assertThat(result.get(1).getId()).isEqualTo(testScreening2.getId());
    assertThat(result.get(1).getTime()).isEqualTo(testScreening2.getTime());
  }
}
