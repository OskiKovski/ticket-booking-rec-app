package pl.oskarkowalski.recruitment.ticketbookingapp.enums;

import lombok.Getter;

@Getter
public enum TicketPrice {
  ADULT((double) 25),
  STUDENT((double) 18),
  CHILD(12.5),
  ADULT_WEEKEND((double) 29),
  STUDENT_WEEKEND((double) 22),
  CHILD_WEEKEND(16.5);

  private final Double amount;

  TicketPrice(Double amount) {
    this.amount = amount;
  }

  public static TicketPrice fromAmount(Double amount) {
    if(amount.equals(ADULT.amount)) return ADULT;
    else if(amount.equals(STUDENT.amount)) return STUDENT;
    else if(amount.equals(CHILD.amount)) return CHILD;
    else if(amount.equals(ADULT_WEEKEND.amount)) return ADULT_WEEKEND;
    else if(amount.equals(STUDENT_WEEKEND.amount)) return STUDENT_WEEKEND;
    else if(amount.equals(CHILD_WEEKEND.amount)) return CHILD_WEEKEND;

    throw new IllegalArgumentException("No category for ticket with " + amount + " amount.");
  }

}
