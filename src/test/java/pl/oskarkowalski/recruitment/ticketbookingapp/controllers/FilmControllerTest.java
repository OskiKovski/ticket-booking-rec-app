package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.FilmRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {

  @InjectMocks
  FilmController controller;

  @Mock
  FilmRepository repository;

  private final Film testFilm1 = new Film("Test film 1", "Test director 1", 420);
  private final Film testFilm2 = new Film("Test film 2", "Test director 2", 69);

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testFilm1, testFilm2));

    // when
    List<Film> result = controller.getAllFilms();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testFilm1.getId());
    assertThat(result.get(0).getTitle()).isEqualTo(testFilm1.getTitle());
    assertThat(result.get(0).getDirector()).isEqualTo(testFilm1.getDirector());
    assertThat(result.get(0).getDuration()).isEqualTo(testFilm1.getDuration());
    assertThat(result.get(1).getId()).isEqualTo(testFilm2.getId());
    assertThat(result.get(1).getTitle()).isEqualTo(testFilm2.getTitle());
    assertThat(result.get(1).getDirector()).isEqualTo(testFilm2.getDirector());
    assertThat(result.get(1).getDuration()).isEqualTo(testFilm2.getDuration());
  }
}
