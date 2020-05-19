package pl.oskarkowalski.recruitment.ticketbookingapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationDto {
  private final Integer id;
  private final Integer personId;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Warsaw")
  private final LocalDateTime orderTime;

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Warsaw")
  private final LocalDateTime expirationTime;

  private final List<TicketDto> tickets;
  private final Boolean isPaid;
  private final Boolean discount;
}
