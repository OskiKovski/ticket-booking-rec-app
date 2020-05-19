package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.SeatRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatControllerTest {

  @InjectMocks
  SeatController controller;

  @Mock
  SeatRepository repository;

  private final Room testRoom = new Room(100);
  private final Seat testSeat1 = new Seat(testRoom, SeatRow.I, 0);
  private final Seat testSeat2 = new Seat(testRoom, SeatRow.I, 0);

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testSeat1, testSeat2));

    // when
    List<Seat> result = controller.getAllSeats();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testSeat1.getId());
    assertThat(result.get(0).getSeatPlace()).isEqualTo(testSeat1.getSeatPlace());
    assertThat(result.get(0).getSeatRow()).isEqualTo(testSeat1.getSeatRow());
    assertThat(result.get(1).getId()).isEqualTo(testSeat2.getId());
    assertThat(result.get(1).getSeatPlace()).isEqualTo(testSeat2.getSeatPlace());
    assertThat(result.get(1).getSeatRow()).isEqualTo(testSeat2.getSeatRow());
  }
}
