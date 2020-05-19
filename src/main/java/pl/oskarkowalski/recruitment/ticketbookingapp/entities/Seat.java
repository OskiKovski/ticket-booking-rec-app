package pl.oskarkowalski.recruitment.ticketbookingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.oskarkowalski.recruitment.ticketbookingapp.enums.SeatRow;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="SEATS")
public class Seat {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JoinColumn(name="ROOM_ID")
  @NotNull
  @NonNull
  private Room room;

  @OneToMany(mappedBy = "seat")
  @JsonIgnore
  private List<Ticket> tickets;

  @NotNull
  @NonNull
  @Enumerated(EnumType.STRING)
  private SeatRow seatRow;

  @NotNull
  @NonNull
  private Integer seatPlace;
}
