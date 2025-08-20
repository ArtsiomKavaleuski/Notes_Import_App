package by.koval.importApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OldClientDto {
    private String agency;
    private String guid;
    private String firstName;
    private String lastName;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
}
