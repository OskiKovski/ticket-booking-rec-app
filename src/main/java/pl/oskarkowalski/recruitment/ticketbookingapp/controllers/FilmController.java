package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.FilmRepository;

import java.util.List;

@RestController
public class FilmController {

  private final FilmRepository repository;

  @Autowired
  public FilmController(FilmRepository filmRepository) {
    this.repository = filmRepository;
  }

  @GetMapping("/films")
  public List<Film> getAllFilms() {
    return repository.findAll();
  }

  @GetMapping("/films/{id}")
  public Film getOneFilm(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film", id));
  }

  @PostMapping("/films")
  public Film newFilm(@RequestBody Film newFilm) {
    return repository.save(newFilm);
  }

  @PutMapping("/films/{id}")
  public Film replaceFilm(@RequestBody Film newFilm, @PathVariable Integer id) {
    return repository.findById(id)
        .map(film -> {
          film.setTitle(newFilm.getTitle());
          film.setDirector(newFilm.getDirector());
          film.setDuration(newFilm.getDuration());
          return repository.save(film);
        }).orElseGet(() -> {
          newFilm.setId(id);
          return repository.save(newFilm);
        });
  }

  @DeleteMapping("/films/{id}")
  public void deleteFilm(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
