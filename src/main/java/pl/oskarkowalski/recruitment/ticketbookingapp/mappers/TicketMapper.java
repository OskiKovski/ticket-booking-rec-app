package pl.oskarkowalski.recruitment.ticketbookingapp.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.TicketDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ReservationRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.ReservationService;

@Component
public class TicketMapper {

  private final ScreeningRepository screeningRepository;
  private final ReservationRepository reservationRepository;
  private final ReservationService reservationService;

  @Autowired
  public TicketMapper(ScreeningRepository screeningRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
    this.screeningRepository = screeningRepository;
    this.reservationRepository = reservationRepository;
    this.reservationService = reservationService;
  }

  public Ticket convertToTicket(TicketDto ticketDto) {
    Screening screening = screeningRepository.getOne(ticketDto.getScreeningId());
    Reservation reservation = reservationRepository.getOne(ticketDto.getReservationId());

    Ticket ticket = new Ticket();
    ticket.setId(ticketDto.getId());
    ticket.setSeat(ticketDto.getSeat());
    ticket.setPrice(reservationService.checkTicketPrice(TicketPrice.fromAmount(ticketDto.getPrice()), screening));
    ticket.setScreening(screening);
    ticket.setReservation(reservation);

    return ticket;
  }

}
