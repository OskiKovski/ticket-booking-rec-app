package pl.oskarkowalski.recruitment.ticketbookingapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resource, Integer id) {
    super("Cannot find " + resource + " with id " + id);
  }
}
