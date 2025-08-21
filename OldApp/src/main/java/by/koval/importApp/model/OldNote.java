package by.koval.importApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OldNote {
    @Column(name = "comments")
    private String comments;
    @Id
    @Column(name = "guid")
    private String guid;
    @Column(name = "modified_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;
    @Column(name = "client_guid")
    private String clientGuid;
    @Column(name = "date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z", timezone = "CDT", locale = "en")
    private LocalDateTime datetime;
    @Column(name = "logged_user")
    private String loggedUser;
    @Column(name = "created_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
}
