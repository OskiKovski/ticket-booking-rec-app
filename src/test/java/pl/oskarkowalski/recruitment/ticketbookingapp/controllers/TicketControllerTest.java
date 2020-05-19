package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

  @InjectMocks
  TicketController controller;

  @Mock
  TicketRepository repository;

  private final Film testFilm = new Film("Test", "Test", 200);
  private final Room testRoom = new Room(100);
  private final Screening testScreening = new Screening(testFilm, testRoom, LocalDateTime.now().plusMinutes(30));
  private final Seat testSeat = new Seat(testRoom, SeatRow.I,1);

  private final Ticket testTicket1 = new Ticket(testSeat, testScreening, TicketPrice.CHILD);
  private final Ticket testTicket2 = new Ticket(testSeat, testScreening, TicketPrice.CHILD);

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testTicket1, testTicket2));

    // when
    List<Ticket> result = controller.getAllTickets();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testTicket1.getId());
    assertThat(result.get(0).getPrice()).isEqualTo(testTicket1.getPrice());
    assertThat(result.get(1).getId()).isEqualTo(testTicket1.getId());
    assertThat(result.get(1).getPrice()).isEqualTo(testTicket1.getPrice());
  }
}
