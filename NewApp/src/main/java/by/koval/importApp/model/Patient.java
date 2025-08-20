package by.koval.importApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "patient_profile", schema = "public")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ElementCollection
    @CollectionTable(name = "old_client_guids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "old_client_guid")
    private List<String> oldClientGuids;
    @Column(name = "status_id", nullable = false)
    private int statusId;
}
