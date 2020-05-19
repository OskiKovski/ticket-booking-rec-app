package pl.oskarkowalski.recruitment.ticketbookingapp.verification;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ReservationRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.TicketRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.ReservationService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ReservationVerificationTest {

  @InjectMocks
  ReservationService reservationService;

  @Mock
  ReservationRepository reservationRepository;

  @Mock
  TicketRepository ticketRepository;

  private final Film testFilm = new Film("Test", "Test", 200);
  private final Room testRoom = new Room(100);
  private final Person testPerson = new Person("Test", "Test");
  private final Screening testScreening = new Screening(testFilm, testRoom, LocalDateTime.now().plusMinutes(30));
  private final Seat testSeat1 = new Seat(testRoom, SeatRow.I,1);
  private final Seat testSeat2 = new Seat(testRoom, SeatRow.I,2);
  private final Seat testSeat3 = new Seat(testRoom, SeatRow.I,3);
  private final Seat testSeat4 = new Seat(testRoom, SeatRow.I,4);
  private final Seat testSeat5 = new Seat(testRoom, SeatRow.I,5);
  private final Ticket testTicket0 = new Ticket(testSeat1, testScreening, TicketPrice.ADULT);
  private final Ticket testTicket1 = new Ticket(testSeat2, testScreening, TicketPrice.STUDENT);
  private final Ticket testTicket2 = new Ticket(testSeat3, testScreening, TicketPrice.CHILD);
  private final Ticket testTicket3 = new Ticket(testSeat4, testScreening, TicketPrice.CHILD);
  private final Ticket testTicket4 = new Ticket(testSeat5, testScreening, TicketPrice.ADULT);

  @Before
  public void init() {
    testRoom.setSeats(Arrays.asList(testSeat1, testSeat2, testSeat3, testSeat4, testSeat5));
    testScreening.setTickets(Arrays.asList(testTicket0, testTicket1, testTicket2, testTicket3, testTicket4));
  }

  @Test
  void testPriceCalculation() {
    Clock clock = Clock.fixed(Instant.parse("2020-05-18T10:00:00.00Z"), ZoneId.of("CET"));
    Reservation testReservation = new Reservation(0, testPerson,
        Arrays.asList(testTicket0, testTicket1, testTicket2, testTicket3, testTicket4),
        LocalDateTime.now(clock).plusMinutes(15), LocalDateTime.now(clock), Boolean.FALSE, Boolean.FALSE);

    assertThat(reservationService.calculatePrice(testReservation)).isEqualTo(93);
  }

  @Test
  void testDiscountIncluding() {
    Reservation testReservationWithoutDiscount = new Reservation(0, testPerson,
        Arrays.asList(testTicket0, testTicket1, testTicket2, testTicket3, testTicket4),
        LocalDateTime.now().plusMinutes(15), LocalDateTime.now(), Boolean.FALSE, Boolean.FALSE);

    Reservation testReservationWithDiscount = new Reservation(0, testPerson,
        Arrays.asList(testTicket0, testTicket1, testTicket2, testTicket3, testTicket4),
        LocalDateTime.now().plusMinutes(15), LocalDateTime.now(), Boolean.FALSE, Boolean.TRUE);

    Double sumWithDiscount = reservationService.calculatePrice(testReservationWithDiscount);
    Double sumWithoutDiscount = reservationService.calculatePrice(testReservationWithoutDiscount);

    assertThat(sumWithDiscount).isEqualTo(sumWithoutDiscount/2);
  }

}
