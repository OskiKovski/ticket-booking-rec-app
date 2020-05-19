package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.DiscountCodeRequest;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.ReservationDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.TicketDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.SingleFreePlaceValidationException;
import pl.oskarkowalski.recruitment.ticketbookingapp.mappers.ReservationMapper;
import pl.oskarkowalski.recruitment.ticketbookingapp.mappers.TicketMapper;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ReservationRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.TicketRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.ReservationService;

import java.util.List;

@RestController
public class ReservationController {

  private final ReservationMapper reservationMapper;
  private final TicketMapper ticketMapper;
  private final TicketRepository ticketRepository;
  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

  private static final String RESERVATION = "Reservation";

  @Autowired
  public ReservationController(ReservationMapper reservationMapper,
                               TicketMapper ticketMapper,
                               TicketRepository ticketRepository,
                               ReservationRepository reservationRepository,
                               ReservationService reservationService) {
    this.reservationMapper = reservationMapper;
    this.ticketMapper = ticketMapper;
    this.ticketRepository = ticketRepository;
    this.reservationRepository = reservationRepository;
    this.reservationService = reservationService;
  }

  @GetMapping("/reservations")
  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  @GetMapping("/reservations/{id}")
  public Reservation getOneReservation(@PathVariable Integer id) {
    return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESERVATION, id));
  }

  @GetMapping("/reservations/by_ticket/{id}")
  public Reservation getOneByTicketId(@PathVariable Integer id) {
    return reservationRepository.getByTickets_Id(id);
  }

  @GetMapping("/reservations/{id}/price")
  public Double getTotalPrice(@PathVariable Integer id) {
    Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESERVATION, id));
    return reservationService.calculatePrice(reservation);
  }

  @PostMapping("/reservations")
  public Reservation commitReservation(@RequestBody ReservationDto newReservationDto) {
    Reservation result = reservationRepository.saveAndFlush(reservationMapper.convertToReservation(newReservationDto));
    List<TicketDto> ticketDtoList = newReservationDto.getTickets();
    if(reservationService.isOkayToBuy(newReservationDto)) {
      for (TicketDto ticketDto : ticketDtoList) {
        ticketRepository.save(ticketMapper.convertToTicket(ticketDto));
      }
    } else {
      throw new SingleFreePlaceValidationException();
    }
    return result;
  }

  @PatchMapping("/reservations/{id}/paid")
  public Reservation markAsPaid(@PathVariable Integer id) {
    return reservationRepository.findById(id)
        .map(reservation -> {
          reservation.setIsPaid(Boolean.TRUE);
          return reservationRepository.save(reservation);
        })
        .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, id));
  }


  @PatchMapping("/reservations/{id}")
  public Reservation setDiscount(@RequestBody DiscountCodeRequest discountCodeRequest, @PathVariable Integer id) {
    Boolean isCodeValid = reservationService.isDiscountCodeValid(discountCodeRequest.getDiscountCode());
    return reservationRepository.findById(id)
        .map(reservation -> {
          reservation.setDiscount(isCodeValid);
          return reservationRepository.save(reservation);
        })
        .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, id));
  }

  @DeleteMapping("/reservations/{id}")
  public void deleteReservation(@PathVariable Integer id) {
    List<Ticket> ticketsConnectedWithReservation = ticketRepository.getAllByReservation_Id(id);
    for(Ticket ticket : ticketsConnectedWithReservation) {
      ticketRepository.delete(ticket);
    }
    reservationRepository.deleteById(id);
  }

}
