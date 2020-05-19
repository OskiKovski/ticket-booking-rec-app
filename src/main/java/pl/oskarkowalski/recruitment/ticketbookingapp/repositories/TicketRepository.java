package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository <Ticket, Integer> {
  List<Ticket> getAllByScreening_Id(Integer screeningId);
  List<Ticket> getAllByReservation_Id(Integer reservationId);
}
