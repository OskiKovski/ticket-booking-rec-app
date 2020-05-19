package pl.oskarkowalski.recruitment.ticketbookingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiscountCodeRequest {
  private final Integer id;
  private final String discountCode;
}
