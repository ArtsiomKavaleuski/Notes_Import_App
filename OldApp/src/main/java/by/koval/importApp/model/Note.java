package by.koval.importApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
public class Note {
    @Column(name = "comments")
    private String comments;
    @Id
    @Column(name = "guid")
    private String guid;
    @Column(name = "modified_datetime")
    private LocalDateTime modifiedDateTime;
    @Column(name = "client_guid")
    private String clientGuid;
    @Column(name = "date_time")
    private LocalDateTime datetime;
    @Column(name = "logged_user")
    private String loggedUser;
    @Column(name = "created_datetime")
    private LocalDateTime createdDateTime;
}
