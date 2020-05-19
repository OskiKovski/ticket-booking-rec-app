package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository <Seat, Integer> {
  List<Seat> getByRoom_Id(Integer roomId);
  List<Seat> findByTickets_Screening_Id(Integer screeningId);
}
