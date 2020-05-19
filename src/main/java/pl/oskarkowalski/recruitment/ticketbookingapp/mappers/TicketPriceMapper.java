package pl.oskarkowalski.recruitment.ticketbookingapp.mappers;

import pl.oskarkowalski.recruitment.ticketbookingapp.enums.TicketPrice;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketPriceMapper implements AttributeConverter<TicketPrice, Double> {
  @Override
  public Double convertToDatabaseColumn(TicketPrice attribute) {
    return attribute.getAmount();
  }

  @Override
  public TicketPrice convertToEntityAttribute(Double dbData) {
    return TicketPrice.fromAmount(dbData);
  }
}
