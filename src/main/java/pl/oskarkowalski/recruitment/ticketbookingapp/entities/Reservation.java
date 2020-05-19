package pl.oskarkowalski.recruitment.ticketbookingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="RESERVATIONS")
public class Reservation {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @NotNull
  @NonNull
  @JoinColumn(name="PERSON_ID")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Person person;

  @OneToMany(mappedBy = "reservation")
  @JsonIgnore
  private List<Ticket> tickets;

  @NotNull
  private LocalDateTime expirationTime;

  @NotNull
  private LocalDateTime orderTime;

  @NotNull
  private Boolean isPaid;

  @NotNull
  private Boolean discount;
}
