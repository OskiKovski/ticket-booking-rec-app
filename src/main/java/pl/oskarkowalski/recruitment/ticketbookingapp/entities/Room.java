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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="ROOMS")
public class Room {

  @Id
  @GeneratedValue
  private Integer id;

  @OneToMany(mappedBy = "room")
  @JsonIgnore
  private List<Screening> screenings;

  @OneToMany(mappedBy = "room")
  @JsonIgnore
  private List<Seat> seats;

  @NotNull
  @NonNull
  private Integer number;
}
