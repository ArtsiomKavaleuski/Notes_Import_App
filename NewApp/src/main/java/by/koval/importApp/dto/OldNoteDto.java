package by.koval.importApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OldNoteDto {
    private String comments;
    private String guid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;
    private String clientGuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z", timezone = "CDT", locale = "en")
    private LocalDateTime datetime;
    private String loggedUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
}
