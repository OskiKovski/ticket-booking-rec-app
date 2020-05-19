package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ReservationRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.TicketRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

  private final TicketRepository ticketRepository;
  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

  @Autowired
  public TicketController(TicketRepository ticketRepository,
                          ReservationRepository reservationRepository,
                          ReservationService reservationService) {
    this.ticketRepository = ticketRepository;
    this.reservationRepository = reservationRepository;
    this.reservationService = reservationService;
  }


  @GetMapping("/tickets")
  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  @GetMapping("/tickets/by_screening/{screeningId}")
  public List<Ticket> getTicketsByScreeningId(@PathVariable Integer screeningId) {
    return ticketRepository.getAllByScreening_Id(screeningId);
  }

  @GetMapping("/tickets/by_reservation/{reservationId}")
  public List<Ticket> getTicketsByReservationId(@PathVariable Integer reservationId) {
    return ticketRepository.getAllByReservation_Id(reservationId);
  }

  @GetMapping("/tickets/{id}")
  public Ticket getOneTicket(@PathVariable Integer id) {
    return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
  }

  @DeleteMapping("/tickets/{id}")
  public void deleteTicket(@PathVariable Integer id) {
    Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
    Reservation reservation = reservationRepository.getByTickets_Id(id);

    Optional<Reservation> reservationOptional = reservationService.deleteTicketFromReservation(ticket, reservation);

    if(reservationOptional.isPresent()) {
      reservationRepository.save(reservationOptional.get());
    } else {
      reservationRepository.deleteById(reservation.getId());
    }

    ticketRepository.deleteById(id);
  }
}
