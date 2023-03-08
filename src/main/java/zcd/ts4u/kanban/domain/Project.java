package zcd.ts4u.kanban.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//by using this entity annotation we are actually using spring jpa to create a table with this entity name in our database
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name required.")
    @Column(length = 23)
    private String projectName;
    @NotBlank(message = "Project Identifier is required.")
//    we can limit the characters of input for project identifier with @size annotation
    @Size(min = 4, max = 6, message = "it should be within 4 to 6 characters")
//then we can make this instance variable not updatable and unique
    @Column(updatable = false, unique = true)
    private String projectIdentifier;
    @NotBlank(message = "Write something about your project")
    @Column(length = 10000)
    private String description;
    @JsonFormat(pattern = "mm-dd-yyyy")
    private Date start_date;
    @JsonFormat(pattern = "mm-dd-yyyy")
    private Date end_date;
    @JsonFormat(pattern = "mm-dd-yyyy")
    @Column(updatable = false)
    private Date created_at = new Date();
    @JsonFormat(pattern = "mm-dd-yyyy")
    private Date updated_at = null;
//    now we can create Backlog entity for our project with the one to one mapping.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project")
    private Backlog backlog;
}
