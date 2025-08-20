package by.koval.importApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReqParams {
    private String agency;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
    private String clientGuid;
}
