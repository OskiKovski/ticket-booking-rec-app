package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.SeatRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeatController {

  private final SeatRepository repository;
  private final ScreeningRepository screeningRepository;

  @Autowired
  public SeatController(SeatRepository repository, ScreeningRepository screeningRepository) {
    this.repository = repository;
    this.screeningRepository = screeningRepository;
  }

  @GetMapping("/seats")
  public List<Seat> getAllSeats() {
    return repository.findAll();
  }

  @GetMapping("/seats/{id}")
  public Seat getOneSeat(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", id));
  }

  @GetMapping("/seats/free/by_screening/{id}")
  public List<Seat> getFreeSeatsByScreeningId(@PathVariable Integer id) {
    List<Seat> reservedSeats = repository.findByTickets_Screening_Id(id);
    Integer roomId = screeningRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Screening", id)).getRoom().getId();
    return repository.getByRoom_Id(roomId).
        stream().
        filter(seat -> !reservedSeats.contains(seat)).
        collect(Collectors.toList());
  }

  @PostMapping("/seats")
  public Seat newSeat(@RequestBody Seat newSeat) {
    return repository.save(newSeat);
  }

  @DeleteMapping("/seats/{id}")
  public void deleteSeat(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
