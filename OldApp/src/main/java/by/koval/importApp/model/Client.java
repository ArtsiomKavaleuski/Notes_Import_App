package by.koval.importApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "clients", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Client {
    @Column(name = "agency")
    private String agency;
    @Id
    private String guid;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "status")
    private String status;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "created_datetime")
    private LocalDateTime createdDateTime;
}
