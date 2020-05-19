package pl.oskarkowalski.recruitment.ticketbookingapp.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.oskarkowalski.recruitment.ticketbookingapp.dtos.ReservationDto;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Person;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Reservation;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.PersonRepository;

@Component
public class ReservationMapper {

  private final PersonRepository personRepository;

  @Autowired
  public ReservationMapper(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Reservation convertToReservation(ReservationDto reservationDto) {
    Person person = personRepository.getOne(reservationDto.getPersonId());

    Reservation reservation = new Reservation();
    reservation.setId(reservationDto.getId());
    reservation.setOrderTime(reservationDto.getOrderTime());
    reservation.setExpirationTime(reservationDto.getExpirationTime());
    reservation.setIsPaid(reservationDto.getIsPaid());
    reservation.setDiscount(reservationDto.getDiscount());
    reservation.setPerson(person);

    return reservation;
  }

}
