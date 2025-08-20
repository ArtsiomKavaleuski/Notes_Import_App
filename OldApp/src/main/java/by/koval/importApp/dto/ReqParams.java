package by.koval.importApp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReqParams {
    private String agency;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String clientGuid;
}
