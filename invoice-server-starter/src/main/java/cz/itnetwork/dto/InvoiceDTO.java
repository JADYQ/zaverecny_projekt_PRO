package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    @JsonProperty("_id")
    private Long id; //"The client requires the attribute name "_id" according to the documentation."
    private Long invoiceNumber;
    private PersonDTO seller;
    private PersonDTO buyer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issued;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String product;
    private Double price;
    private Long vat;
    private String note;
}
