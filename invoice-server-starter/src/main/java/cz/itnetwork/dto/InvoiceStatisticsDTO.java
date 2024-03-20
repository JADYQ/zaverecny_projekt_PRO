package cz.itnetwork.dto;

import lombok.Data;

@Data
public class InvoiceStatisticsDTO {
    private Double currentYearSum;
    private Double allTimeSum;
    private Long invoicesCount;
}
