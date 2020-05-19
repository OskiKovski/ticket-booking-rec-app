package pl.oskarkowalski.recruitment.ticketbookingapp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="TICKETS")
public class Ticket {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JoinColumn(name="SEAT_ID")
  @NotNull
  @NonNull
  private Seat seat;

  @ManyToOne
  @JoinColumn(name="RESERVATION_ID")
  @NotNull
  private Reservation reservation;

  @ManyToOne
  @JoinColumn(name="SCREENING_ID")
  @NotNull
  @NonNull
  private Screening screening;

  @NotNull
  @NonNull
  private TicketPrice price;
}
