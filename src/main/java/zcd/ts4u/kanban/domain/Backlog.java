package zcd.ts4u.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backlogId;
    //    Integer is the implementation class for int interface
//    private Integer PTSequence = 0;
//    we set PT sequence 0 by default so that we can update it later on
    private String projectIdentifier;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
//    we are not going to create a column in each table, that's why I am using join column to one single column that have this relation mapping.
    // we also use Json ignore annotation for preventing a recursion.
//    now our project with backlog mapping is done, we can go map our backlog with project tasks.
    private Project project;

//    then we also need project tasks within our backlog class.
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,mappedBy = "backlog")
//    we used cascade refresh so that only when the parent entity change its child entities will also change accordingly.
    private List<ProjectTask> projectTasks = new ArrayList<>();
//    we don't have project task class. so let's create one.

}
