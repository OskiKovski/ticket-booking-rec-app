package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
}
