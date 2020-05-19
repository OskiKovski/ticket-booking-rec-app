package pl.oskarkowalski.recruitment.ticketbookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
