package pl.oskarkowalski.recruitment.ticketbookingapp.verification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.TicketDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.ScreeningRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.services.ReservationService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TicketVerificationTest {

  @InjectMocks
  ReservationService reservationService;

  @Mock
  ScreeningRepository screeningRepository;

  private final Film testFilm = new Film("Test", "Test", 200);
  private final Room testRoom = new Room(100);

  @Test
  void testWeekendPrices() {
    Clock clock = Clock.fixed(Instant.parse("2020-05-15T14:15:30.00Z"), ZoneId.of("CET"));
    LocalDateTime fridayTime = LocalDateTime.now(clock);
    LocalDateTime saturdayTime = LocalDateTime.now(clock).plusDays(1);
    LocalDateTime sundayTime = LocalDateTime.now(clock).plusDays(2);

    Screening testScreeningFriday = new Screening(testFilm, testRoom, fridayTime.plusMinutes(30));
    Screening testScreeningSaturday = new Screening(testFilm, testRoom, saturdayTime.plusMinutes(30));
    Screening testScreeningSunday = new Screening(testFilm, testRoom, sundayTime.plusMinutes(30));

    assertThat(reservationService.checkTicketPrice(TicketPrice.ADULT, testScreeningFriday)).
        isEqualTo(TicketPrice.ADULT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.STUDENT, testScreeningFriday)).
        isEqualTo(TicketPrice.STUDENT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.CHILD, testScreeningFriday)).
        isEqualTo(TicketPrice.CHILD_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.ADULT, testScreeningSaturday)).
        isEqualTo(TicketPrice.ADULT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.STUDENT, testScreeningSaturday)).
        isEqualTo(TicketPrice.STUDENT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.CHILD, testScreeningSaturday)).
        isEqualTo(TicketPrice.CHILD_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.ADULT, testScreeningSunday)).
        isEqualTo(TicketPrice.ADULT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.STUDENT, testScreeningSunday)).
        isEqualTo(TicketPrice.STUDENT_WEEKEND);
    assertThat(reservationService.checkTicketPrice(TicketPrice.CHILD, testScreeningSunday)).
        isEqualTo(TicketPrice.CHILD_WEEKEND);
  }

  @Test
  void testTimeAmountBeforeScreeningCorrectness() {
    Screening testScreening0 = new Screening(testFilm, testRoom, LocalDateTime.now().plusSeconds(870));
    Screening testScreening1 = new Screening(testFilm, testRoom, LocalDateTime.now().plusMinutes(930));

    assertThat(reservationService.isThisAGoodTime(LocalDateTime.now(), testScreening1)).isTrue();
    assertThat(reservationService.isThisAGoodTime(LocalDateTime.now(), testScreening0)).isFalse();
  }

  @Test
  void testSinglePlaceReaction() {
    Screening testScreening = new Screening(testFilm, testRoom, LocalDateTime.now().plusMinutes(30));

    Seat testSeat1 = new Seat(testRoom, SeatRow.I,1);
    Seat testSeat2 = new Seat(testRoom, SeatRow.I,2);
    Seat testSeat3 = new Seat(testRoom, SeatRow.I,3);
    Seat testSeat4 = new Seat(testRoom, SeatRow.I,4);
    Seat testSeat5 = new Seat(testRoom, SeatRow.I,5);
    Seat testSeat6 = new Seat(testRoom, SeatRow.II,1);
    Seat testSeat7 = new Seat(testRoom, SeatRow.II,2);
    Seat testSeat8 = new Seat(testRoom, SeatRow.II,3);
    Seat testSeat9 = new Seat(testRoom, SeatRow.II,4);
    Seat testSeat10 = new Seat(testRoom, SeatRow.II,5);

    testRoom.setSeats(Arrays.asList(testSeat1, testSeat2, testSeat3, testSeat4, testSeat5,
        testSeat6, testSeat7, testSeat8, testSeat9, testSeat10));

    Ticket ticket0 = new Ticket(testSeat1,testScreening,TicketPrice.ADULT);
    Ticket ticket1 = new Ticket(testSeat2,testScreening,TicketPrice.ADULT);
    Ticket ticket2 = new Ticket(testSeat10,testScreening,TicketPrice.ADULT);

    testScreening.setTickets(Arrays.asList(ticket0, ticket1, ticket2));

    TicketDto purchasedTicketBad0 = new TicketDto(20, 30, testSeat4, testScreening.getId(), TicketPrice.ADULT.getAmount());
    TicketDto purchasedTicketBad1 = new TicketDto(21, 30, testSeat7, testScreening.getId(), TicketPrice.ADULT.getAmount());
    TicketDto purchasedTicketBad2 = new TicketDto(22, 30, testSeat8, testScreening.getId(), TicketPrice.ADULT.getAmount());

    TicketDto purchasedTicketGood0 = new TicketDto(23, 31, testSeat3, testScreening.getId(), TicketPrice.ADULT.getAmount());
    TicketDto purchasedTicketGood1 = new TicketDto(24, 31, testSeat6, testScreening.getId(), TicketPrice.ADULT.getAmount());
    TicketDto purchasedTicketGood2 = new TicketDto(25, 31, testSeat7, testScreening.getId(), TicketPrice.ADULT.getAmount());

    assertThat(reservationService.isThereNoSingleFreeSeatOnScreening(
        Arrays.asList(purchasedTicketBad0, purchasedTicketBad1, purchasedTicketBad2), testScreening)).isFalse();
    assertThat(reservationService.isThereNoSingleFreeSeatOnScreening(
        Arrays.asList(purchasedTicketGood0, purchasedTicketGood1, purchasedTicketGood2), testScreening)).isTrue();
  }
}
