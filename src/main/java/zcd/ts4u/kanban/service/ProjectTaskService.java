package zcd.ts4u.kanban.service;

import zcd.ts4u.kanban.domain.ProjectTask;

public interface ProjectTaskService {
    //let's say, our task requires us to create apis for create project task inside project and find all the project task from a single project. so, we create two method for that first.
    ProjectTask createProjectTask(String identifier, ProjectTask projectTask);

    Iterable<ProjectTask> getProjectTaskFromProject(String identifier);
    // now let's make a implementation class for this service.
}
