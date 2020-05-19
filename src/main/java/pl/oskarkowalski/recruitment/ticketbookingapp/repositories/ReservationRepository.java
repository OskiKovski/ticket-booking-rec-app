package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
  Reservation getByTickets_Id(Integer id);
}
