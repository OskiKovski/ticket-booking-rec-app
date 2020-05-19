package pl.oskarkowalski.recruitment.ticketbookingapp.exceptions;

public class NameNotCorrectException extends RuntimeException {
  public NameNotCorrectException() {
    super("Provided name or surname is not correct.");
  }
}
