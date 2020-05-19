package pl.oskarkowalski.recruitment.ticketbookingapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.oskarkowalski.recruitment.ticketbookingapp.entities.Room;
import pl.oskarkowalski.recruitment.ticketbookingapp.repositories.RoomRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

  @InjectMocks
  RoomController controller;

  @Mock
  RoomRepository repository;

  private final Room testRoom1 = new Room(420);
  private final Room testRoom2 = new Room(666);

  @Test
  void testFindAll() {
    // given
    when(repository.findAll()).thenReturn(Arrays.asList(testRoom1, testRoom2));

    // when
    List<Room> result = controller.getAllRooms();

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.get(0).getId()).isEqualTo(testRoom1.getId());
    assertThat(result.get(0).getNumber()).isEqualTo(testRoom1.getNumber());
    assertThat(result.get(1).getId()).isEqualTo(testRoom2.getId());
    assertThat(result.get(1).getNumber()).isEqualTo(testRoom2.getNumber());
  }
}
