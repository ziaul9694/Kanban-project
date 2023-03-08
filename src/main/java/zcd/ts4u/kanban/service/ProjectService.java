package zcd.ts4u.kanban.service;

import org.w3c.dom.stylesheets.LinkStyle;
import zcd.ts4u.kanban.domain.Project;

import java.util.List;

public interface ProjectService {

    // let's say, we have these 4 requests coming from the client.
    //create project and an associated backlog will be created also.
    Project saveOrUpdate(Project project);
    //find project by project identifier.
    Project getProjectByIdentifier(String identifier);

    //delete project by project identifier.
    void deleteProjectByIdentifier(String identifier);

    //get all the projects from the project repository.
    Iterable<Project> getAllProjects();
    //So, we need an implementation class for this interface.
}
