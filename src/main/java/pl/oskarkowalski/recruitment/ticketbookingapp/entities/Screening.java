package pl.oskarkowalski.recruitment.ticketbookingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="SCREENINGS")
public class Screening {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JoinColumn(name="FILM_ID")
  @NotNull
  @NonNull
  private Film film;

  @ManyToOne
  @JoinColumn(name="ROOM_ID")
  @NotNull
  @NonNull
  private Room room;

  @OneToMany(mappedBy = "screening")
  @JsonIgnore
  List<Ticket> tickets;

  @NotNull
  @NonNull
  private LocalDateTime time;
}
