package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.oskarkowalski.recruitment.ticketbookingapp.exceptions.ResourceNotFoundException;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.RoomRepository;

import java.util.List;

@RestController
public class RoomController {

  private final RoomRepository repository;

  @Autowired
  public RoomController(RoomRepository roomRepository) {
    this.repository = roomRepository;
  }

  @GetMapping("/rooms")
  public List<Room> getAllRooms() {
    return repository.findAll();
  }

  @GetMapping("/rooms/{id}")
  public Room getOneRoom(@PathVariable Integer id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", id));
  }

  @PostMapping("/rooms")
  public Room newRoom(@RequestBody Room newRoom) {
    return repository.save(newRoom);
  }

  @PutMapping("/rooms/{id}")
  public Room replaceRoom(@RequestBody Room newRoom, @PathVariable Integer id) {
    return repository.findById(id)
        .map(room -> {
          room.setNumber(newRoom.getNumber());
          return repository.save(room);
        }).orElseGet(() -> {
          newRoom.setId(id);
          return repository.save(newRoom);
        });
  }

  @DeleteMapping("/rooms/{id}")
  public void deleteRoom(@PathVariable Integer id) {
    repository.deleteById(id);
  }
}
