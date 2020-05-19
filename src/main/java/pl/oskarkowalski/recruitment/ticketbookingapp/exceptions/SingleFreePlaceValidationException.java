package pl.oskarkowalski.recruitment.ticketbookingapp.exceptions;

public class SingleFreePlaceValidationException extends RuntimeException {
  public SingleFreePlaceValidationException() {
    super("There cannot be a single free place on the screening.");
  }
}
