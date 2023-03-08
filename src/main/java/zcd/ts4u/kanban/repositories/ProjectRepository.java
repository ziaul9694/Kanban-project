package zcd.ts4u.kanban.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zcd.ts4u.kanban.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    // here, we will build one custom method to find project by project identifier
    Project findByProjectIdentifier(String identifier);
    // i will create similar ways to find other entities by project identifier.
}

