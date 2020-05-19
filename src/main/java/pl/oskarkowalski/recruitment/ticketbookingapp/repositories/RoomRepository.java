package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;

public interface RoomRepository extends JpaRepository <Room, Integer> {
}
