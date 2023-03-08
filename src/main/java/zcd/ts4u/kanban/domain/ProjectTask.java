package zcd.ts4u.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(updatable = false,unique = true)
    private String projectSequence;
    @NotBlank(message = "Write a summary here")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private String priority;
    @Column(updatable = false)
    private String projectIdentifier;
    @JsonFormat(pattern = "mm-dd-yyyy")
    private Date dueDate;
    @JsonFormat(pattern = "mm-dd-yyyy")
    @Column(updatable = false)
    private Date createdAt = new Date();
    @JsonFormat(pattern = "mm-dd-yyyy")
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;
//    As now, we all have all our instance variables for all the entities, we go for the mapping with Jpa mapping withing these entities

    // we have created all the entities. Now we need to create repositories so that this layer can communicate with the database.
}
