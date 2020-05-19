package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Screening;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository <Screening, Integer> {
  List<Screening> findByTimeIsBetween(LocalDateTime startTime, LocalDateTime endTime);
}
