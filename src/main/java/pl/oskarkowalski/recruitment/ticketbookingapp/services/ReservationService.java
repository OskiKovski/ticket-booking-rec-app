package pl.oskarkowalski.recruitment.ticketbookingapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.ReservationDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.TicketDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReservationService {

  private final ScreeningRepository screeningRepository;
  private final TicketRepository ticketRepository;

  @Value("${discount.code}")
  private char[] discountCode;

  @Autowired
  public ReservationService(ScreeningRepository screeningRepository, TicketRepository ticketRepository) {
    this.screeningRepository = screeningRepository;
    this.ticketRepository = ticketRepository;
  }

  public boolean isOkayToBuy(ReservationDto reservationDto) {
    TicketDto ticketDto = reservationDto.getTickets().iterator().next();
    Screening screening = screeningRepository.findById(ticketDto.getScreeningId())
        .orElseThrow(() -> new ResourceNotFoundException("Screening", ticketDto.getScreeningId()));
    return isThereNoSingleFreeSeatOnScreening(reservationDto.getTickets(), screening) && isThisAGoodTime(reservationDto.getOrderTime(), screening);
  }

  public boolean isThisAGoodTime(LocalDateTime orderTime, Screening screening) {
    LocalDateTime startTime = screening.getTime();
    return orderTime.compareTo(startTime.minusMinutes(15)) <= 0;
  }

  public Boolean isDiscountCodeValid(String providedCode) {
    char[] providedCodeLetters = providedCode.toCharArray();
    return Arrays.equals(providedCodeLetters, discountCode);
  }

  public Double calculatePrice(Reservation reservation) {
    Double totalPrice = (double) 0;
    List<TicketPrice> ticketPrices = reservation.getTickets().stream().map(Ticket::getPrice).collect(Collectors.toList());

    for (TicketPrice price : ticketPrices) {
      totalPrice += price.getAmount();
    }

    if(Boolean.TRUE.equals(reservation.getDiscount())) {
      totalPrice /= 2;
    }

    return totalPrice;
  }

  public Optional<Reservation> deleteTicketFromReservation(Ticket ticket, Reservation reservation) {
    List<Ticket> updatedTicketsList = ticketRepository.getAllByReservation_Id(reservation.getId());
    updatedTicketsList.remove(ticket);

    if(updatedTicketsList.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new Reservation(reservation.getId(),
          reservation.getPerson(),
          updatedTicketsList,
          reservation.getExpirationTime(),
          reservation.getOrderTime(),
          reservation.getIsPaid(),
          reservation.getDiscount()));
    }
  }

  public TicketPrice checkTicketPrice(TicketPrice ticketPrice, Screening screening) {
    if (isWeekend(screening.getTime())) {
      if (TicketPrice.CHILD.equals(ticketPrice)) return TicketPrice.CHILD_WEEKEND;
      else if (TicketPrice.STUDENT.equals(ticketPrice)) return TicketPrice.STUDENT_WEEKEND;
      else if (TicketPrice.ADULT.equals(ticketPrice)) return TicketPrice.ADULT_WEEKEND;
      else throw new IllegalArgumentException("Unspecified ticket type");
    } else {
      return ticketPrice;
    }
  }

  public boolean isThereNoSingleFreeSeatOnScreening(List<TicketDto> reservationTicketDtos, Screening screening) {
    List<SeatRow> analyzedRows = reservationTicketDtos.
        stream().
        map(ticket -> ticket.getSeat().getSeatRow()).
        collect(Collectors.toList());

    for (SeatRow seatRow : analyzedRows) {
      List<Seat> analyzedRowSeats = screening.getRoom().getSeats().
          stream().
          filter(seat -> seatRow.equals(seat.getSeatRow())).
          collect(Collectors.toList());

      List<Seat> occupiedSeats = screening.getTickets().
          stream().
          map(Ticket::getSeat).
          filter(seat -> seatRow.equals(seat.getSeatRow())).
          collect(Collectors.toList());

      List<Seat> selectedSeats = reservationTicketDtos.
          stream().
          map(TicketDto::getSeat).
          filter(ticket -> seatRow.equals(ticket.getSeatRow())).
          collect(Collectors.toList());

      List<Seat> allSeats = Stream.concat(occupiedSeats.stream(), selectedSeats.stream()).collect(Collectors.toList());

      if (isThereAFreeSingleSeatInARow(analyzedRowSeats, allSeats)) return false;
    }
    return true;
  }

  private boolean isThereAFreeSingleSeatInARow(List<Seat> allSeats, List<Seat> occupiedSeats) {
    if (occupiedSeats.size() == allSeats.size()) return false;

    List<Integer> ticketSeatsNumbers = occupiedSeats
        .stream()
        .map(Seat::getSeatPlace)
        .collect(Collectors.toList());

    List<Integer> freeSeats = allSeats
        .stream()
        .map(Seat::getSeatPlace)
        .filter(seatPlace -> !ticketSeatsNumbers.contains(seatPlace))
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.toList());

    if (freeSeats.size() == 1) return true;

    Integer previousSeat = freeSeats.get(0)-1;

    for (Integer freeSeatNumber : freeSeats) {
      if (freeSeatNumber - previousSeat > 1) return true;
      previousSeat = freeSeatNumber;
    }

    return false;
  }

  private boolean isWeekend(LocalDateTime time) {
    switch (time.getDayOfWeek()) {
      case FRIDAY:
        return time.getHour() >= 14;
      case SATURDAY:
        return true;
      case SUNDAY:
        return time.getHour() < 23;
      default:
        return false;
    }
  }
}
