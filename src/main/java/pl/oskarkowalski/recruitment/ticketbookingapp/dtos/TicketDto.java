package pl.oskarkowalski.recruitment.ticketbookingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;

@Getter
@AllArgsConstructor
public class TicketDto {
  private final Integer id;
  private final Integer reservationId;
  private final Seat seat;
  private final Integer screeningId;
  private final Double price;
}
