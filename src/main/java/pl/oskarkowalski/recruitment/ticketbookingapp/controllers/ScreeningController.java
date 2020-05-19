package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ScreeningController {

  private final ScreeningRepository repository;

  @Autowired
  public ScreeningController(ScreeningRepository screeningRepository) {
    this.repository = screeningRepository;
  }

  @GetMapping("/screenings")
  public List<Screening> getAllScreenings() {
    return repository.findAll();
  }

  @GetMapping("/screenings/timebetween/{start_time}/{end_time}")
  public List<Screening> getScreeningsByTimeBetween(
      @PathVariable(value = "start_time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
      @PathVariable(value = "end_time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime) {
    return repository.findByTimeIsBetween(startTime, endTime);
  }

  @GetMapping("/screenings/{id}")
  public Screening getOneScreening(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Screening", id));
  }

  @PostMapping("/screenings")
  public Screening newScreening(@RequestBody Screening newScreening) {
    return repository.save(newScreening);
  }

  @DeleteMapping("/screenings/{id}")
  public void deleteScreening(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
